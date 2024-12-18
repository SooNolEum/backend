package com.soonoleum.education.repository.dto.response;

import lombok.Builder;

@Builder
public record PostResponse(
		Long id
) {
	public static PostResponse from(final Long descriptionId) {
		return builder()
				.id(descriptionId)
				.build();
	}
}
