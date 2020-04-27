package br.example.exception;

public class MessageNotFoundException extends MessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageNotFoundException() {
	}

	public MessageNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MessageNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public MessageNotFoundException(String message) {
		super(message);
	}

	public MessageNotFoundException(Throwable cause) {
		super(cause);
	}

}
