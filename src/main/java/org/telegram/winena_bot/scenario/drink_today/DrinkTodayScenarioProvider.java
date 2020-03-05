package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.Scenario;
import org.telegram.winena_bot.scenario.ScenarioProvider;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkToday;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayMemRepository;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestionRepository;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayRepository;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;
import static org.telegram.winena_bot.scenario.Scenario.DRINK_TODAY;
import static org.telegram.winena_bot.scenario.Scenario.FINISH;

@Component
@RequiredArgsConstructor
public class DrinkTodayScenarioProvider implements ScenarioProvider {
    private final DrinkTodayRepository repository;
    private final DrinkTodayMemRepository memRepository;
    private final DrinkTodayQuestionRepository questionRepository;
    private final DrinkTodayQuestionService drinkTodayQuestionService;

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == DRINK_TODAY;
    }

    private DrinkToday findOrCreateQuestion(int userId) {
        var questions = repository.findAllByUserId(userId);

        var openQuestion = questions.stream().filter(q -> q.getPoints() == -1).findFirst();
        if(openQuestion.isPresent()) return openQuestion.get();

        var excludedIds = questions.stream().map(DrinkToday::getQuestionId).collect(toList());
        var newQuestion = questionRepository.findAllRandomOrder(
                excludedIds.size() == 0 ? List.of(0L) : excludedIds,
                PageRequest.of(0, 1)
        ).get().findFirst().orElseThrow();

        return repository.save(new DrinkToday()
                .setUserId(userId)
                .setQuestionId(newQuestion.getId())
                .setPoints(-1));
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        var question = findOrCreateQuestion(message.getFrom().getId());

        return ScenarioResponseDTO.builder()
                .message(drinkTodayQuestionService.getQuestion(question.getQuestionId(), message.getChatId()))
                .scenario(DRINK_TODAY)
                .build();
        }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        var questions = repository.findAllByUserId(message.getFrom().getId());

        var openQuestion = questions.stream().filter(q -> q.getPoints() == -1).findFirst().orElseThrow(IllegalStateException::new);
        var points = drinkTodayQuestionService.checkResponse(openQuestion.getQuestionId(), message.getText());

        if(questions.size() == questionRepository.count() || (questions.size() > 3 && ThreadLocalRandom.current().nextBoolean())) {
            boolean result = (questions.stream().map(DrinkToday::getPoints).reduce(Integer::sum).orElse(0) + points)
                    / questions.size() >= 50;
            repository.deleteInBatch(questions);
            return result ? getYesResponse(message.getChatId()) : getNoResponse(message.getChatId());
        } else {
            repository.save(openQuestion.setPoints(points));
            return ScenarioResponseDTO.builder()
                    .scenario(DRINK_TODAY)
                    .build();
        }
    }

    private ScenarioResponseDTO getYesResponse(long chatId) {
        var photoId = memRepository.findMem("YES").stream().map(s -> BotHelper.getSendPhoto(chatId, s.getFileId()))
                .findFirst().orElse(null);
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Поздравляю!\uD83D\uDE03 Сегодня винишко пить можно!\uD83D\uDC83"))
                .photo(photoId)
                .scenario(FINISH)
                .build();
    }

    private ScenarioResponseDTO getNoResponse(long chatId) {
        var photoId = memRepository.findMem("NO").stream().map(s -> BotHelper.getSendPhoto(chatId, s.getFileId()))
                .findFirst().orElse(null);
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Oh, no! Лучше сегодня пить не стоит..\uD83D\uDE14"))
                .photo(photoId)
                .scenario(FINISH)
                .build();
    }
}
