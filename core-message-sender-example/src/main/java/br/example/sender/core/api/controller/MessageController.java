package br.example.sender.core.api.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.example.sender.core.exception.MessageNotFoundException;
import br.example.sender.core.service.MessageConsumerService;
import br.example.sender.core.vo.MessageVO;

@RestController
@RequestMapping("/messages")
public class MessageController {

	@Autowired
	private MessageConsumerService mConsumerService;

	@RequestMapping(value = "/{id}/consume", method = RequestMethod.POST, consumes = "application/json")
	public void consumerMessage(@RequestHeader(name = "API_KEY", required = true) String apiKey, @PathVariable("id") long mId, @RequestBody MessageVO message) throws MessageNotFoundException {

		if (StringUtils.isEmpty(apiKey)) {
			throw new IllegalArgumentException("API_KEY não informada");
		} else {
			// TODO validar autenticidade da API_KEY (cruzar com a base de dados)
		}

		if (message.getId() != mId) {
			throw new IllegalArgumentException("Requisição inválida: objeto informado é inválido");
		}

		this.mConsumerService.execute(message);
	}
}
