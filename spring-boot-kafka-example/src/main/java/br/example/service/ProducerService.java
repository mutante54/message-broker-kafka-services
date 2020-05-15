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
import br.example.enums.MessageType;
import br.example.exception.MessageException;
import br.example.exception.MessageNotFoundException;
import br.example.kafka.vo.MessageVO;

@Service
public class ProducerService {

	@Autowired
	private MessageRepository messageRepository;

	@Value("${message.sender.api}")
	private String messageSenderAPI;

	private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

	@Transactional
	public Long execute(MessageVO messageVO, MessageStatusType status) throws MessageException {

		logger.info("Salvando/ atualizando dados da mensagem (status de produção): " + messageVO.toString());

		Message m = null;

		// recuperando dados da msg na base (ou criando nova mensagem)
		if (messageVO.getId() != null) {
			m = this.messageRepository.findById(messageVO.getId()).orElseThrow(MessageNotFoundException::new);
			m.setUpdatedDate(new Date());
		} else {
			m = new Message();
			m.setCreatedDate(new Date());
		}

		try {

			m.setContent(messageVO.getContent());
			m.setType(MessageType.HTML);
			m.setStatus(status);
			m.setConsumerGroup(messageVO.getConsumerGroup());

			// TODO apenas para testes
			try {
				m.setPhost(InetAddress.getLocalHost().getCanonicalHostName());
			} catch (Exception e) {
				m.setPhost(e.getMessage());
			}

			m.setParams(messageVO.getParams());

			this.messageRepository.save(m);

			logger.info("Mensagem salva/ atualizada com sucesso : " + m.getId());

			return m.getId();

		} catch (Exception e) {
			throw new MessageException("Erro ao salvar/ atualizar mensagem [" + m.getId() + "]", e);
		}

	}

}
