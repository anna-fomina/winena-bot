package org.telegram.winena_bot.scenario;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;

import java.util.List;

import static org.telegram.winena_bot.scenario.Scenario.*;

@Service
public class FinishScenarioProvider implements ScenarioProvider {

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == FINISH;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        SendMessage m = BotHelper.getSendMessage(
                message.getChatId(),
                "\uD83D\uDD39\uD83D\uDD39\uD83D\uDD39",
                List.of("\uD83D\uDC46В начало")
        );
        return ScenarioResponseDTO.builder()
                .message(List.of(m))
                .scenario(FINISH)
                .build();
    }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        return ScenarioResponseDTO.builder()
                .scenario(DEFAULT)
                .build();
    }
}
