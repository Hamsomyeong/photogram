package com.cos.photogramstart.handler.ex;

import java.util.Map;

//ControllerExceptionHandler이 낚아챔
public class CustomValidationException extends RuntimeException {

	// 객체를 구분함
	private static final long serialVersionUID = 1L;

	private Map<String, String> errorMap;
	
	public CustomValidationException(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	
	public Map<String, String> getErrorMap(){
		return errorMap;
	}
}
