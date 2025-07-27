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

    private Optional<Skill> findById(Long id) {
        return skillRepository.findById(id);
    }

    public Skill create(Skill skill) {
        return skillRepository.save(skill);
    }

    public Optional<Skill> update(Long id, Skill updatedSkill) {
        return skillRepository.findById(id).map(existing -> {
            existing.setName(updatedSkill.getName());
            existing.setTestSteps(updatedSkill.getTestSteps());
            return skillRepository.save(existing);
        });
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
