package org.telegram.winena_bot.scenario.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.winena_bot.scenario.Scenario;

@Builder
public class ScenarioResponseDTO {
    public final BotApiMethod message;
    public final Scenario scenario;
    public final String state;
}
