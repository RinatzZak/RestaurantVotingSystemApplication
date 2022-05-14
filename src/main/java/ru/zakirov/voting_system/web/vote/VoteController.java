package ru.zakirov.voting_system.web.vote;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.zakirov.voting_system.model.Restaurant;
import ru.zakirov.voting_system.model.Vote;
import ru.zakirov.voting_system.repository.RestaurantRepository;
import ru.zakirov.voting_system.repository.UserRepository;
import ru.zakirov.voting_system.repository.VoteRepository;
import ru.zakirov.voting_system.web.SecurityUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.zakirov.voting_system.util.validation.ValidationUtil.*;

@RestController
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class VoteController {

    static final String REST_URL = "/api/votes";

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    public VoteController(VoteRepository voteRepository, RestaurantRepository restaurantRepository,
                          UserRepository userRepository) {
        this.voteRepository = voteRepository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/my-votes")
    public List<Vote> getAllMyVotes() {
        int userId = SecurityUtil.authId();
        log.info("getAllMyVotes for user{}", userId);
        return voteRepository.getAllById(userId);
    }

    @GetMapping("/today-votes")
    public ResponseEntity<Vote> getTodayVote() {
        int userId = SecurityUtil.authId();
        log.info("getTodayVote for user{}", userId);
        return ResponseEntity.of(voteRepository.findByIdAndDate(userId, LocalDate.now()));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Vote> create(Integer restaurantId) {
        int userId = SecurityUtil.authId();
        Vote vote = getTodayVote().getBody();
        assureIdConsistent(restaurantRepository.getById(restaurantId), restaurantId);
        checkForNull(vote);
        Vote created = new Vote(LocalDate.now(),restaurantRepository.getById(restaurantId), userRepository.getById(userId));
        voteRepository.save(created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    public void update(Integer restaurantId) {
        Vote vote = getTodayVote().getBody();
        Restaurant restaurant = restaurantRepository.getById(restaurantId);
        checkForNotNull(vote);
        checkTime();
        assureIdConsistent(restaurant, restaurantId);
        vote.setRestaurant(restaurant);
        voteRepository.save(vote);
    }

}
