package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{//Object,primary key type
	//jpa query method 사용 -> 메소드 이름으로 쿼리 작성 
	//data JPA -> 이름만으로 username 찾기
	User findByUsername(String username);
}
