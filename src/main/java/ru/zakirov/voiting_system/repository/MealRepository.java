package ru.zakirov.voiting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.Meal;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {
}