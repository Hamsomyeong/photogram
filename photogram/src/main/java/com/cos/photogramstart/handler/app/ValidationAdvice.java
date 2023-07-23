package com.cos.photogramstart.handler.app;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

@Component // IoC 컨테이너에 등록해주는 어노테이션들의 최상위 객체 RestController, service 등 모든것들이 Component를 상속해서 만들어져있음
@Aspect //AOP처리할수 있는 핸들러 
public class ValidationAdvice {

	//@Before - 직전 실행
	//@After - 후 실행
	//@Around - 직전부터 후까지 실행
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")//*무슨 함수로 할래?
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		//ProceedingJoinPoint - commentSave 함수의 모든 내부정보에 접근할 수 있는 파라미터
		//							   - profile 함수보다 먼저실행, profile 함수 모든 곳에 접근 가능.
		System.out.println("web api 컨트롤러 ====================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			//유효성 검사를 하는 함수
			if(arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					throw new CustomValidationApiException("유효검사 실패",errorMap);
				}
			}
			
		}
	
		return proceedingJoinPoint.proceed();//해당 함수로 돌아감//profile 함수 실행됨.
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("web 컨트롤러 ====================================");
		Object[] args = proceedingJoinPoint.getArgs();
		for (Object arg : args) {
			if(arg instanceof BindingResult) {//유효성 검사를 하는 함수
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println(error.getDefaultMessage());
					}
					throw new CustomValidationException("유효검사 실패",errorMap);
				}
				
			}
		}
	
		return proceedingJoinPoint.proceed();
	}
}
