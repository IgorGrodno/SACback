package com.example.sacBack.models.DTOs;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class ProfileUpdateRequest {
    private ProfileDTO profile;
    private List<SkillDTO> skills;
}
