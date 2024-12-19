package com.soonoleum.education.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.soonoleum.education.repository.dto.response.PostResponse;
import com.soonoleum.education.service.DescriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Description Post API", description = "전달받은 폼 데이터를 변환하고 저장하는 컨트롤러 입니다.")
public class DescriptionPostController {

	private final DescriptionService descriptionService;


	@Operation(summary = "폼 데이터 변환 후 저장", description = "전달받은 폼 데이터를 GPT 기반 프롬프팅을 통해 동화로 만들어 저장하고, Description Id 반환")
	@PostMapping("/speech")
	public ResponseEntity<PostResponse> getTextByFile(@RequestParam("name") final String name,
												@RequestParam("gender") final String gender,
												@RequestParam("number") final String number,
												@RequestParam("region") final String region,
												@RequestParam("keyword") final String keyword,
												@RequestParam("audio") final MultipartFile audio) {
		// description, summary, quiz 생성 및 Id 반환
		final PostResponse response = descriptionService.generate(name, gender, number, region, keyword, audio);
		// 결과 반환
		return ResponseEntity.ok(response);
	}
}
