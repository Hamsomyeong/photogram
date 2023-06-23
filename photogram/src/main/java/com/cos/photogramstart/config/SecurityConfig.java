package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity  //해당 파일로 시큐리티를 활성화 시킴. 
@Configuration  //IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//정상적인 사용자(정상적인 경로로 들어왔는지)확인 할 때 시큐리티 CSRF 토큰을 써서 넘겨주고 받으며 확인한다.
		//But 이번프로젝트에서는 많이 오고가야하기때문에 빼고 쓴다.
		http.csrf().disable();
		
		//super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨
		http.authorizeRequests()
		.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**")
		.authenticated()
		.anyRequest()
		.permitAll() //해당 주소는 인증이 필요(loginPage로 자동 이동)하고, 그 외 주소는 모두 허용한다.
		.and()
		.formLogin()
		.loginPage("/auth/signin")
		.defaultSuccessUrl("/");//로그인을 정상적으로 처리하면 "/"페이지로 이동한다.
	}
}
