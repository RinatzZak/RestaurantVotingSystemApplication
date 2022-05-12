package ru.zakirov.voting_system.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Restaurant;

import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    Optional<Restaurant> getByNameIgnoreCase(String description);
}