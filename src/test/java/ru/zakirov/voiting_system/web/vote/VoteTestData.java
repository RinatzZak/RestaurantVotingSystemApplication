package ru.zakirov.voiting_system.web.vote;

import ru.zakirov.voiting_system.model.Meal;
import ru.zakirov.voiting_system.model.Vote;
import ru.zakirov.voiting_system.web.MatcherFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.zakirov.voiting_system.web.restaurant.RestaurantTestData.*;
import static ru.zakirov.voiting_system.web.user.UserTestData.admin;
import static ru.zakirov.voiting_system.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = 1;
    public static final int NOT_FOUND = 100;

    public static Vote vote1 = new Vote(VOTE1_ID, LocalTime.of(10,3), restaurant1, user);

    public static List<Vote> voteList = List.of(vote1);

    public static Vote getNew() {
        return new Vote(null, LocalTime.now().truncatedTo(ChronoUnit.MILLIS), restaurant2, admin);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalTime.now(), restaurant3, user);
    }
}
