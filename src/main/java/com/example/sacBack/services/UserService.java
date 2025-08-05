package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.TeacherProfileDTO;
import com.example.sacBack.models.DTOs.UserDTO;
import com.example.sacBack.models.ntities.*;
import com.example.sacBack.repositories.RoleRepository;
import com.example.sacBack.repositories.StudentProfileRepository;
import com.example.sacBack.repositories.TeacherProfileRepository;
import com.example.sacBack.repositories.UserRepository;
import com.example.sacBack.utils.NtityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final NtityToDTOConverter converter;
    private final TeacherProfileRepository teacherProfileRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final NtityToDTOConverter ntityToDTOConverter;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       NtityToDTOConverter converter,
                       TeacherProfileRepository teacherProfileRepository,
                       StudentProfileRepository studentProfileRepository, NtityToDTOConverter ntityToDTOConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.teacherProfileRepository = teacherProfileRepository;
        this.studentProfileRepository = studentProfileRepository;
        this.ntityToDTOConverter = ntityToDTOConverter;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(converter::convertToDTO)
                .toList();
    }

    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userDTO.getId()));

        user.setUsername(userDTO.getUsername());
        Set<Role> roles = new HashSet<>();

        for (String roleStr : userDTO.getRoles()) {
            ERole eRole = ERole.valueOf(roleStr);
            Role role = roleRepository.findByName(eRole);
            roles.add(role);

            if (eRole == ERole.ROLE_TEACHER && user.getTeacherProfile() == null) {
                createTeacherProfileForUser(user);
            }

            if (eRole == ERole.ROLE_STUDENT && user.getStudentProfile() == null) {
                createStudentProfileForUser(user);
            }
        }

        user.setRoles(roles);

        converter.convertToDTO(userRepository.save(user));
    }

    private void createTeacherProfileForUser(User user) {
        TeacherProfile profile = new TeacherProfile();
        profile.setUser(user);
        teacherProfileRepository.save(profile);
        user.setTeacherProfile(profile);
    }

    private void createStudentProfileForUser(User user) {
        StudentProfile profile = new StudentProfile();
        profile.setUser(user);
        studentProfileRepository.save(profile);
        user.setStudentProfile(profile);
    }

    public TeacherProfileDTO getTeacherProfileByUserId(Long id) {
        User user = getUserById(id);
        return ntityToDTOConverter.convertToDTO(user.getTeacherProfile());
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
