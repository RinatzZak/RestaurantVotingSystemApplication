package ru.zakirov.voiting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}