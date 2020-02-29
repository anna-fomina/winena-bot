package org.telegram.winena_bot.scenario;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;

public interface ScenarioProvider {
    boolean isSupported(Scenario scenario);
    ScenarioResponseDTO getRequest(Message message);
    ScenarioResponseDTO getResponse(Message message);
}
