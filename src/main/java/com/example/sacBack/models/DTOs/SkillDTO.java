package com.example.sacBack.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SkillDTO {
    private Long id;
    private String name;
    private List<TestStepDTO> steps;
}
