/**
 * 
 */
package br.example.sender.core.smtp;

import br.example.sender.core.ISender;
import br.example.sender.core.vo.MessageVO;

/**
 * Interface que define um contrato padrão para envio de mensagens para um servidor SMTP.
 * 
 * Este contrato define comportamentos adicionais aos impostos pelo {@link ISender}
 * 
 * @author G0055135
 *
 */
public interface ISMTPSender extends ISender<MessageVO, String> {

}
