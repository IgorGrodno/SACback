package com.example.sacBack.models.ntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class StepScoreEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TestStep testStep;

    private Integer score;

    @ManyToOne
    private SkillTestResult skillTestResult;
}

