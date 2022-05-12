package ru.zakirov.voting_system.web.meal;

import ru.zakirov.voting_system.model.Dish;
import ru.zakirov.voting_system.web.MatcherFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class);

    public static final int DISH1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static final Dish DISH_1 = new Dish(DISH1_ID, "Rice with seafood", new BigDecimal("400.00"));
    public static final Dish DISH_2 = new Dish(DISH1_ID + 1, "Soup puree", new BigDecimal("300.00"));
    public static final Dish DISH_3 = new Dish(DISH1_ID + 2, "Royal roast beef", new BigDecimal("600.00"));
    public static final Dish DISH_4 = new Dish(DISH1_ID + 3, "Orange juice", new BigDecimal("10.00"));
    public static final Dish DISH_5 = new Dish(DISH1_ID + 4, "Coffee", new BigDecimal("10.00"));
    public static final Dish DISH_6 = new Dish(DISH1_ID + 5, "Milk cocktail", new BigDecimal("20.00"));
    public static final Dish DISH_7 = new Dish(DISH1_ID + 6, "Orange ice cream", new BigDecimal("50.00"));
    public static final Dish DISH_8 = new Dish(DISH1_ID + 7, "Cherry ice cream", new BigDecimal("55.00"));
    public static final Dish DISH_9 = new Dish(DISH1_ID + 8, "Apple ice cream", new BigDecimal("51.00"));

    public static final List<Dish> DISHES = Arrays.asList(DISH_1, DISH_2, DISH_3, DISH_4, DISH_5, DISH_6, DISH_7, DISH_8, DISH_9);

    public static Dish getNew() {
        return new Dish(null, "NewDish", new BigDecimal(777));
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "UpdatedDish", new BigDecimal(111));
    }
}