package ru.zakirov.voting_system.web.vote;

import ru.zakirov.voting_system.model.Vote;
import ru.zakirov.voting_system.web.MatcherFactory;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.zakirov.voting_system.web.restaurant.RestaurantTestData.*;
import static ru.zakirov.voting_system.web.user.UserTestData.admin;
import static ru.zakirov.voting_system.web.user.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user");

    public static final int VOTE1_ID = 1;

    public static Vote vote1 = new Vote(VOTE1_ID, LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
            restaurant1, user);

    public static List<Vote> voteList = List.of(vote1);

    public static Vote getNew() {
        return new Vote(null, LocalTime.now().truncatedTo(ChronoUnit.MINUTES), restaurant2, admin);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE1_ID, LocalTime.now().truncatedTo(ChronoUnit.MINUTES), restaurant3, user);
    }
}
