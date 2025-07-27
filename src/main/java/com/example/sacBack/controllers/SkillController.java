package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.security.respose.MessageResponse;
import com.example.sacBack.services.SkillService;
import com.example.sacBack.services.TestStepService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final SkillService skillService;
    private final TestStepService testStepService;

    public SkillController(SkillService skillService, TestStepService testStepService) {
        this.skillService = skillService;
        this.testStepService = testStepService;
    }

    @GetMapping("/steps")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TestStepDTO>> getAllSteps() {
        return ResponseEntity.ok(testStepService.findAll());
    }

    @PostMapping("/steps")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addStep(@RequestBody TestStepDTO testStepDTO) {
        testStepService.create(testStepDTO);
        return ResponseEntity.ok(new MessageResponse("Step created successfully!"));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAll());
    }
}
