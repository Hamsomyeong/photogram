package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService{//로그인 진행

	private final UserRepository userRepository;
	
	//UserDetailsService 안에 loadUserByUsername 실행 -> 매개변수 username 밖에 없음 -> userRepository DB에서 찾음
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User userEntity = userRepository.findByUsername(username);
		 if (userEntity == null) { // username을 찾지 못했다면
	            return null; // null을 리턴하고
	        } else { // username을 찾았다면
	            return new PrincipalDetails(userEntity); // principalDetails를 session에 저장한다.
	        }
	}

}
