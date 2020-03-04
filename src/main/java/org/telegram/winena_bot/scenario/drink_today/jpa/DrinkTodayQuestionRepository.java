package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DrinkTodayQuestionRepository extends JpaRepository<DrinkTodayQuestion, Long> {
    List<DrinkTodayQuestion> findAllByAuthorIdAndCompleted(int authorId, boolean completed);
    List<DrinkTodayQuestion> findAllByIdNotIn(Collection<Long> ids);
}