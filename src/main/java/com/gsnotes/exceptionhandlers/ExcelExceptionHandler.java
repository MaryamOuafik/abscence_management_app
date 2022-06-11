package com.gsnotes.exceptionhandlers;

public class ExcelExceptionHandler extends Exception {

		public ExcelExceptionHandler() {
			super();
		}

		public ExcelExceptionHandler(String message, Throwable cause, boolean enableSuppression,
				boolean writableStackTrace) {
			super(message, cause, enableSuppression, writableStackTrace);
		}

		public ExcelExceptionHandler(String message, Throwable cause) {
			super(message, cause);
		}

		public ExcelExceptionHandler(String message) {
			super(message);
		}

		public ExcelExceptionHandler(Throwable cause) {
			super(cause);
		}
	

}
