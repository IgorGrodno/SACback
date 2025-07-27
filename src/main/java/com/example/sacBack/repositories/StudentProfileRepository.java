package com.example.sacBack.repositories;


import com.example.sacBack.models.ntities.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
}
