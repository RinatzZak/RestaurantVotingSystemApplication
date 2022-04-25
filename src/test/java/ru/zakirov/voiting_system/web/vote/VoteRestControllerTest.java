package ru.zakirov.voiting_system.web.vote;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zakirov.voiting_system.repository.VoteRepository;
import ru.zakirov.voiting_system.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zakirov.voiting_system.web.meal.MealTestData.*;
import static ru.zakirov.voiting_system.web.meal.MealTestData.MEAL1_ID;
import static ru.zakirov.voiting_system.web.user.UserTestData.USER_MAIL;
import static ru.zakirov.voiting_system.web.vote.VoteTestData.*;

class VoteRestControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(voteList));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
        mocked.when(LocalTime::now).thenReturn(LocalTime.of(10, 3 ));
        perform(MockMvcRequestBuilders.delete("/api/1/votes"))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(voteRepository.findById(VOTE1_ID).isPresent());
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }
}