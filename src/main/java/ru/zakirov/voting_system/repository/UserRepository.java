package ru.zakirov.voting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}