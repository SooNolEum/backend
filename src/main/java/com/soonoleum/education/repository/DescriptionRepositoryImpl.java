package com.soonoleum.education.repository;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import com.soonoleum.education.domain.Description;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DescriptionRepositoryImpl {

	private final DescriptionJpaRepository descriptionDomainJpaRepository;

	public List<Description> findAll() {
		return descriptionDomainJpaRepository.findAll();
	}

	public void save(final Description description) {
		descriptionDomainJpaRepository.save(description);
	}

	public Description findById(final Long id) {
		return descriptionDomainJpaRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("존재하지 않는 id 입니다."));
	}

}
