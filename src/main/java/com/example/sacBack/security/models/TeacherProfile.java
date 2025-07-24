package com.example.sacBack.security.models;

import com.example.sacBack.models.ntities.Skill;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TeacherProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();
}

