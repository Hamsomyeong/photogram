package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId") //FK 이름
	@ManyToOne //한 유저가 여러이미지 등록
	private User user;
	
	//이미지 좋아요
	@JsonIgnoreProperties({"image"})//무한참조 안되게 막음
	@OneToMany(mappedBy = "image")//연관관계의 주인이 아님.
	private List<Likes> likes;
	
	//댓글
	
	private LocalDateTime localDateTime;
	
	@Transient //DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	@PrePersist
	public void createDate() {
		this.localDateTime = LocalDateTime.now();
	}
}
