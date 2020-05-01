/**
 * 
 */
package br.example.sender.core.smtp.impl;

import java.util.HashMap;

import br.example.sender.core.ISender;
import br.example.sender.core.exception.SendMessageException;

/**
 * @author G0055135
 *
 */
public class TextMessageSender implements ISender<String, String> {

	@SuppressWarnings("unused")
	private String textMessage;

	@Override
	public String send() throws SendMessageException {
		/**
		 * TODO enviar para onde for necessário
		 */
		return null;
	}

	@Override
	public TextMessageSender build(HashMap<Enum<?>, Object> params, String content) throws SendMessageException {
		this.textMessage = content;
		return this;
	}
}
