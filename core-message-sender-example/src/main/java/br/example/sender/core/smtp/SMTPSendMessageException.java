/**
 * 
 */
package br.example.sender.core.smtp;

import br.example.sender.core.exception.SendMessageException;

/**
 * @author G0055135
 *
 */
public class SMTPSendMessageException extends SendMessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SMTPSendMessageException() {
	}

	/**
	 * @param message
	 */
	public SMTPSendMessageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SMTPSendMessageException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SMTPSendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SMTPSendMessageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
