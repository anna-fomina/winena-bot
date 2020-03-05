package org.telegram.winena_bot.scenario;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.List;

import static org.telegram.winena_bot.scenario.Scenario.*;

@Service
public class DefaultScenarioProvider implements ScenarioProvider {
    private final String DRINK_TODAY_BTN = "\uD83C\uDF77Можно немного винишка?";
    private final String MAKE_DIARY_RECORD_BTN = "\uD83D\uDC69\uD83C\uDFFC\u200D\uD83D\uDCBBСделать запись в дневнике (future feature)";
    private final String GET_DIARY_RECORDS_BTN = "\uD83D\uDCDAПосмотреть дневник (future feature)";

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == DEFAULT;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        SendMessage m = BotHelper.getSendMessage(
                message.getChatId(),
                "Привет, красотка!✌\n" +
                        "Если сомневаешься, пить сегодня винишко или нет - спроси у меня!\uD83D\uDE1C",
                List.of(DRINK_TODAY_BTN)
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
        if(message.getText().equals(MAKE_DIARY_RECORD_BTN) || message.getText().equals(GET_DIARY_RECORDS_BTN)) {
            return ScenarioResponseDTO.builder()
                    .message(BotHelper.getSendMessage(message.getChatId(), "К сожалению, я этого еще не умею!\uD83D\uDE29"))
                    .scenario(DEFAULT)
                    .build();
        }
        if(message.getText().equals("/uploadMem")) {
            return ScenarioResponseDTO.builder()
                    .scenario(DRINK_TODAY_MEM)
                    .build();
        }
        if(message.getText().equals("/createQuestion")) {
            return ScenarioResponseDTO.builder()
                    .scenario(DRINK_TODAY_QUESTION)
                    .build();
        }
        throw new InvalidAnswerException();
    }
}
