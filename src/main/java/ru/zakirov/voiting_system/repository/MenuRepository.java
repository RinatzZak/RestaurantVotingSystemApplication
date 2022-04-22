package ru.zakirov.voiting_system.repository;

import ru.zakirov.voiting_system.model.Menu;

public interface MenuRepository extends BaseRepository<Menu> {

    Menu getByRestaurantId(int restaurant_id);
}
