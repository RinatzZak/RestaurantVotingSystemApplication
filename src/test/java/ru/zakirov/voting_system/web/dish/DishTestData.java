package ru.zakirov.voting_system.web.dish;

import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static ru.zakirov.voting_system.web.restaurant.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final Dish dish1 = new Dish(DISH1_ID, "Rice with seafood", new BigDecimal("400.00"), restaurant1);
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "Soup puree", new BigDecimal("300.00"), restaurant2);
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "Royal roast beef", new BigDecimal("600.00"), restaurant3);
    public static final Dish dish4 = new Dish(DISH1_ID + 3, "Orange juice", new BigDecimal("10.00"), restaurant1);
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "Coffee", new BigDecimal("10.00"), restaurant2);
    public static final Dish dish6 = new Dish(DISH1_ID + 5, "Milk cocktail", new BigDecimal("20.00"), restaurant3);
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "Orange ice cream", new BigDecimal("50.00"), restaurant1);
    public static final Dish dish8 = new Dish(DISH1_ID + 7, "Cherry ice cream", new BigDecimal("55.00"), restaurant2);
    public static final Dish dish9 = new Dish(DISH1_ID + 8, "Apple ice cream", new BigDecimal("51.00"), restaurant3);

    public static final List<Dish> DISHES = Arrays.asList(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8, dish9);

    public static Dish getNew() {
        return new Dish(null, "NewDish", new BigDecimal(777), restaurant2);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "UpdatedDish", new BigDecimal(111), restaurant1);
    }
}
