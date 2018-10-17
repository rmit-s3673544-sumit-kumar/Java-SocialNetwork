package application.exception;

public class NoParentException extends RelationshipException {
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		

		public NoParentException(String message) {
			super(message);
			// TODO Auto-generated constructor stub
		}

		public NoParentException() {
			super();
			// TODO Auto-generated constructor stub
		}

		public NoParentException(String message, Throwable cause,
				boolean enableSuppression, boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
			// TODO Auto-generated constructor stub
		}

		public NoParentException(String message, Throwable cause) {
			super(message, cause);
			// TODO Auto-generated constructor stub
		}

		public NoParentException(Throwable cause) {
			super(cause);
			// TODO Auto-generated constructor stub
		}

}
