package ru.zakirov.voting_system.web.restaurant;

import ru.zakirov.voting_system.model.Restaurant;
import ru.zakirov.voting_system.web.MatcherFactory;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.zakirov.voting_system.web.dish.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class);
    public static final MatcherFactory.Matcher<Restaurant>RESTAURANT_WITH_DISH_MATCHER = MatcherFactory.usingAssertions(Restaurant.class,
            (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("dishes.restaurant").isEqualTo(e),
            (a, e) -> {
                throw new UnsupportedOperationException();
            });

    public static final int RESTAURANT1_ID = 1;
    public static final int NOT_FOUND_ID = 100;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "Kazuya", "193 Synods Street Eden Terrace");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "Ahi", "Commercial Bay Level 2/7 Queen Street");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "Culprit", "Level 1/12 Wyndham Street");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT1_ID + 3, "Hello Beasty", "95-97 Customs Street West Market Square");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3, restaurant4);

    static {
        restaurant1.setDishes(List.of(dish7));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "NewRestaurant", "NewAddress 777");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "UpdatedName", "UpdatedAddress");
    }
}
