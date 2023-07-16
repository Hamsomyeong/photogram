package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.subscribe.subscribe;
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
@Table(//복합키 unique 제약 조건
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name="likes_uk", // Unique 제약조건 이름
	            columnNames = { // Unique 제약조건을 적용할 컬럼명//같은 값을 가질 수 없음.
	                "imageId", 
	                "userId"
	            } 
	        )
	    }
	)
public class Likes {//N
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 자동 증가
	private int id;
	
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image; //1
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne
	private User user; //1
	
	private LocalDateTime createDate;
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
