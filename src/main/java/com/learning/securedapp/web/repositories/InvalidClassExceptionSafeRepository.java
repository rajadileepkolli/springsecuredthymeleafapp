package com.learning.securedapp.web.repositories;

import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.stereotype.Component;

@Component
public class InvalidClassExceptionSafeRepository<S extends ExpiringSession> implements SessionRepository<S> {
    private final SessionRepository<S> repository;

    public InvalidClassExceptionSafeRepository(SessionRepository<S> repository) {
        this.repository = repository;
    }

    public S getSession(String id) {
        try {
            return repository.getSession(id);
        } catch(SerializationException e) {
            delete(id);
            return null;
        }
    }

    public S createSession() {
        return repository.createSession();
    }

    public void save(S session) {
        repository.save(session);
    }

    public void delete(String id) {
        repository.delete(id);
    }
}
