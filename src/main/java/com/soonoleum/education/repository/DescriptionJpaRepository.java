package com.soonoleum.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.soonoleum.education.domain.Description;

public interface DescriptionJpaRepository extends JpaRepository<Description, Long> {
}
