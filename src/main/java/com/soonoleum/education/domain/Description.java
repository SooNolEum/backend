package com.soonoleum.education.domain;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Description {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String gender;

	@Column(nullable = false)
	private String number;

	@Column(nullable = false)
	private String region;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String keyword;

	@Column(nullable = false)
	private String rawText;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 1000)
	private String fullDescription;

	@Column(nullable = false)
	private String quiz;

	@Column(nullable = false)
	@Convert(converter = StringListConverter.class)
	private List<String> options;

	@Column(nullable = false)
	private Integer answer;
}
