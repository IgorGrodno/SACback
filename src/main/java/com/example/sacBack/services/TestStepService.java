package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.repositories.TestStepRepository;

import com.example.sacBack.utils.EntityToDTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestStepService {

    private static final Logger logger = LoggerFactory.getLogger(TestStepService.class);

    private final TestStepRepository testStepRepository;
    private final EntityToDTOConverter converter;

    @Autowired
    public TestStepService(TestStepRepository testStepRepository, EntityToDTOConverter converter) {
        this.testStepRepository = testStepRepository;
        this.converter = converter;
    }

    public List<TestStepDTO> findAll() {
       List<TestStep> testSteps = testStepRepository.findAll();
       List<TestStepDTO> testStepDTOs = new ArrayList<>();
       for (TestStep testStep : testSteps) {
           TestStepDTO stepDTO = converter.convertToDTO(testStep);
           testStepDTOs.add(stepDTO);
       }
       return testStepDTOs;
    }

    public TestStepDTO findById(Long id) {
        TestStep step = testStepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestStep not found with id: " + id));
        TestStepDTO result = converter.convertToDTO(step);
        return result;
    }

    public TestStepDTO create(TestStepDTO dto) {
        TestStep newStep = new TestStep();
        newStep.setName(dto.getName());

        TestStep saved = testStepRepository.save(newStep);
        logger.info("Created TestStep with id {}", saved.getId());

        return converter.convertToDTO(saved);
    }

    public TestStepDTO update(Long id, TestStepDTO dto) {
        TestStep step = testStepRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TestStep not found with id: " + id));

        step.setName(dto.getName());
        TestStep updated = testStepRepository.save(step);

        logger.info("Updated TestStep with id {}", id);
        return converter.convertToDTO(updated);
    }

    public void delete(Long id) {
        if (!testStepRepository.existsById(id)) {
            throw new RuntimeException("TestStep not found with id: " + id);
        }
        testStepRepository.deleteById(id);
        logger.info("Deleted TestStep with id {}", id);
    }
}

