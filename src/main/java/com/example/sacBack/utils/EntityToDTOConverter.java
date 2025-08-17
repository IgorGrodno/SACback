package com.example.sacBack.utils;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.DTOs.ProfileDTO;
import com.example.sacBack.models.DTOs.TestStepDTO;
import com.example.sacBack.models.DTOs.UserDTO;
import com.example.sacBack.models.ntities.Skill;
import com.example.sacBack.models.ntities.Profile;
import com.example.sacBack.models.ntities.TestStep;
import com.example.sacBack.models.ntities.User;
import com.example.sacBack.repositories.TestStepRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EntityToDTOConverter {

    private final TestStepRepository stepRepository;

    public EntityToDTOConverter(TestStepRepository stepRepository) {
        this.stepRepository = stepRepository;
    }


    public SkillDTO convertToDTO (Skill skill) {
        SkillDTO skillDTO = new SkillDTO();
        skillDTO.setName(skill.getName());
        skillDTO.setId(skill.getId());
        List<TestStepDTO> testStepDTOList = new ArrayList<>();
        skill.getTestSteps().forEach(testStep -> {
            testStepDTOList.add(convertToDTO(testStep));
        });
        skillDTO.setSteps(testStepDTOList);
        return skillDTO;
    }

    public TestStepDTO convertToDTO(TestStep testStep) {
        TestStepDTO testStepDTO = new TestStepDTO();
        testStepDTO.setId(testStep.getId());
        testStepDTO.setName(testStep.getName());
        testStepDTO.setCanDelete(!stepRepository.existsByIdUsedInSkills(testStep.getId()));
        return testStepDTO;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        Set<String> roles = new HashSet<>();
        user.getRoles().forEach(role -> {roles.add(String.valueOf(role.getName()));});
        userDTO.setRoles(roles);
        return userDTO;
    }

    public ProfileDTO convertToDTO(Profile profile){
        ProfileDTO teacherProfileDTO = new ProfileDTO();
        teacherProfileDTO.setId(profile.getId());
        teacherProfileDTO.setFirstName(profile.getFirstName());
        teacherProfileDTO.setSecondName(profile.getSecondName());
        teacherProfileDTO.setFatherName(profile.getFatherName());
        return teacherProfileDTO;
    }
}
