package ru.zakirov.voting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zakirov.voting_system.model.Restaurant;
import ru.zakirov.voting_system.repository.RestaurantRepository;

import java.util.Objects;

@RestController
@Slf4j
@CacheConfig(cacheNames = "restaurants")
@RequestMapping(value = UserRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController {

    public static final String REST_URL = "/api/restaurant";
    private final RestaurantRepository repository;

    public UserRestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }


}
