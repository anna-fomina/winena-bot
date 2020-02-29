package org.telegram.winena_bot.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.winena_bot.Scenario;

@Builder
public class ScenarioResponseDTO {
    public final BotApiMethod message;
    public final Scenario scenario;
}
