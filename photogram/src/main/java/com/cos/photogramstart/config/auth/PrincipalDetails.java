package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 권한 리턴 : 한개가 아닐 수 있음. (3개 이상의 권한)
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
//		collector.add(new GrantedAuthority(){
//			@Override
//			public String getAuthority() {
//				return user.getRole();
//			}
//		});
		//람다식
		collector.add(()->{return user.getRole();});
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// 계정이 만료 확인
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// 계정이 잠김 확인
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// 비밀번호 1년 동안 한번도 안바꼈는지 확인
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 활성화 되어있는지 확인
		return true;
	}


}
