package com.example.sacBack.models.DTOs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TestStepDTO {
    private Long id;
    private String name;
    private boolean canDelete;
}
