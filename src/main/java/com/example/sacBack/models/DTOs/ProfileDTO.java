package com.example.sacBack.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    private Long id;
    private String firstName;
    private String secondName;
    private String fatherName;
}
