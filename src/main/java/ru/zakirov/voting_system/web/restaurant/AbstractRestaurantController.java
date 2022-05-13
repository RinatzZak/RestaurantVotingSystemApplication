package ru.zakirov.voting_system.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import ru.zakirov.voting_system.model.Restaurant;
import ru.zakirov.voting_system.repository.RestaurantRepository;

import java.time.LocalDate;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository repository;

    public ResponseEntity<Restaurant> getWithTodayDish(int id) {
        log.info("get rest{} with today dishes", id);
        return ResponseEntity.of(repository.getWithTodayDishes(id, LocalDate.now()));
    }
}
