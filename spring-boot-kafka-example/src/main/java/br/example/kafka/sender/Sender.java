package br.example.kafka.sender;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.example.enums.MessageStatusType;
import br.example.exception.MessageException;
import br.example.exception.ProduceMessageException;
import br.example.kafka.vo.MessageMailVO;
import br.example.kafka.vo.MessageVO;
import br.example.service.ProducerService;

public class Sender {

	@Autowired
	private ProducerService producerService;

	private static final Logger logger = LoggerFactory.getLogger(Sender.class);

	@Autowired
	private KafkaTemplate<String, MessageVO> kafkaTemplate;

	/**
	 * Faz o envio de uma mensagem
	 * 
	 * @param apikey Chave de autenticação da API
	 * @param topic Fila do Kafka onde a mensagem será processada
	 * @param value Mensagem do solicitante
	 * @throws ProduceMessageException
	 */
	public void sendMessage(String apiKey, String topic, MessageMailVO value) throws ProduceMessageException {

		logger.info("Enviando mensagem: {}", value);

		MessageVO mVO = new MessageVO();
		Message<MessageVO> message = null;

		try {
			// TODO criptografar a API_KEY antes de processar no Kafka. Somente este módulo e o de envio irão possuir a chave privada para criptografia/ descriptografia.
			// TODO Alternativamente, pode ser configurado a criptografia de dados no Kafka com SSL (https://docs.confluent.io/current/kafka/encryption.html).
			mVO.setApiKey(apiKey);
			mVO.setContent(value.getContent());
			mVO.setCreatedDate(value.getSentDate() != null ? value.getSentDate() : new Date());
			ObjectMapper mapper = new ObjectMapper();
			mVO.setParams(mapper.writeValueAsString(value.getParams()));
			mVO.setTemplateCode(value.getTemplateCode());
			mVO.setType(value.getType());

			// we created a Message<SenderObject> using the MessageBuilder. It’s important to add the topic where we are going to send the message to.
			message = MessageBuilder.withPayload(mVO).setHeader(KafkaHeaders.TOPIC, topic).build();

			// garante que cada disparo irá gerar uma nova msg na base
			mVO.setId(null);

			// atualizando mensagem com o id gerado na base
			message.getPayload().setId(this.producerService.execute(mVO, MessageStatusType.READY_TO_PRODUCE));
		} catch (Exception e1) {
			throw new ProduceMessageException("Erro ao produzir mensagem: " + value, e1);
		}

		// the KafkaTemplate provides asynchronous send methods returning a Future
		ListenableFuture<SendResult<String, MessageVO>> future = this.kafkaTemplate.send(message);

		// you can register a callback with the listener to receive the result of the
		// send asynchronously
		future.addCallback(new ListenableFutureCallback<SendResult<String, MessageVO>>() {

			@Override
			public void onSuccess(SendResult<String, MessageVO> result) {
				logger.info("sent message='{}' with offset={}", value, result.getRecordMetadata().offset());

				try {
					producerService.execute(mVO, MessageStatusType.PRODUCED);
				} catch (MessageException e) {
					logger.error("Erro: ", e);
				}
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("unable to send message='{}'", value, ex);

				try {
					producerService.execute(mVO, MessageStatusType.PRODUCE_ERROR);
				} catch (MessageException e) {
					logger.error("Erro: ", e);
				}
			}
		});

		// alternatively, to block the sending thread, to await the result, invoke the futures get() method
	}
}