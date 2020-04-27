package br.example.exception;

public class ProduceMessageException extends MessageException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProduceMessageException() {
	}

	public ProduceMessageException(String message) {
		super(message);
	}

	public ProduceMessageException(Throwable cause) {
		super(cause);
	}

	public ProduceMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProduceMessageException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
