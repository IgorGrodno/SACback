package com.example.sacBack.models.ntities;

import com.example.sacBack.security.models.TeacherProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderColumn(name = "position")
    @JoinTable(
            name = "skill_teststep",
            joinColumns = @JoinColumn(name = "skill_id"),
            inverseJoinColumns = @JoinColumn(name = "teststep_id")
    )
    private List<TestStep> testSteps = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<TeacherProfile> teacherProfiles = new ArrayList<>();
}

