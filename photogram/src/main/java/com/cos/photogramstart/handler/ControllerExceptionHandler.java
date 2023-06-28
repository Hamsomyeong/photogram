package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.util.Script;
import com.cos.photogramstart.web.CMRespDto;

@RestController
@ControllerAdvice // 모든 exception 낚아채기
public class ControllerExceptionHandler {

	// RuntimeException이 발생하는 모든 함수를 가로챔 -> 내가 관리 CustomValidationException
	// error
//	@ExceptionHandler(CustomValidationException.class)
//	public Map<String, String> validationException(CustomValidationException e) {
//		
//		return e.getErrorMap();
//	}

//	//json 에러 page (ajax,android 통신 좋음) -> error message + error //제네릭 ? : 모든 타입 받음
//	@ExceptionHandler(CustomValidationException.class)
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		return new CMRespDto<Map<String, String>>(-1, e.getMessage(), e.getErrorMap());// 리턴 제네릭은 명시!
//	}

	//javascript (클라이언트에게 응답할때는 script 좋음)
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
		return Script.back(e.getErrorMap().toString());
	}
}
