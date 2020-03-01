package org.telegram.winena_bot.scenario.drink_today.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DrinkTodayMemRepository extends JpaRepository<DrinkTodayMem, Integer> {
    @Query("SELECT d FROM DrinkTodayMem d WHERE d.type = :reqType order by function('RAND')")
    List<DrinkTodayMem> findMem(@Param("reqType") String type);
}