package application.exception;

public class TooYoungException extends RelationshipException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooYoungException(String alert) {
		super(alert);
	}

	public TooYoungException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TooYoungException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public TooYoungException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TooYoungException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	

}
