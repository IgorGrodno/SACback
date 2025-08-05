package com.example.sacBack.repositories;

import com.example.sacBack.models.ntities.TestStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestStepRepository extends JpaRepository<TestStep, Long> {
    List<TestStep> findBySkills_Id(Long skillId);

    @Query("SELECT COUNT(s) > 0 FROM Skill s JOIN s.testSteps ts WHERE ts.id = :testStepId")
    boolean existsByIdUsedInSkills(@Param("testStepId") Long testStepId);
}
