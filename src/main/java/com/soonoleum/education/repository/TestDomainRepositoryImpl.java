package com.soonoleum.education.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.soonoleum.education.domain.TestDamain;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TestDomainRepositoryImpl {

	private final TestDomainJpaRepository testDomainJpaRepository;

	public TestDamain save(TestDamain testDamain) {
		return testDomainJpaRepository.save(testDamain);
	}

	public Optional<TestDamain> findById(final Long id) {
		return testDomainJpaRepository.findById(id);
	}

}
