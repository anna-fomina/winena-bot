package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface DrinkTodayQuestionRepository extends JpaRepository<DrinkTodayQuestion, Long> {
    List<DrinkTodayQuestion> findAllByAuthorIdAndCompleted(int authorId, boolean completed);
    @Query(value = "select d from DrinkTodayQuestion d where d.id not in (:excludedIds) and d.completed = true"
            + " order by mod(d.authorId + :seed, mod(:seed, 20)+1), d.id desc")
    Page<DrinkTodayQuestion> findAllRandomOrder(@Param("seed") long seed, @Param("excludedIds") Collection<Long> excludedIds,
                                                Pageable pageable);

}