package com.example.sacBack.services;

import com.example.sacBack.models.ntities.Session;
import com.example.sacBack.repositories.SessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public Session create(Session session) {
        return sessionRepository.save(session);
    }

    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    public Optional<Session> findById(Long id) {
        return sessionRepository.findById(id);
    }

    @Transactional
    public Optional<Session> update(Long id, Session updatedSession) {
        return sessionRepository.findById(id)
                .map(existing -> {
                    existing.setStartDate(updatedSession.getStartDate());
                    existing.setStudentNumbers(updatedSession.getStudentNumbers());
                    return existing;
                });
    }

    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

    public Optional<Session> findActive(){
        return sessionRepository.findByActiveTrue();
    }
}
