package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // final이 걸려있는 모든 것의 생성자를 만들어준다 (자동 DI(의존성 주입))
@Controller // 1.IoC 2.파일을 리턴하는 컨트롤러
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	// final은 무조건 초기화 해줘야함.
	private final AuthService authService;
//	public AuthController(AuthService authService) {
//		//의존성 주입 -> final
//		//@Service 어노테이션으로 컨테이너에 AuthService 만들어 놓음.
//		this.authService = authService;
//	}

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}

	// 로직 : 회원가입 버튼 -> /auth/signup -> /auth/signin
	// 1. post할때는 기본적으론 csrf 토큰이 있다. 비활성화해서 사용. -> config
	// 2. @Valid 전처리(길이 검사 등) -> Dto에 걸어줌 -> 에러가 있으면 BindingResult에 담김
	// 3. String 이면 파일을 리턴하지만 @ResponseBody String이면 데이터를 리턴한다.
	// 4. 예외처리는 한번에! -> CustomValidationException 직접 만들기
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) {// key=value
			User user = signupDto.toEntity();
			authService.회원가입(user);	
			return "auth/signin";
	}

}
