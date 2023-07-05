package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity //DB 테이블 생성
public class Image {
	
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 자동 증가
	private int id;
	
	private String caption; //오늘 나 너무 피곤해
	private String postImageUrl;//사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장, DB에 경로 insert
	
	@JoinColumn(name = "userId") //FK 이름
	@ManyToOne //한 유저가 여러이미지 등록
	private User user;
	
	//이미지 좋아요
	
	//댓글
	
	private LocalDateTime localDateTime;
	
	@PrePersist
	public void createDate() {
		this.localDateTime = LocalDateTime.now();
	}
}
