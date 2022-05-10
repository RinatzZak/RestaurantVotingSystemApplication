package ru.zakirov.voting_system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Vote;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.user.id = ?1")
    List<Vote> findAllById(int userId);

    @Query("select v from Vote v where v.user.id = ?1 and v.date = ?2")
    Optional<Vote> findVoteByIdAndDate(int userId, @NotNull LocalDate date);
}