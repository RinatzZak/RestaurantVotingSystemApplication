package ru.zakirov.voting_system.web.vote;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.zakirov.voting_system.web.user.UserTestData.ADMIN_MAIL;
import static ru.zakirov.voting_system.web.user.UserTestData.USER_MAIL;
import static ru.zakirov.voting_system.web.vote.VoteTestData.*;

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
    void delete() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(10, 59));
            perform(MockMvcRequestBuilders.delete("/api/1/votes"))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            assertFalse(voteRepository.findById(VOTE1_ID).isPresent());
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteAfterElevenClock() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(12, 0));
            perform(MockMvcRequestBuilders.delete("/api/1/votes"))
                    .andDo(print())
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post("/api/2/votes/2/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        Vote created = VOTE_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createSecondVote() throws Exception {
        Vote newVote = VoteTestData.getNew();
        perform(MockMvcRequestBuilders.post("/api/1/votes/3/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(6, 11));
            Vote updated = VoteTestData.getUpdated();
            perform(MockMvcRequestBuilders.put("/api/1/votes/3/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception ignored) {
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterElevenClock() {
        try {
            MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class);
            mocked.when(LocalTime::now).thenReturn(LocalTime.of(11, 1));
            Vote updated = VoteTestData.getUpdated();
            perform(MockMvcRequestBuilders.put("/api/1/votes/3/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isUnprocessableEntity());
        } catch (Exception ignored) {
        }
    }
}