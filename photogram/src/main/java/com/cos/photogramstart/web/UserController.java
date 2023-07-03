package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;

@Controller
public class UserController {

	@GetMapping({"/user/{id}"})
	public String profile(@PathVariable int id) {
		return "user/profile";
	}

	@GetMapping({"/user/{id}/update"})
	public String update(@PathVariable int id, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		System.out.println("세션 정보: "+principalDetails.getUser());
		
		// Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();
        // System.out.println("직접찾은 세션 정보: " + mPrincipalDetails.getUser());
        
//		//principal : 접근 주체 
		//시큐리티 태그 라이브러리 사용 안했을 때, model 이용
//		model.addAttribute("principal", principalDetails.getUser());
		//시큐리티 태그 라이브러리 사용 했을때는 jsp에서 직접 값을 받아옴
		return "user/update";
	}
}
