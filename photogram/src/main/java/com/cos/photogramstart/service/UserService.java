package com.cos.photogramstart.service;

import java.io.Console;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원수정(int id, User user) {
		//1. 영속화
		//1. get() 무조건 찾았다 2. orElseThrow() 못찾았어
		User userEntitiy = userRepository.findById(id).get();

		//2. 영속화된 오브젝트를 수정 - 더티체킹(업데이트 완료)
		userEntitiy.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntitiy.setPassword(encPassword);
		
		userEntitiy.setBio(user.getBio());
		userEntitiy.setWebsite(user.getWebsite());
		userEntitiy.setPhone(user.getPhone());
		userEntitiy.setGender(user.getGender());
		
		return userEntitiy;
	}
}
