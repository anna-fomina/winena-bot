package org.telegram.winena_bot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.dto.ScenarioResponseDTO;

public interface ScenarioProvider {
    boolean isSupported(Scenario scenario);
    ScenarioResponseDTO getRequest(Message message);
    ScenarioResponseDTO getResponse(Message message);
}
