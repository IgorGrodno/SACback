package com.example.sacBack.models.ntities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StudentProfile {
    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;
}

