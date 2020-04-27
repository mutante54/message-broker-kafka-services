package br.example.kafka.receiver;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import br.example.exception.MessageNotFoundException;
import br.example.kafka.vo.MessageVO;
import br.example.service.ConsumerHttpPostService;
import br.example.service.ConsumerService;

public class Receiver {

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private ConsumerHttpPostService consumerHttpPostService;

	/**
	 * Consumers can join a group by using the samegroup.id.
	 * 
	 * The maximum parallelism of a group is that the number of consumers in the
	 * group ← no of partitions.
	 * 
	 * Kafka assigns the partitions of a topic to the consumer in a group, so that
	 * each partition is consumed by exactly one consumer in the group.
	 * 
	 * Kafka guarantees that a message is only ever read by a single consumer in the
	 * group.
	 * 
	 * Consumers can see the message in the order they were stored in the log.
	 * 
	 * https://www.tutorialspoint.com/apache_kafka/apache_kafka_consumer_group_example.htm
	 */

	private static final Logger logger = LoggerFactory.getLogger(Receiver.class);

	// onlyForTests
	private CountDownLatch latchTopicCustomMessages = new CountDownLatch(100);

	// onlyForTests
	private CountDownLatch latchTopicBatchMessages = new CountDownLatch(10000);

	@KafkaListener(topics = "custom-messages", groupId = "custom-messages-group")
	public void receiveCustomMessage(@Payload MessageVO message, @Headers MessageHeaders headers) {
		logger.info("received custom message='{}'", message.toString());
		this.latchTopicCustomMessages.countDown();

		// TODO somente testes
		message.setConsumerGroup("custom-messages-group");

		try {
			// atualizando status de consumo
			this.consumerService.execute(message);
			// request para o módulo de envio de mensagens
			this.consumerHttpPostService.execute(message);
		} catch (MessageNotFoundException e) {
			logger.error("Erro ao consumir mensagem [{}] ", message.getId(), e);
		} catch (Exception er) {
			logger.error("Erro: ", er);
		}
	}

	@KafkaListener(topics = "batch-messages", groupId = "batch-messages-group")
	public void receiveBatchMessage(@Payload MessageVO message, @Headers MessageHeaders headers) {
		logger.info("received batch message='{}'", message);
		this.latchTopicBatchMessages.countDown();

		// TODO somente testes
		message.setConsumerGroup("batch-messages-group");

		try {
			// atualizando status de consumo
			this.consumerService.execute(message);
			// request para o módulo de envio de mensagens
			this.consumerHttpPostService.execute(message);
		} catch (MessageNotFoundException e) {
			logger.error("Erro ao consumir mensagem [{}] ", message.getId(), e);
		} catch (Exception er) {
			logger.error("Erro: ", er);
		}
	}

	public CountDownLatch getLatchTopicCustom() {
		return this.latchTopicCustomMessages;
	}

	public CountDownLatch getLatchTopicBatch() {
		return this.latchTopicBatchMessages;
	}
}
