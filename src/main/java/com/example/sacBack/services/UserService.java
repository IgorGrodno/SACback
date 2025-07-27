package com.example.sacBack.services;

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
    private final NtityToDTOConverter ntityToDTOConverter;
    private final TeacherProfileRepository teacherProfileRepository;
    private final StudentProfileRepository studentProfileRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       NtityToDTOConverter ntityToDTOConverter, TeacherProfileRepository teacherProfileRepository,
                       StudentProfileRepository studentProfileRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.ntityToDTOConverter = ntityToDTOConverter;
        this.teacherProfileRepository = teacherProfileRepository;
        this.studentProfileRepository = studentProfileRepository;
    }


    public List<UserDTO> getAllUsers() {
        List<UserDTO> usersDTO = new ArrayList<>();
        userRepository.findAll().forEach(user -> {usersDTO.add(ntityToDTOConverter.convertToDTO(user));});
        return usersDTO;
    }

    public User getUserById(long id) {return userRepository.getReferenceById(id);}

    public void deleteUser(long id){
        userRepository.deleteById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(UserDTO user) {
        User oldUser = userRepository.getReferenceById(user.getId());
        oldUser.setUsername(user.getUsername());
        Set<Role> roles = new HashSet<>();
        user.getRoles().forEach(role -> {
            roles.add(roleRepository.findByName(ERole.valueOf(role)));
            if(Objects.equals(role, "ROLE_TEACHER") &&oldUser.getTeacherProfile()==null){
                TeacherProfile teacherProfile = new TeacherProfile();
                teacherProfile.setUser(oldUser);
                teacherProfileRepository.save(teacherProfile);
                oldUser.setTeacherProfile(teacherProfile);
            }
            if (Objects.equals(role, "ROLE_STUDENT") && oldUser.getStudentProfile() == null) {
                StudentProfile studentProfile = new StudentProfile();
                studentProfile.setUser(oldUser);
                studentProfileRepository.save(studentProfile);
                oldUser.setStudentProfile(studentProfile);
            }
        });
        oldUser.setRoles(roles);
        return userRepository.save(oldUser);
    }

    public TeacherProfile getTeacherProfileById(long id) {
       return userRepository.getReferenceById(id).getTeacherProfile();
    }

    public StudentProfile getStudentProfileById(long id) {
        return userRepository.getReferenceById(id).getStudentProfile();
    }
}
