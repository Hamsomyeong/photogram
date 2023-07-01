package com.cos.photogramstart.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{//Object,primary key type
	//jpa query method 사용
	//로그인 세션 저장
	User findByUsername(String username);
}
