package ru.zakirov.voting_system.repository;

import org.springframework.data.jpa.repository.Query;
import ru.zakirov.voting_system.model.Menu;

public interface MenuRepository extends BaseRepository<Menu> {

    @Query("select m from Menu m where m.restaurant.id = ?1")
    Menu getByRestaurantId(int restaurant_id);
}
