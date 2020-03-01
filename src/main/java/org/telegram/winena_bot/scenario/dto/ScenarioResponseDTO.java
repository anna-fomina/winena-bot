package org.telegram.winena_bot.scenario.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.winena_bot.scenario.Scenario;

import java.util.List;

@Builder
public class ScenarioResponseDTO {
    public final List<PartialBotApiMethod> message;
    public final Scenario scenario;
    public final String state;
}
