package com.github.rinatzzak.votingsystem.web.restaurant;

import com.github.rinatzzak.votingsystem.repository.RestaurantRepository;
import com.github.rinatzzak.votingsystem.util.validation.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import com.github.rinatzzak.votingsystem.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class AbstractRestaurantController {

    @Autowired
    private RestaurantRepository repository;

    public ResponseEntity<Restaurant> getWithTodayDish(int id) {
        log.info("get rest{} with today dishes", id);
        ValidationUtil.assureIdConsistent(repository.getById(id), id);
        return ResponseEntity.of(repository.getWithDishesWithDate(id, LocalDate.now()));
    }

    public List<Restaurant> getAllWithTodayDishes() {
        log.info("getAllWithTodayDishes");
        return repository.getAllWithDishes(LocalDate.now());
    }
}
