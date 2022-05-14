package com.github.rinatzzak.votingsystem.repository;

import org.springframework.transaction.annotation.Transactional;
import com.github.rinatzzak.votingsystem.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}