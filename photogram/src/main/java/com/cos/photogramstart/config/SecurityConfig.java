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
        http.authorizeRequests() // 이 주소경로로 요청이 들어오면
        .antMatchers("/", "/user/**", "/image/**", "/subscribe/**", "/comment/**","/api/**")
        .authenticated() // 인증이 필요하다.
        .anyRequest() // 그 외의 요청들은
        .permitAll() // 모두 허용한다.
        .and() // 그리고
        .formLogin() // 로그인(인증)이 필요한 요청이 들어오면
        .loginPage("/auth/signin") // 로그인페이지 auth/signin 으로 이동시키고(GET요청)
        .loginProcessingUrl("/auth/signin") // auth/signin 이라는 POST요청을 실행시킨다.(UserDetailsService가 낚아챔 -> 로그인 진행(PrincipalDetailsService))
        .defaultSuccessUrl("/"); // 인증이 정삭적으로 완료되면 / 로 이동한다.
	}
}
