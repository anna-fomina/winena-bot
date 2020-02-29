package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.Scenario;
import org.telegram.winena_bot.scenario.ScenarioProvider;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkToday;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayRepository;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static org.telegram.winena_bot.scenario.Scenario.DEFAULT;
import static org.telegram.winena_bot.scenario.Scenario.DRINK_TODAY;

@Component
@RequiredArgsConstructor
public class DrinkTodayScenarioProvider implements ScenarioProvider {
    private final Collection<DrinkTodayQuestionProvider> providers;
    private final DrinkTodayRepository repository;

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == DRINK_TODAY;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        var userId = message.getFrom().getId();
        var oldQuestions = repository.findAllByUserId(userId).stream().map(DrinkToday::getQuestion).collect(toList());
        var newQuestion = providers.stream()
                .filter(p -> !oldQuestions.contains(p.getName()))
                .findAny();;
        if(newQuestion.isEmpty()) {
            return getErrorResponse(message.getChatId());
        } else {
            repository.save(new DrinkToday()
                    .setUserId(userId)
                    .setQuestion(newQuestion.get().getName())
                    .setPoints(-1)
            );
            return ScenarioResponseDTO.builder()
                    .message(newQuestion.get().getQuestion(message.getChatId()))
                    .scenario(DRINK_TODAY)
                    .build();
        }
    }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        var userId = message.getFrom().getId();
        var questions = repository.findAllByUserId(userId);

        var openQuestion = questions.stream().filter(q -> q.getPoints() == -1).findFirst();
        if(openQuestion.isEmpty()) return getErrorResponse(message.getChatId());

        var provider = providers.stream()
                .filter(p -> p.getName().equals(openQuestion.get().getQuestion()))
                .findFirst();
        if(provider.isEmpty()) return getErrorResponse(message.getChatId());

        var points = provider.get().checkResponse(message.getText());
        if(points == -1) return getRepeatResponse(message.getChatId());

        if(questions.size() == providers.size() || (questions.size() > 3 && ThreadLocalRandom.current().nextBoolean())) {
            boolean result = (questions.stream().map(DrinkToday::getPoints).reduce(Integer::sum).orElse(0) + points)
                    / questions.size() >= 50;
            repository.deleteInBatch(questions);
            return result ? getYesResponse(message.getChatId()) : getNoResponse(message.getChatId());
        } else {
            repository.save(openQuestion.get().setPoints(points));
            return ScenarioResponseDTO.builder()
                    .scenario(DRINK_TODAY)
                    .build();

        }
    }

    private ScenarioResponseDTO getErrorResponse(long chatId) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Что-то пошло не так. Попробуем сначала!\uD83D\uDE4F"))
                .scenario(DEFAULT)
                .build();
    }


    private ScenarioResponseDTO getRepeatResponse(long chatId) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Что-то непонятное...\uD83D\uDE35"))
                .scenario(DRINK_TODAY)
                .build();
    }

    private ScenarioResponseDTO getYesResponse(long chatId) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Поздравляю!\uD83D\uDE03 Сегодня винишко пить можно!\uD83D\uDC83"))
                .scenario(DEFAULT)
                .build();
    }

    private ScenarioResponseDTO getNoResponse(long chatId) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Oh, no! Лучше сегодня пить не стоит..\uD83D\uDE14"))
                .scenario(DEFAULT)
                .build();
    }
}
