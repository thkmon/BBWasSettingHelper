package com.thkmon.was.error;

public class MsgException extends Exception {
	public MsgException() {
		super();
	}

	public MsgException(String message, Throwable cause) {
		super(message, cause);
	}

	public MsgException(String message) {
		super(message);
	}

	public MsgException(Throwable cause) {
		super(cause);
	}
}