package com.cos.photogramstart.handler.ex;

import java.util.Map;

//ControllerExceptionHandler이 낚아챔
public class CustomApiException extends RuntimeException {

	// 객체를 구분함
	private static final long serialVersionUID = 1L;
	
	public CustomApiException(String message) {
		super(message);
	}
	
}
