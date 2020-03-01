package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.Scenario;
import org.telegram.winena_bot.scenario.ScenarioProvider;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayMem;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayMemRepository;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.stream.Stream;

import static org.telegram.winena_bot.scenario.Scenario.DEFAULT;
import static org.telegram.winena_bot.scenario.Scenario.DRINK_TODAY_MEM;

@Service
@RequiredArgsConstructor
public class DrinkTodayMemScenarioProvider implements ScenarioProvider {
    private final DrinkTodayMemRepository repository;

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == DRINK_TODAY_MEM;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        SendMessage m = BotHelper.getSendMessage(
                message.getChatId(),
                "Upload mem with 'YES' or 'NO' text"
        );
        return ScenarioResponseDTO.builder()
                .message(m)
                .scenario(DRINK_TODAY_MEM)
                .build();
    }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        var photoId = message.getPhoto().stream().map(PhotoSize::getFileId).findFirst().orElseThrow(InvalidAnswerException::new);
        if(Stream.of("YES", "NO").noneMatch(s -> s.equals(photoId))) throw new InvalidAnswerException();
        repository.save(new DrinkTodayMem()
                .setType(message.getText())
                .setFileId(photoId));
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(message.getChatId(), "Saved"))
                .scenario(DEFAULT)
                .build();
    }
}
