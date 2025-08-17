package com.example.sacBack.models.ntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    private String firstName;
    private String secondName;
    private String fatherName;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Skill> skills = new ArrayList<>();
}

