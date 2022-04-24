package ru.zakirov.voiting_system.repository;

import org.springframework.data.jpa.repository.Query;
import ru.zakirov.voiting_system.model.Menu;

import java.util.List;

public interface MenuRepository extends BaseRepository<Menu> {

    @Query("select m from Menu m where m.restaurant.id = ?1")
    Menu getByRestaurantId(int restaurant_id);
}
