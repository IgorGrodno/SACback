package com.example.sacBack.models.ntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "skilltestresult")
public class SkillTestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long teacherId;

    @OneToMany(mappedBy = "skillTestResult", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderColumn(name = "position")
    private List<StepScoreEntry> stepScores = new ArrayList<>();

    private int lightMistakes;
    private int hardMistakes;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resultDate;
}

