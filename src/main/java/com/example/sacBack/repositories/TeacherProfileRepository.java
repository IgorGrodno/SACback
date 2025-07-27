package com.example.sacBack.repositories;


import com.example.sacBack.models.ntities.TeacherProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherProfileRepository extends JpaRepository<TeacherProfile, Long> {
}