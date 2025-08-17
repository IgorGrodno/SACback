package com.example.sacBack.repositories;


import com.example.sacBack.models.ntities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}