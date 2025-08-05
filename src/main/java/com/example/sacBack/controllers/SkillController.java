package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.services.SkillService;
import com.example.sacBack.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@PreAuthorize("hasRole('ADMIN')")
public class SkillController {

    private static final Logger logger = LoggerFactory.getLogger(SkillController.class);

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkillById(@PathVariable long id) {
        return ResponseEntity.ok(skillService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addSkill(@RequestBody SkillDTO skillDTO) {
        logger.info("Creating skill: {}", skillDTO.getName());
        skillService.create(skillDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Skill created successfully!"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateSkill(@PathVariable long id, @RequestBody SkillDTO skillDTO) {
        logger.info("Updating skill with id {}", id);
        skillService.update(id, skillDTO);
        return ResponseEntity.ok(new ApiResponse(true, "Skill updated successfully!"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(@PathVariable long id) {
        logger.info("Deleting skill with id {}", id);
        skillService.delete(id);
        return ResponseEntity.ok(new ApiResponse(true, "Skill deleted successfully!"));
    }

}
