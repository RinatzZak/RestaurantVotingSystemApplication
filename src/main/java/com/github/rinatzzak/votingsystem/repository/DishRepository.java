package com.github.rinatzzak.votingsystem.repository;

import com.github.rinatzzak.votingsystem.model.Dish;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}