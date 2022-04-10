package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.Vote;

public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    Vote findByUserId(int user_id);
}