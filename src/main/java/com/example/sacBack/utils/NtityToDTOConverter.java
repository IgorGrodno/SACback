package com.example.sacBack.utils;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.ntities.Skill;
import com.example.sacBack.models.ntities.TestStep;
import org.springframework.stereotype.Service;

@Service
public class NtityToDTOConverter {
    public SkillDTO convertToDTO (Skill skill) {
        SkillDTO skillNameDTO = new SkillDTO();
        skillNameDTO.setName(skill.getName());
        skillNameDTO.setId(skill.getId());
        return skillNameDTO;
    }

    public TestStepDTO convertToDTO(TestStep testStep) {
        TestStepDTO testStepDTO = new TestStepDTO();
        testStepDTO.setId(testStep.getId());
        testStepDTO.setDescription(testStep.getDescription());
        return testStepDTO;
    }
}
