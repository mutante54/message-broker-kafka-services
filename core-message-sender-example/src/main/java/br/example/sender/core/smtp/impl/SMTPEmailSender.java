/**
 * 
 */
package br.example.sender.core.smtp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.example.sender.core.ISender;
import br.example.sender.core.exception.SMTPSendMessageException;
import br.example.sender.core.exception.SendMessageException;
import br.example.sender.core.smtp.ISMTPSender;
import br.example.sender.core.smtp.enums.SMTPParamType;

/**
 * Responsável por configurar e enviar um e-mail para um host SMTP
 * 
 * @author G0055135
 *
 */
public class SMTPEmailSender implements ISMTPSender {

	private HtmlEmail htEmail;

	/**
	 * 
	 */
	public SMTPEmailSender() {
		this.htEmail = new HtmlEmail();
	}

	@Override
	public String send() throws SMTPSendMessageException {
		try {
			return this.htEmail.send();
		} catch (EmailException e) {
			throw new SMTPSendMessageException("Erro ao enviar e-mail", e);
		}
	}

	@Override
	public ISender<?, ?> build(HashMap<Enum<?>, Object> params, String content) throws SendMessageException {

		try {

			this.htEmail.setCharset((String) params.get(SMTPParamType.CHARSET));
			this.htEmail.setHostName((String) params.get(SMTPParamType.HOST));
			this.htEmail.setSmtpPort(Integer.parseInt((String) params.get(SMTPParamType.SMTP_PORT)));

			this.htEmail.setFrom((String) params.get(SMTPParamType.FROM));
			this.htEmail.addTo((String) params.get(SMTPParamType.TO));
			this.htEmail.setSubject((String) params.get(SMTPParamType.SUBJECT));

			// conteúdo HTML do e-mail (pronto para envio)
			this.htEmail.setHtmlMsg(content);

			String addressReplyTo = (String) params.get(SMTPParamType.REPLY_TO);

			// caso existe uma caixa de resposta
			if (StringUtils.isNotEmpty(addressReplyTo)) {
				List<InternetAddress> internetAddresses = new ArrayList<InternetAddress>();
				InternetAddress address = new InternetAddress(addressReplyTo);
				internetAddresses.add(address);
				this.htEmail.setReplyTo(internetAddresses);
			}

			// caso exista a necessidade de autenticação
			String user = (String) params.get(SMTPParamType.USER);

			if (StringUtils.isNotEmpty(addressReplyTo)) {
				this.htEmail.setAuthentication(user, (String) params.get(SMTPParamType.PASSWORD));
			}

			return this;

		} catch (Exception e) {
			throw new SMTPSendMessageException("Erro ao configurar e-mail para envio", e);
		}
	}
}
