package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.TeacherProfileDTO;
import com.example.sacBack.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TeacherProfileDTO> getProfileDTO(@PathVariable long id) {
        return ResponseEntity.ok(userService.getTeacherProfileByUserId(id));
    }
}
