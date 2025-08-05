package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.ntities.Skill;
import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.repositories.SkillRepository;
import com.example.sacBack.repositories.TestStepRepository;
import com.example.sacBack.utils.NtityToDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final TestStepRepository testStepRepository;
    private final NtityToDTOConverter converter;

    public SkillService(SkillRepository skillRepository,
                        TestStepRepository testStepRepository,
                        NtityToDTOConverter converter) {
        this.skillRepository = skillRepository;
        this.testStepRepository = testStepRepository;
        this.converter = converter;
    }

    public List<SkillDTO> findAll() {
        return skillRepository.findAll().stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public SkillDTO findById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        return converter.convertToDTO(skill);
    }

    public SkillDTO create(SkillDTO skillDTO) {
        Skill skill = new Skill();
        skill.setName(skillDTO.getName());

        List<TestStep> testSteps = skillDTO.getSteps().stream()
                .map(stepDTO -> testStepRepository.findById(stepDTO.getId())
                        .orElseThrow(() -> new RuntimeException("TestStep not found: " + stepDTO.getId())))
                .toList();

        skill.setTestSteps(testSteps);

        return converter.convertToDTO(skillRepository.save(skill));
    }

    public void update(Long id, SkillDTO updatedDTO) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));

        skill.setName(updatedDTO.getName());

        List<TestStep> steps = updatedDTO.getSteps().stream()
                .map(stepDTO -> testStepRepository.findById(stepDTO.getId())
                        .orElseThrow(() -> new RuntimeException("TestStep not found: " + stepDTO.getId())))
                .collect(Collectors.toList());

        skill.setTestSteps(steps);

        converter.convertToDTO(skillRepository.save(skill));
    }

    public void delete(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new RuntimeException("Skill not found with id: " + id);
        }
        skillRepository.deleteById(id);
    }

    public List<TestStepDTO> getTestStepsDTO(Long skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + skillId));

        return skill.getTestSteps().stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public SkillDTO setTestSteps(Long skillId, List<Long> testStepIds) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + skillId));

        List<TestStep> steps = testStepRepository.findAllById(testStepIds);

        if (steps.size() != testStepIds.size()) {
            throw new RuntimeException("One or more TestSteps not found");
        }

        skill.setTestSteps(steps);
        return converter.convertToDTO(skillRepository.save(skill));
    }
}
