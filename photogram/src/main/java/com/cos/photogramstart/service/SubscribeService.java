package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;

	//데이터베이스에 영향을 주니까 @Transactional을 걸어준다.
	@Transactional
	public void 구독하기(Integer fromUserId, Integer toUserId ) {
		try {
			subscribeRepository.mSubscribe(fromUserId, toUserId);
		} catch (Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
		
	}
	
	@Transactional
	public void 구독취소하기(Integer fromUserId, Integer toUserId ) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
