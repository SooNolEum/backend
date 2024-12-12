package com.soonoleum.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soonoleum.education.domain.TestDamain;

public interface TestDomainJpaRepository extends JpaRepository<TestDamain, Long> {
}
