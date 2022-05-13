package ru.zakirov.voting_system.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.zakirov.voting_system.model.Restaurant;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    @Query("select r from Restaurant r where upper(r.name) = upper(?1)")
    Optional<Restaurant> getByNameIgnoreCase(String description);

    @Query("select r from Restaurant r join fetch r.dishes d where r.id=?1 and d.date=?2")
    Optional<Restaurant> getWithTodayDishes(int id, LocalDate date);

    @Query("select r from Restaurant r join fetch r.dishes d where r.id=?1 and d.date=?2")
    Optional<Restaurant> getWithDishesWithSomeDate(int id, LocalDate date);
}