package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakirov.voiting_system.model.Meal;

public interface MealRepository extends JpaRepository<Meal, Integer> {
}