package ru.zakirov.voting_system.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.zakirov.voting_system.model.Vote;
import ru.zakirov.voting_system.repository.VoteRepository;
import ru.zakirov.voting_system.util.JsonUtil;
import ru.zakirov.voting_system.web.AbstractControllerTest;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zakirov.voting_system.util.validation.ValidationUtil.FRONTIER;
import static ru.zakirov.voting_system.util.validation.ValidationUtil.setFRONTIER;
import static ru.zakirov.voting_system.web.user.UserTestData.ADMIN_MAIL;
import static ru.zakirov.voting_system.web.user.UserTestData.USER_MAIL;
import static ru.zakirov.voting_system.web.vote.VoteTestData.*;


class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL = VoteController.REST_URL + '/';

    @Autowired
    private VoteRepository repository;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllMyVotes() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "my-votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(myVotes));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "all-today-votes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(votesToday));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getTodayVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_MATCHER.contentJson(vote4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        assertEquals(repository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createSecondVote() throws Exception {
        Vote newVote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateForAll() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        if (FRONTIER.isAfter(LocalTime.now())) {
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", "2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());
            assertEquals(repository.getById(VOTE1_ID + 3), VoteTestData.getUpdated());
        } else {
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", "2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isUnprocessableEntity());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeFrontier() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        setFRONTIER(LocalTime.now().plusMinutes(1));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertEquals(repository.getById(VOTE1_ID + 3), VoteTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterFrontier() throws Exception {
        Vote updated = VoteTestData.getUpdated();
        setFRONTIER(LocalTime.now().minusMinutes(1));
        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", "2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isUnprocessableEntity());
    }

}