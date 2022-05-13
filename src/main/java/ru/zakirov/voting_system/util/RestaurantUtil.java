package ru.zakirov.voting_system.util;

import lombok.experimental.UtilityClass;
import ru.zakirov.voting_system.model.Restaurant;
import ru.zakirov.voting_system.to.RestaurantTo;

@UtilityClass
public class RestaurantUtil {

    public Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress());
    }

    public Restaurant updateFromTo(Restaurant restaurant, RestaurantTo restaurantTo) {
        restaurant.setName(restaurantTo.getName());
        restaurant.setAddress(restaurantTo.getAddress());
        return restaurant;
    }
}
