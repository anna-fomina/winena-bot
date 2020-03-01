package org.telegram.winena_bot.scenario.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.winena_bot.scenario.Scenario;

@Builder
public class ScenarioResponseDTO {
    public final SendMessage message;
    public final SendPhoto photo;
    public final Scenario scenario;
    public final String state;
}
