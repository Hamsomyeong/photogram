package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	// application.yml -> file -> path 가져옴
	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public User 회원프로필사진변경(int principalId, MultipartFile multipartFile) {
		UUID uuid = UUID.randomUUID(); // 유일성이 보장되는 암호코드
		String imageFileName = uuid + "_" + multipartFile.getOriginalFilename();// 파일명.jpg
		System.out.println("이미지 파일 이름: " + imageFileName);

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);

		// 통신, I/O 일어날때 예외 발생 할 수 있음.(없을 수 있음)
		try {
			Files.write(imageFilePath, multipartFile.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		User userEntity = userRepository.findById(principalId).orElseThrow(()->{
			throw new CustomApiException("유저를 찾을 수 없습니다.");
		});
		userEntity.setProfileImageUrl(imageFileName);
		return userEntity;
	}// 더티체킹으로 업데이트됨

	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();

		// select*from image where userId=userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});

		dto.setUser(userEntity);
		dto.setImageCount(userEntity.getImages().size());
		dto.setPageOwnerState(pageUserId == principalId);// true은 페이지의 주인, false은 주인이 아님.

		// DTO에 구독정보 담기
		Integer subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		Integer subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

		dto.setSubscribeState(subscribeState == 1);// 1이면 true
		dto.setSubscribeCount(subscribeCount);

		// 좋아요 카운트 추가하기
		userEntity.getImages().forEach((image) -> {
			image.setLikeCount(image.getLikes().size());
		});

		return dto;
	}

	@Transactional
	public User 회원수정(int id, User user) {
		// 1. 영속화
		// 1. get() 무조건 찾았다 2. orElseThrow() 못찾았어 액션 발동 시킬께
//		User userEntitiy = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("찾을 수 없는 id입니다.");
//			}
//		});

		// 람다식
		User userEntitiy = userRepository.findById(id).orElseThrow(() -> {
			return new CustomValidationApiException("찾을 수 없는 id입니다.");
		});

		// 2. 영속화된 오브젝트를 수정 - 더티체킹(업데이트 완료)
		userEntitiy.setName(user.getName());

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntitiy.setPassword(encPassword);

		userEntitiy.setBio(user.getBio());
		userEntitiy.setWebsite(user.getWebsite());
		userEntitiy.setPhone(user.getPhone());
		userEntitiy.setGender(user.getGender());

		return userEntitiy;
	}
}
