/**
 * 
 */
package br.example.sender.core.smtp;

import java.util.HashMap;
import java.util.Map;

import br.example.sender.core.ISender;
import br.example.sender.core.smtp.enums.SMTPParamType;
import br.example.sender.core.vo.MessageVO;

/**
 * Interface que define um contrato padrão para envio de mensagens para um
 * servidor SMTP
 * 
 * @author G0055135
 *
 */
public interface ISMTPSender extends ISender<MessageVO, String> {

	/**
	 * Realiza a configuração de um e-mail para que ele possa ser enviado
	 * 
	 * @param params  {@link HashMap} com os parâmetros do e-mail (chave-valor)
	 * @param content Conteúdo do e-mail (html)
	 * @return a própria instância de {@link ISMTPSender} já configurada e pronta
	 *         para envio
	 * @throws SMTPSendMessageException
	 */
	ISMTPSender build(Map<SMTPParamType, String> params, String content) throws SMTPSendMessageException;

}
