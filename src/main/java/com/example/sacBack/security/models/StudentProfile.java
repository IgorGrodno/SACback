package com.example.sacBack.security.models;

import jakarta.persistence.*;

@Entity
public class StudentProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;
}

