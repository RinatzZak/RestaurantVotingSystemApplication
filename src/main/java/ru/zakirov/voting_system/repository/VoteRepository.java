package ru.zakirov.voting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    Vote findByUserId(int user_id);
}