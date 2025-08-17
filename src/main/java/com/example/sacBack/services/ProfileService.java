package com.example.sacBack.services;

import com.example.sacBack.models.DTOs.ProfileDTO;
import com.example.sacBack.models.ntities.Profile;
import com.example.sacBack.repositories.ProfileRepository;
import com.example.sacBack.utils.EntityToDTOConverter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EntityToDTOConverter entityToDTOConverter;

    public ProfileService(ProfileRepository profileRepository, EntityToDTOConverter entityToDTOConverter) {
        this.profileRepository = profileRepository;
        this.entityToDTOConverter = entityToDTOConverter;
    }

    public Optional<Profile> getProfile(Long id) {
        return profileRepository.findById(id);
    }

    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public ProfileDTO getProfileDTO(Long id) {
        return entityToDTOConverter.convertToDTO(profileRepository.findById(id).get());
    }
}
