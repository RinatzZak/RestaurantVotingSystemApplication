package ru.zakirov.voting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Dish;

import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
    Optional<Dish> getByDescriptionIgnoreCase(String description);
}