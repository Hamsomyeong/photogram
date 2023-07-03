package com.cos.photogramstart.domain.subscribe;

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
@Table(//복합키 unique 제약 조건
	    uniqueConstraints = {
	        @UniqueConstraint(
	            name="subscribe_uk", // Unique 제약조건 이름
	            columnNames = { // Unique 제약조건을 적용할 컬럼명
	                "fromUserId", 
	                "toUserId"
	            }
	        )
	    }
	)
public class Subscribe {
	@Id //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 자동 증가
	private int id;
	
	@JoinColumn(name = "fromUserId")
	@ManyToOne
	private User fromUser;
	
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser;
	
	private LocalDateTime createDate;//데이터가 입력된 시간
	
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
