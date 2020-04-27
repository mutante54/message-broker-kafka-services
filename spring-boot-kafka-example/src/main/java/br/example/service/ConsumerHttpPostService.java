package br.example.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.example.exception.MessageException;
import br.example.kafka.vo.MessageVO;
import br.example.net.exception.HttpRequestUtilsException;
import br.example.net.util.HttpRequestUtils;

@Service
public class ConsumerHttpPostService {

	@Value("${message.sender.api}")
	private String messageSenderAPI;

	private static final Logger logger = LoggerFactory.getLogger(ConsumerHttpPostService.class);

	public void execute(MessageVO messageVO) throws MessageException {

		logger.info("Enviando mensagem para o módulo de envio: " + messageVO.toString());

		try {

			/**
			 * Enviando para o módulo de envio de mensagens
			 */
			MessageVO m = new MessageVO();
			m.setId(messageVO.getId());

			HttpRequestUtils<MessageVO, Void> http = new HttpRequestUtils<MessageVO, Void>(this.messageSenderAPI + m.getId() + "/consume");
			
			// by-pass da API_KEY para o módulo de envio
			HashMap<String, String> headerParams = new HashMap<String, String>();
			headerParams.put("API_KEY", messageVO.getApiKey());

			http.post(m, headerParams);

			logger.info("Mensagem enviada com sucesso para o módulo de envio: " + messageVO.getId());

		} catch (Exception e) {
			if (e instanceof HttpRequestUtilsException) {
				logger.error("Erro ao enviar mensagem [{}] para o módulo de envio", messageVO, e.getCause());
			}
			throw new MessageException("Erro ao consumir mensagem [" + messageVO.getId() + "]", e);
		}

	}

}
