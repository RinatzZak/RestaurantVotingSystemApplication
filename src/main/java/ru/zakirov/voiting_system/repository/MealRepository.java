package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.model.Restaurant;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Restaurant> {
}