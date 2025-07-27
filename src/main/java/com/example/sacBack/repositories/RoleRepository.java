package com.example.sacBack.repositories;


import com.example.sacBack.models.ntities.ERole;
import com.example.sacBack.models.ntities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
