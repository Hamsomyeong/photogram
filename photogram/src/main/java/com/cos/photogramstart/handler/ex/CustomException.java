package com.cos.photogramstart.handler.ex;

//ControllerExceptionHandler이 낚아챔
public class CustomException extends RuntimeException {

	// 객체를 구분함
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);
	}
}
