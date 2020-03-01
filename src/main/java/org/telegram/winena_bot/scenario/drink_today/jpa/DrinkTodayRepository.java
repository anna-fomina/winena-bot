package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DrinkTodayRepository extends JpaRepository<DrinkToday, Integer> {
    List<DrinkToday> findAllByUserId(int userId);
    Optional<DrinkToday> findAllByUserIdAndPoints(int userId, int points);
    void deleteAllByUserId(int userId);
}