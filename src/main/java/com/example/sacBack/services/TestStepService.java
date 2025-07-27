package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.repositories.SkillRepository;
import com.example.sacBack.repositories.TestStepRepository;

import com.example.sacBack.utils.NtityToDTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestStepService {

    private static final Logger logger = LoggerFactory.getLogger(TestStepService.class);

    private final TestStepRepository repository;
    private final TestStepRepository testStepRepository;
    private final NtityToDTOConverter ntityToDTOConverter;

    @Autowired
    public TestStepService(TestStepRepository repository, SkillRepository skillRepository,
                           TestStepRepository testStepRepository, NtityToDTOConverter ntityToDTOConverter) {
        this.repository = repository;
        this.testStepRepository = testStepRepository;
        this.ntityToDTOConverter = ntityToDTOConverter;
    }

    public List<TestStepDTO> findAll() {
        List<TestStepDTO> result = new ArrayList<>();
        for (TestStep testStep : repository.findAll()) {
            result.add(ntityToDTOConverter.convertToDTO(testStep));
            logger.info(testStep.getDescription());
        }
        return result;
    }

    public Optional<TestStep> findById(Long id) {
        return repository.findById(id);
    }

    public void create(TestStepDTO step) {
            TestStep newStep = new TestStep();
            newStep.setDescription(step.getDescription());
        testStepRepository.save(newStep);
    }

    public Optional<TestStep> update(Long id, TestStepDTO updatedStep) {
        return repository.findById(id).map(existing -> {
            existing.setDescription(updatedStep.getDescription());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
