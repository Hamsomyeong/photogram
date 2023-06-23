package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor //final이 걸려있는 모든 것의 생성자를 만들어준다 (자동 DI(의존성 주입))
@Controller //1.IoC 2.파일을 리턴하는 컨트롤러
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	//final은 무조건 초기화 해줘야함.
	private final AuthService authService;
//	public AuthController(AuthService authService) {
//		//의존성 주입
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
	
	//회원가입 버튼 -> /auth/signup -> /auth/signin
	//post할때는 기본적으론 csrf 토큰이 있다. 비활성화해서 사용.
	@PostMapping("/auth/signup")
	public String signup(SignupDto signupDto) {//key=value 기본(x-www-form-urlencoded)
		log.info(signupDto.toString());
		User user = signupDto.toEntity();
		log.info(user.toString());
		User userEntitiy = authService.회원가입(user);
		System.out.println(userEntitiy);
		return "auth/signin";
	}
	
	
	
}
