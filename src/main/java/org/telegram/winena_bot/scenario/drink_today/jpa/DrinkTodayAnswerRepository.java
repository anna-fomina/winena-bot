package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrinkTodayAnswerRepository extends JpaRepository<DrinkTodayAnswer, Long> {
    List<DrinkTodayAnswer> findAllByQuestionId(long questionId);
    List<DrinkTodayAnswer> findAllByQuestionIdAndText(long questionId, String text);
}