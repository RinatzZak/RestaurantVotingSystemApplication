package com.github.rinatzzak.votingsystem.util;

import lombok.experimental.UtilityClass;
import com.github.rinatzzak.votingsystem.model.Restaurant;
import com.github.rinatzzak.votingsystem.to.RestaurantTo;

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
