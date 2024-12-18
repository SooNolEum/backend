package com.soonoleum.education.repository.dto.response;

import java.util.ArrayList;
import java.util.List;

import com.soonoleum.education.domain.Description;

import lombok.Builder;

@Builder
public record DescriptionResponse(
		Long id,
		String name,
		String gender,
		String region,
		String title,
		String keyword,
		String description,
		String fullDescription,
		String quiz,
		List<String> options,
		Integer answer
) {

	public static List<DescriptionResponse> fromAll(final List<Description> descriptions) {
		List<DescriptionResponse> responses = new ArrayList<>();
		for (Description description : descriptions) {
			responses.add(builder()
					.id(description.getId())
					.name(description.getName())
					.gender(description.getGender())
					.region(description.getRegion())
					.title(description.getTitle())
					.keyword(description.getKeyword())
					.description(description.getDescription())
					.fullDescription(description.getFullDescription())
					.quiz(description.getQuiz())
					.options(description.getOptions())
					.answer(description.getAnswer())
					.build());
		}
		return responses;
	}

	public static DescriptionResponse from(final Description description) {
		return builder()
				.id(description.getId())
				.name(description.getName())
				.gender(description.getGender())
				.region(description.getRegion())
				.title(description.getTitle())
				.keyword(description.getKeyword())
				.description(description.getDescription())
				.fullDescription(description.getFullDescription())
				.quiz(description.getQuiz())
				.options(description.getOptions())
				.answer(description.getAnswer())
				.build();
	}
}
