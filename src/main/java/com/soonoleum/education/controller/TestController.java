package com.soonoleum.education.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.soonoleum.education.repository.dto.request.TestRequest;
import com.soonoleum.education.repository.dto.response.TestResponse;
import com.soonoleum.education.service.TestService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestController {

	private final TestService testService;

	@GetMapping("/helloGet/{id}")
	public TestResponse testGet(@PathVariable final Long id) {
		return testService.findById(id);
	}

	@PostMapping("/helloPost")
	public void testPost(@RequestBody final TestRequest request) {
		testService.createTest(request);
	}
}
