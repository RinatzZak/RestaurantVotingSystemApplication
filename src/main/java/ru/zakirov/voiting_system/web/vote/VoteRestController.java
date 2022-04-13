package ru.zakirov.voiting_system.web.vote;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = "votes")
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

    @GetMapping("/api/profile/votes")
    public List<Vote> getAll() {
        log.info("getAll");
        return voteRepository.findAll();
    }

    @SneakyThrows
    @DeleteMapping("/api/profile/{user_id}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int user_id) {
        if (checkTime()) {
            voteRepository.delete(voteRepository.findByUserId(user_id));
        } else {
            throw new Exception("You can't change your vote  after 11!");
        }
    }

    @SneakyThrows
    @PostMapping("/api/profile/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public void create(@PathVariable int user_id, @PathVariable int restaurant_id) {
        Vote vote = voteRepository.findByUserId(user_id);
        if (vote.isNew()) {
            log.info("add voice by user{} for restaurant{}", user_id, restaurant_id);
            vote = new Vote(LocalTime.now(), restaurantRepository.getById(restaurant_id),
                    userRepository.getById(user_id));
            voteRepository.save(vote);
        } else {
            throw new Exception("You already voted!");
        }
    }

    @SneakyThrows
    @PutMapping("/api/profile/{user_id}/votes/{restaurant_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(allEntries = true)
    public void update(@PathVariable int user_id, @PathVariable int restaurant_id) {
        Vote vote = voteRepository.findByUserId(user_id);
        if (!vote.isNew() && checkTime()) {
            log.info("update voice by user{} for restaurant{}", user_id, restaurant_id);
            vote.setTime(LocalTime.now());
            vote.setRestaurant(restaurantRepository.getById(restaurant_id));
            voteRepository.save(vote);
        } else {
            throw new Exception("No no no, you can't change your choose after 11!");
        }
    }
}
