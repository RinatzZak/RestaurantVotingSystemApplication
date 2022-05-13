package ru.zakirov.voting_system.util;

import lombok.experimental.UtilityClass;
import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.to.DishTo;

@UtilityClass
public class DishUtil {

    public Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getDate(), dishTo.getPrice());
    }

    public Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setDate(dishTo.getDate());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }
}
