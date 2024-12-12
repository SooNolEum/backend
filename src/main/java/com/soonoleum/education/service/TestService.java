package com.soonoleum.education.service;

import org.springframework.stereotype.Service;

import com.soonoleum.education.domain.TestDamain;
import com.soonoleum.education.repository.TestDomainRepositoryImpl;
import com.soonoleum.education.repository.dto.request.TestRequest;
import com.soonoleum.education.repository.dto.response.TestResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestService {

	private final TestDomainRepositoryImpl testDomainRepository;

	public void createTest(final TestRequest request) {
		final TestDamain testDamain = request.toTestDomain();
		testDomainRepository.save(testDamain);
	}

	public TestResponse findById(final Long id) {
		final TestDamain testDamain = testDomainRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("존재하지 않는 아이디 입니다."));
		return TestResponse.from(testDamain);
	}

}
