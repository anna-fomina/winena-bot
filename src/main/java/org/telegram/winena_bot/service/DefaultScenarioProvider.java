package org.telegram.winena_bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.dto.ScenarioResponseDTO;

import static org.telegram.winena_bot.service.Scenario.*;

@Component
public class DefaultScenarioProvider implements ScenarioProvider {
    private final String DRINK_TODAY_BTN = "\uD83C\uDF77Можно немного винишка?";

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == NEW || scenario == DEFAULT;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        SendMessage m = BotHelper.getSendMessage(
                message.getChatId(),
                "Привет, красотка, я - Winena Бот!✌\n" +
                        "Если сомневаешься, пить сегодня винишко или нет - спроси у меня!\uD83D\uDE1C",
                DRINK_TODAY_BTN
        );
        return ScenarioResponseDTO.builder()
                .message(m)
                .scenario(DEFAULT)
                .build();
    }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        if(message.getText().equals(DRINK_TODAY_BTN)) {
            return ScenarioResponseDTO.builder()
                    .message(BotHelper.getSendMessage(message.getChatId(), "Давай попробуем узнать!\uD83D\uDE0B"))
                    .scenario(DRINK_TODAY)
                    .build();
        }
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(message.getChatId(), "Извини, не могу прочитать, что написано\uD83D\uDE1E"))
                .scenario(DEFAULT)
                .build();
    }
}
