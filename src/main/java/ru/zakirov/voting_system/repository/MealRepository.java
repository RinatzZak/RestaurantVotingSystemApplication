package ru.zakirov.voting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Meal;

import java.util.Optional;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {
    Optional<Meal> getByDescriptionIgnoreCase(String description);
}