package com.example.sacBack.repositories;

import com.example.sacBack.models.ntities.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestStepRepository extends JpaRepository<TestStep, Long> {
    List<TestStep> findBySkills_Id(Long skillId);
}
