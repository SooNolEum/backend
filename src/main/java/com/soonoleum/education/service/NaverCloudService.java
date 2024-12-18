package com.soonoleum.education.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.soonoleum.education.config.NaverCloudConfig;
import com.soonoleum.education.repository.dto.NaverCloudDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NaverCloudService {
	private final NaverCloudConfig naverCloudClient;

	public NaverCloudDto getTextByFile(MultipartFile file) {
		try {
			File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();

			String response = naverCloudClient.soundToText(convFile);
			return new NaverCloudDto(response);
		} catch (Exception e) {
			throw new InvalidFileNameException("잘못된 파일", null);
		}
	}
}
