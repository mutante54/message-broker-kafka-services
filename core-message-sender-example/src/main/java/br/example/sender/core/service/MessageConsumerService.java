package br.example.sender.core.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.example.sender.core.data.model.Message;
import br.example.sender.core.data.repository.MessageRepository;
import br.example.sender.core.enums.MessageStatusType;
import br.example.sender.core.exception.MessageNotFoundException;
import br.example.sender.core.vo.MessageVO;

@Service
public class MessageConsumerService {

	@Autowired
	private MessageRepository messageRepository;

	private static final Logger logger = LogManager.getLogger(MessageConsumerService.class);

	@Transactional
	public void execute(MessageVO message) throws MessageNotFoundException {

		logger.info("Consumindo mensagem: {}", message.getId());		

		// recuperando dados da msg na base
		Message m = this.messageRepository.findById(message.getId()).orElseThrow(MessageNotFoundException::new);
		
		/**
		 * TODO processar a mensagem conforme objetivo da solução de negócio. Utilizar
		 * Service específico.
		 */

		m.setUpdatedDate(new Date());
		m.setStatus(MessageStatusType.SENT);

		this.messageRepository.save(m);

		logger.info("Mensagem consumida com sucesso: {}", message.getId());

	}

}
