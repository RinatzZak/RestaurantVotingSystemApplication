package ru.zakirov.voiting_system.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.zakirov.voiting_system.model.Vote;
import ru.zakirov.voiting_system.repository.RestaurantRepository;
import ru.zakirov.voiting_system.repository.UserRepository;
import ru.zakirov.voiting_system.repository.VoteRepository;

import java.time.LocalTime;
import java.util.List;

import static ru.zakirov.voiting_system.util.validation.ValidationUtil.checkTime;

@RestController
@Slf4j
public class VoteRestController {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteRestController(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                              UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/api/profile/{user_id}/votes")
    public Vote get(@PathVariable int user_id) {
        log.info("take vote for user{}", user_id);
        return voteRepository.findByUserId(user_id);
    }

    @GetMapping("/api/profile/votes")
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.findAll();
    }

    @DeleteMapping("/api/profile/{user_id}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int user_id) {
        voteRepository.delete(voteRepository.findByUserId(user_id));
    }

    @PostMapping("/api/profile/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void create(@PathVariable int user_id, @PathVariable int restaurant_id) throws Exception {
        Vote vote = voteRepository.findByUserId(user_id);
        if (vote == null) {
            log.info("add voice by user{} for restaurant{}", user_id, restaurant_id);
            vote = new Vote(LocalTime.now(), restaurantRepository.getById(restaurant_id),
                    userRepository.getById(user_id));
            voteRepository.save(vote);
        } else {
            throw new Exception("You already voted!");
        }
    }

    @PutMapping("/api/profile/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(@PathVariable int user_id, @PathVariable int restaurant_id) throws Exception {
        Vote vote = voteRepository.findByUserId(user_id);
        if (vote != null && checkTime()) {
            log.info("update voice by user{} for restaurant{}", user_id, restaurant_id);
            vote.setRestaurant(restaurantRepository.getById(restaurant_id));
            voteRepository.save(vote);
        } else {
            throw new Exception("No no no, you can't change your choose after 11!");
        }
    }
}
