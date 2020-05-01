package br.example.sender.core.service;

import java.util.Date;
import java.util.HashMap;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.example.sender.core.SenderFactory;
import br.example.sender.core.data.model.Message;
import br.example.sender.core.data.repository.MessageRepository;
import br.example.sender.core.enums.MessageStatusType;
import br.example.sender.core.enums.MessageType;
import br.example.sender.core.exception.MessageNotFoundException;
import br.example.sender.core.exception.SendMessageException;
import br.example.sender.core.smtp.enums.SMTPParamType;
import br.example.sender.core.vo.MessageVO;

@Service
public class MessageConsumerService {

	@Autowired
	private MessageRepository messageRepository;

	private SenderFactory senderFactory;

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
		HashMap<Enum<?>, Object> params = new HashMap<Enum<?>, Object>();

		if (m.getType() == MessageType.HTML) {
			params.put(SMTPParamType.HOST, "host");
			params.put(SMTPParamType.SMTP_PORT, "smtp_port");
			params.put(SMTPParamType.USER, "smtp_user");
			params.put(SMTPParamType.PASSWORD, "smtp_user");
			params.put(SMTPParamType.FROM, "email_from");
			params.put(SMTPParamType.TO, "email_to");
			params.put(SMTPParamType.SUBJECT, "subject");
			params.put(SMTPParamType.REPLY_TO, "reply_to");
			params.put(SMTPParamType.REPLY_TO, "charset");
		}

		m.setUpdatedDate(new Date());

		try {
			// enviando mensagem
			this.senderFactory.execute(m.getType()).build(params, m.getContent()).send();
			m.setStatus(MessageStatusType.SENT);
		} catch (SendMessageException e) {
			m.setStatus(MessageStatusType.SEND_ERROR);
			logger.error("Erro ao enviar mensagem", e);
		}

		this.messageRepository.save(m);

		logger.info("Mensagem consumida com sucesso: {}", message.getId());

	}

}
