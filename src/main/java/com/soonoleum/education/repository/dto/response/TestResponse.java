package com.soonoleum.education.repository.dto.response;

import com.soonoleum.education.domain.TestDamain;

import lombok.Builder;

@Builder
public record TestResponse(
		Long id,
		String name
) {

	public static TestResponse from(final TestDamain testDamain) {
		return TestResponse.builder()
				.id(testDamain.getId())
				.name(testDamain.getName())
				.build();
	}
}
