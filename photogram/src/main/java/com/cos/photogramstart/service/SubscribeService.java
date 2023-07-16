package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {

	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체

	@Transactional(readOnly = true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {
		// 쿼리 준비 단계
		// 스칼라 쿼리: select절에 sub select 절을 반환(단, 단일 행이어야 한다)
		// if로 통일성을 준다 -> true면 1, false면 0
		// 쿼리에는 세미콜론 첨부하면 안됨.
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
		
		sb.append("if((SELECT 1 FROM subscribe ");
		sb.append("WHERE fromUserId = ? AND toUserId = u.id),1,0) subscribeState, ");
		
		sb.append("if((?=u.id),1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribes ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId = ?");

		// 1. 물음표=pageUserId
		// 2. 물음표=principalId
		// 3. 마지막 물음표=pageUserId

		// 쿼리 완성
		Query query = em.createNativeQuery(sb.toString()).setParameter(1, principalId).setParameter(2, principalId)
				.setParameter(3, pageUserId);

		// 쿼리 실행
		// 한 건 리턴: uniqueResult, 여러 건 린턴: list
		// pom.xml 추가
		// -> qlrm: 데이터베이스에서 result를 자바클래스에 매핑해주는 라이브러리
		// -> Dto에 DB결과를 매핑하기 위해서 사용
		JpaResultMapper result = new JpaResultMapper();
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);

		return subscribeDtos;
	}

	// 데이터베이스에 영향을 주니까 @Transactional을 걸어준다.
	@Transactional
	public void 구독하기(Integer fromUserId, Integer toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}

	}

	@Transactional
	public void 구독취소하기(Integer fromUserId, Integer toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
