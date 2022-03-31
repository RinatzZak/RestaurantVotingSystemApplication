package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakirov.voiting_system.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}