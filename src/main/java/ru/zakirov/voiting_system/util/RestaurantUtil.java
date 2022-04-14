package ru.zakirov.voiting_system.util;

import lombok.experimental.UtilityClass;
import ru.zakirov.voiting_system.model.Restaurant;
import ru.zakirov.voiting_system.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getDescription(), restaurantTo.getAddress());
    }

    public static Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setDescription(restaurantTo.getDescription());
        restaurant.setAddress(restaurantTo.getAddress());
        return restaurant;
    }
}
