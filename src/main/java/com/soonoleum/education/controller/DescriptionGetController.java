package com.soonoleum.education.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.soonoleum.education.repository.dto.response.DescriptionResponse;
import com.soonoleum.education.service.DescriptionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Tag(name = "Description Get API", description = "저장된 폼 데이터를 반환하는 컨트롤러 입니다.")
public class DescriptionGetController {

	private final DescriptionService descriptionService;

	@Operation(summary = "모든 폼 데이터 반환", description = "저장된 모든 폼 데이터 반환")
	@GetMapping("/explore")
	public ResponseEntity<List<DescriptionResponse>> findAll() {
		final List<DescriptionResponse> responses = descriptionService.findAll();
		return ResponseEntity.ok(responses);
	}

	@Operation(summary = "Description Id 기반 폼 데이터 반환", description = "저장된 폼 데이터 반환")
	@GetMapping("/speech/result/{id}")
	public ResponseEntity<DescriptionResponse> findById(@PathVariable final Long id) {
		final DescriptionResponse response = descriptionService.findById(id);
		return ResponseEntity.ok(response);
	}
}
