/**
 * 
 */
package br.example.sender.core;

import org.springframework.stereotype.Component;

import br.example.sender.core.enums.MessageType;
import br.example.sender.core.smtp.impl.SMTPEmailSender;
import br.example.sender.core.smtp.impl.TextMessageSender;

/**
 * Factory para a construção de mecanismo de envio de mensagem
 * 
 * @author G0055135
 *
 */
@Component
public class SenderFactory {

	public ISender<?, ?> execute(MessageType type) {
		if (type.equals(MessageType.HTML)) {
			return new SMTPEmailSender();
		} else if (type.equals(MessageType.TEXT)) {
			return new TextMessageSender();
		} else {
			return null;
		}
	}
}
