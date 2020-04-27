package br.example.service;

import java.net.InetAddress;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.example.data.model.Message;
import br.example.data.repository.MessageRepository;
import br.example.enums.MessageStatusType;
import br.example.exception.MessageException;
import br.example.exception.MessageNotFoundException;
import br.example.kafka.vo.MessageVO;

@Service
public class ConsumerService {

	@Autowired
	private MessageRepository messageRepository;

	@Value("${message.sender.api}")
	private String messageSenderAPI;

	private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

	@Transactional
	public void execute(MessageVO messageVO) throws MessageException {

		logger.info("Atualizando status de consumo da mensagem: {}" + messageVO.getId());

		// recuperando dados da msg na base
		Message mUpdate = this.messageRepository.findById(messageVO.getId()).orElseThrow(MessageNotFoundException::new);

		try {

			mUpdate.setUpdatedDate(new Date());
			mUpdate.setConsumerGroup(messageVO.getConsumerGroup());
			mUpdate.setStatus(MessageStatusType.CONSUMED);

			// TODO apenas para testes
			try {
				mUpdate.setChost(InetAddress.getLocalHost().getCanonicalHostName());
			} catch (Exception e) {
				mUpdate.setChost(e.getMessage());
			}

			this.messageRepository.save(mUpdate);

			logger.info("Status de consumo da mensagem atualizado com sucesso: {}", messageVO.getId());

		} catch (Exception e) {
			mUpdate.setStatus(MessageStatusType.CONSUME_ERROR);
			this.messageRepository.save(mUpdate);
			throw new MessageException(
					"Erro ao atualizar status de consumo da mensagem [" + messageVO.getId() + "]", e);
		}

	}

}
