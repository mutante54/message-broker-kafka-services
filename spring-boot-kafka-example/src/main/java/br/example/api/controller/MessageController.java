package br.example.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.example.exception.ProduceMessageException;
import br.example.kafka.sender.Sender;
import br.example.kafka.vo.MessageMailVO;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private Sender sender;

	@RequestMapping(value = "/{topic}/send", method = RequestMethod.POST, consumes = "application/json")
	public void sendMessage(@RequestHeader(name = "API_KEY", required = true) String apiKey, @PathVariable("topic") String topic, @RequestBody MessageMailVO messageMailVO) throws ProduceMessageException {

		if (StringUtils.isEmpty(apiKey)) {
			throw new IllegalArgumentException("API_KEY não informada");
		} else {
			// TODO validar autenticidade da API_KEY (cruzar com a base de dados)
		}

		this.sender.sendMessage(apiKey, topic, messageMailVO);
	}
}
