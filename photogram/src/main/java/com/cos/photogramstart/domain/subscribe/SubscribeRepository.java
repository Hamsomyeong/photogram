package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
	
	//:은 해당 변수를 바인딩해서 DB에 넣겠다는 뜻
	//@Modifying -> insert, delete, update를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요!!
	@Modifying
	@Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
	void mSubscribe(int fromUserId, int toUserId);
	
	@Modifying
	@Query(value = "DELETE FROM subscribe WHERE fromUserId =:fromUserId AND toUserId=:toUserId", nativeQuery = true)
	void mUnSubscribe(int fromUserId, int toUserId);

}
