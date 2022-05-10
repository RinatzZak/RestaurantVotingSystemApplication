package ru.zakirov.voting_system.web.vote;

import ru.zakirov.voting_system.model.Vote;
import ru.zakirov.voting_system.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.zakirov.voting_system.web.restaurant.RestaurantTestData.*;
import static ru.zakirov.voting_system.web.user.UserTestData.admin;
import static ru.zakirov.voting_system.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user.password", "user.registered");

    public static final int VOTE1_ID = 1;

    public static Vote vote1 = new Vote(VOTE1_ID, LocalDate.of(2020, 10, 10), restaurant1, user);
    public static Vote vote2 = new Vote(VOTE1_ID + 1, LocalDate.of(2021, 11, 11), restaurant2, user);
    public static Vote vote3 = new Vote(VOTE1_ID + 2, LocalDate.of(2022, 1, 1), restaurant3, user);

    public static Vote vote4 = new Vote(VOTE1_ID + 3, LocalDate.now(), restaurant1, user);

    public static List<Vote> myVotes = List.of(vote1, vote2, vote3);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), restaurant2, user);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalDate.now(), restaurant3, user);
    }
}
