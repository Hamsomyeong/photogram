package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB)할 수 있는 API제공)

@Builder
@AllArgsConstructor //모든 생성자를 자동으로 만들어준다
@NoArgsConstructor //빈 생성자를 자동으로 만들어준다
@Data //Getter,Setter,toString를 자동으로 만들어준다
@Entity //DB에 테이블 자동 생성
public class User {
	@Id //primary key
	//mysql - auto_increment , oracle - 시퀀스 (데이터베이스 전략을 따라간다)
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 자동 증가
	private int id;
	
	@Column(unique = true)
	private String username;
	private String password;
	
	private String name;
	private String website;
	private String bio;//자기소개
	private String email;
	private String phone;
	private String gender;

	private String profileImageUrl;//프로필 사진
	private String role;//권한
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 insert되기 직전 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
