package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkTodayRepository extends JpaRepository<DrinkToday, Integer> {
    List<DrinkToday> findAllByUserId(int userId);
}