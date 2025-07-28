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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;
    private final TestStepRepository testStepRepository;
    private final NtityToDTOConverter ntityToDTOConverter;

    @Autowired
    public SkillService(SkillRepository skillRepository, TestStepRepository testStepRepository,
                        NtityToDTOConverter ntityToDTOConverter) {
        this.skillRepository = skillRepository;
        this.testStepRepository = testStepRepository;
        this.ntityToDTOConverter = ntityToDTOConverter;
    }

    public List<SkillDTO> findAll() {
        List<SkillDTO> result = new ArrayList<>();
        skillRepository.findAll().forEach(skill -> {
            result.add(ntityToDTOConverter.convertToDTO(skill));
        });
        return result;
    }

    public SkillDTO findById(Long id) {

        return ntityToDTOConverter.convertToDTO(Objects.requireNonNull(skillRepository.findById(id).orElse(null)));
    }

    public SkillDTO create(SkillDTO skill) {
        Skill newSkill = new Skill();
        newSkill.setName(skill.getName());
        List<TestStep> testSteps = new ArrayList<>();
        skill.getSteps().forEach(testStepDTO -> {
            testSteps.add(testStepRepository.findById(testStepDTO.getId()).orElse(null));
        });
        newSkill.setTestSteps(testSteps);
        return ntityToDTOConverter.convertToDTO(skillRepository.save(newSkill));
    }

    public Optional<Skill> update(Long id, SkillDTO updatedSkill) {
        Skill skill = skillRepository.findById(id).orElse(null);
        skill.setName(updatedSkill.getName());
        List<TestStep> testSteps = new ArrayList<>();
        updatedSkill.getSteps().forEach(stepDTO -> {
            testSteps.add(testStepRepository.findById(stepDTO.getId()).orElse(null));
        });
        skill.setTestSteps(testSteps);
        skillRepository.save(skill);
        return Optional.of(skill);
    }

    public void delete(Long id) {
        skillRepository.deleteById(id);
    }

    public List<TestStepDTO> getTestStepsDTO(Long skillId) {
        List<TestStepDTO> results = new ArrayList<>();
        skillRepository.findById(skillId).get().getTestSteps().forEach(testStep ->
        {
            results.add(ntityToDTOConverter.convertToDTO(testStep));
        });
        return results;
    }

    public Skill setTestSteps(Long skillId, List<Long> testStepIds) {
        Optional<Skill> skillOpt = skillRepository.findById(skillId);
        List<TestStep> steps = testStepRepository.findAllById(testStepIds);
        Skill skill = skillOpt.get();
        skill.setTestSteps(steps);
        return skillRepository.save(skill);
    }
}
