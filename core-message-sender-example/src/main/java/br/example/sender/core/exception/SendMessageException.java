/**
 * 
 */
package br.example.sender.core.exception;

/**
 * @author G0055135
 *
 */
public class SendMessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public SendMessageException() {
	}

	/**
	 * @param message
	 */
	public SendMessageException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public SendMessageException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public SendMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public SendMessageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
