package com.github.rinatzzak.votingsystem.util;

import lombok.experimental.UtilityClass;
import com.github.rinatzzak.votingsystem.model.Dish;
import com.github.rinatzzak.votingsystem.to.DishTo;

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
