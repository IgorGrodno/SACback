package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.ProfileDTO;
import com.example.sacBack.models.DTOs.UserDTO;
import com.example.sacBack.models.ntities.*;
import com.example.sacBack.repositories.RoleRepository;
import com.example.sacBack.repositories.StudentProfileRepository;
import com.example.sacBack.repositories.ProfileRepository;
import com.example.sacBack.repositories.UserRepository;
import com.example.sacBack.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EntityToDTOConverter converter;
    private final EntityToDTOConverter entityToDTOConverter;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       EntityToDTOConverter converter,
                       ProfileRepository teacherProfileRepository,
                       StudentProfileRepository studentProfileRepository, ProfileRepository profileRepository, EntityToDTOConverter entityToDTOConverter) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.converter = converter;
        this.entityToDTOConverter = entityToDTOConverter;
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
        }
        user.setRoles(roles);
        converter.convertToDTO(userRepository.save(user));
    }

    public ProfileDTO getProfileByUserId(Long id) {
        return entityToDTOConverter.convertToDTO(userRepository.findById(id).get().getProfile());
    }

    public void save(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            if (user.getProfile() == null) {
                Profile profile = new Profile();
                profile.setUser(user);
                user.setProfile(profile);
            }
        }
        userRepository.save(user);
    }



    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
