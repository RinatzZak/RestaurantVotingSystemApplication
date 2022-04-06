package ru.zakirov.voiting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.Meal;

import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {
    List<Meal> getAllByRestaurantId(int restaurant_id);
}