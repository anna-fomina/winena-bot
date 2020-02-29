package org.telegram.winena_bot.scenario.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserScenarioRepository extends JpaRepository<UserScenario, Integer> {
}
