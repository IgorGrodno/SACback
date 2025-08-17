package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.ProfileDTO;
import com.example.sacBack.models.DTOs.ProfileUpdateRequest;
import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.ntities.Profile;
import com.example.sacBack.models.ntities.Skill;
import com.example.sacBack.services.ProfileService;
import com.example.sacBack.services.SkillService;
import com.example.sacBack.services.UserService;
import org.hibernate.mapping.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/profile")
@PreAuthorize("isAuthenticated()")
public class ProfileController {

    private final UserService userService;
private final ProfileService profileService;
private final SkillService skillService;

    public ProfileController(UserService userService, ProfileService profileService, SkillService skillService) {
        this.userService = userService;
        this.profileService = profileService;
        this.skillService = skillService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileDTO> getProfileByUserId(@PathVariable long id) {
        return ResponseEntity.ok(userService.getProfileByUserId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfileDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody ProfileUpdateRequest request
    ) {
        ProfileDTO profileData = request.getProfile();
        List<SkillDTO> skills = request.getSkills();

        Profile profile = profileService.getProfile(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        profile.setFirstName(profileData.getFirstName());
        profile.setSecondName(profileData.getSecondName());
        profile.setFatherName(profileData.getFatherName());

        List<Skill> newSkills = skills.stream()
                .map(s -> skillService.findById(s.getId()))
                .collect(Collectors.toList());

        profile.setSkills(newSkills);
        profileService.saveProfile(profile);

        return ResponseEntity.ok(profileService.getProfileDTO(profile.getId()));
    }


}
