package com.soonoleum.education.repository.dto.request;

import java.util.List;

import com.soonoleum.education.domain.Description;

import lombok.Builder;

@Builder
public record DescriptionRequest(
		String name,
		String gender,
		String number,
		String region,
		String title,
		String keyword,
		String rawText,
		String description,
		String fullDescription,
		String quiz,
		List<String> options,
		Integer answer
) {

	public Description toDescription() {
		return Description.builder()
				.name(name)
				.gender(gender)
				.number(number)
				.region(region)
				.title(title)
				.keyword(keyword)
				.rawText(rawText)
				.description(description)
				.fullDescription(fullDescription)
				.quiz(quiz)
				.options(options)
				.answer(answer)
				.build();
	}
}
