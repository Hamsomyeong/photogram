package com.cos.photogramstart.web.dto.Image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	// MultipartFile은 @notblank가 지원이 안된다. -> controller에서 처리해주자
	private MultipartFile file;
	private String caption; //코멘트
	
	public Image toEntity(String postImageUrl, User user){
		return Image.builder()
				.caption(caption)
				.postImageUrl(postImageUrl)
				.user(user) //어떤 유저가 사진을 저장했는지 저장.
				.build();
	}
}
