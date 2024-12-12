package com.soonoleum.education.repository.dto.request;

import com.soonoleum.education.domain.TestDamain;

import lombok.Builder;

@Builder
public record TestRequest(
		String name
) {
	public TestDamain toTestDomain() {
		return TestDamain.builder()
				.name(name)
				.build();
	}
}
