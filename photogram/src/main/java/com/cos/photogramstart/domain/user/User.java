package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;

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
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String name;
	private String website;
	private String bio;//자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;

	private String profileImageUrl;//프로필 사진
	private String role;//권한
	
	//양방향 매핑
	//한명의 유저는 여러개의 이미지를 가질 수 있다.
	//mappedBy 
	//		1. 나는 연관관계의 주인이 아니다. 그러므로 테이블 컬럼을 만들지마.
	//		2. User를 Select할 때 해당 User id로 등록된 image를 다 가져와.
	//FetchType
	//		1. Lazy = User를 Select할 때 해당 User id로 등록된 image들을 가져오지마 - 대신 getImages()함수의 image들이 호출될 때 가져와!!
	//		2. EAGER = User를 Select할 때 해당 User id로 등록된 image들을 전부 Join해서 가져와!
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Image> images; // 프로필페이지를 응답할 때, 같이 담아올 image의 정보
	
	private LocalDateTime createDate;
	
	@PrePersist //DB에 insert되기 직전 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
}
