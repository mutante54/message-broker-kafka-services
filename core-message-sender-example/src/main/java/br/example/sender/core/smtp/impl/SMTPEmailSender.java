/**
 * 
 */
package br.example.sender.core.smtp.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import br.example.sender.core.smtp.ISMTPSender;
import br.example.sender.core.smtp.SMTPSendMessageException;
import br.example.sender.core.smtp.enums.SMTPParamType;
import br.example.sender.core.vo.MessageVO;

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
	public String send(MessageVO p) throws SMTPSendMessageException {
		try {
			return this.htEmail.send();
		} catch (EmailException e) {
			throw new SMTPSendMessageException("Erro ao enviar e-mail", e);
		}
	}

	@Override
	public void configure(Map<SMTPParamType, String> params, String content) throws SMTPSendMessageException {

		try {

			this.htEmail.setCharset(params.get(SMTPParamType.CHARSET));
			this.htEmail.setHostName(params.get(SMTPParamType.HOST));
			this.htEmail.setSmtpPort(Integer.parseInt(params.get(SMTPParamType.SMTP_PORT)));

			this.htEmail.setFrom(params.get(SMTPParamType.FROM));
			this.htEmail.addTo(params.get(SMTPParamType.TO));
			this.htEmail.setSubject(params.get(SMTPParamType.SUBJECT));

			// conteúdo HTML do e-mail (pronto para envio)
			this.htEmail.setHtmlMsg(content);

			String addressReplyTo = params.get(SMTPParamType.REPLY_TO);

			// caso existe uma caixa de resposta
			if (StringUtils.isNotEmpty(addressReplyTo)) {
				List<InternetAddress> internetAddresses = new ArrayList<InternetAddress>();
				InternetAddress address = new InternetAddress(addressReplyTo);
				internetAddresses.add(address);
				this.htEmail.setReplyTo(internetAddresses);
			}

			// caso exista a necessidade de autenticação
			String user = params.get(SMTPParamType.USER);

			if (StringUtils.isNotEmpty(addressReplyTo)) {
				this.htEmail.setAuthentication(user, params.get(SMTPParamType.PASSWORD));
			}

		} catch (Exception e) {
			throw new SMTPSendMessageException("Erro ao configurar e-mail para envio", e);
		}
	}
}
