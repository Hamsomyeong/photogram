package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.Image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	 //application.yml -> file -> path 가져옴
	@Value("${file.path}")
	private String uploadFolder;
	
	//왜 @Transactional 해줘야하지? -> 여러가지 insert나 update가 일어나면 하나를 호출했을때 한개가 실패한다면 다 실패하게 해야한다.
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID(); //유일성이 보장되는 암호코드
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();//파일명.jpg
		System.out.println("이미지 파일 이름: "+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
	
		//통신, I/O 일어날때 예외 발생 할 수 있음.(없을 수 있음)
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		imageRepository.save(image);
	
		//imageEntity.toString 시 User 출력 시 오류 발생
		//오브젝트를 콘솔에 출력 할 때 문제가 될 수 있어서 User  부분을 출력하지 않게 함.
//		System.out.println(imageEntity);
	}
}
