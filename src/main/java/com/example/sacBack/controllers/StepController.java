package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.security.respose.MessageResponse;
import com.example.sacBack.services.TestStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@PreAuthorize("hasRole('ADMIN')")
public class StepController {

    private static final Logger logger = LoggerFactory.getLogger(StepController.class);

    private final TestStepService testStepService;

    public StepController(TestStepService testStepService) {
        this.testStepService = testStepService;
    }

    @GetMapping
    public ResponseEntity<List<TestStepDTO>> getAllSteps() {
        logger.info("Fetching all test steps");
        return ResponseEntity.ok(testStepService.findAll());
    }

    @PostMapping
    public ResponseEntity<MessageResponse> addStep(@RequestBody TestStepDTO testStepDTO) {
        logger.info("Creating new test step: {}", testStepDTO.getName());
        testStepService.create(testStepDTO);
        return ResponseEntity.ok(new MessageResponse("Step created successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteStep(@PathVariable long id) {
        logger.info("Deleting test step with id: {}", id);
        testStepService.delete(id);
        return ResponseEntity.ok(new MessageResponse("Step deleted successfully!"));
    }
}

