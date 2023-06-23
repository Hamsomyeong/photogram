package com.cos.controllerdemo.web;

import java.util.Iterator;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.UserDatabase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.controllerdemo.domain.User;

@Controller
public class JavaToJspController {

	@GetMapping("/jsp/java")
	public String jspToJava() {
		return "d";
	}
	
	@GetMapping("jsp/java/model")
	public String javaToJavaToModel(Model model) {//함수의 파라미터에 모델을 선언하고,
		User user = new User();
		user.setUsername("ssar");
		model.addAttribute("username", user.getUsername());//addAttribute 함수로 전달만 하면 됨.
		
		return "e";
	}
}
