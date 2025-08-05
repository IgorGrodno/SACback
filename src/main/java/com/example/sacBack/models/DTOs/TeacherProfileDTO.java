package com.example.sacBack.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeacherProfileDTO {
    private Long id;
    private Long userId;
    private String userName;
    private List<SkillDTO> skills = new ArrayList<>();
}
