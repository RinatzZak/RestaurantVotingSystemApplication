package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakirov.voiting_system.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
}