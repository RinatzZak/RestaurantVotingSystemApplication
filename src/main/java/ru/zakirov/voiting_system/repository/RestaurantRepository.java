package ru.zakirov.voiting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voiting_system.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> getByDescriptionIgnoreCase(String description);
}