package com.example.sacBack.services;

import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.repositories.SkillRepository;
import com.example.sacBack.repositories.TestStepRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestStepService {

    private final TestStepRepository repository;

    @Autowired
    public TestStepService(TestStepRepository repository, SkillRepository skillRepository) {
        this.repository = repository;
    }

    public List<TestStep> findAll() {
        return repository.findAll();
    }

    public Optional<TestStep> findById(Long id) {
        return repository.findById(id);
    }

    public TestStep create(TestStep step) {
        return repository.save(step);
    }

    public Optional<TestStep> update(Long id, TestStep updatedStep) {
        return repository.findById(id).map(existing -> {
            existing.setDescription(updatedStep.getDescription());
            return repository.save(existing);
        });
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
