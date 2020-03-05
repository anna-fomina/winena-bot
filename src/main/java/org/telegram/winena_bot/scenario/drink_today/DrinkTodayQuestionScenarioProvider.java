package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.Scenario;
import org.telegram.winena_bot.scenario.ScenarioProvider;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswer;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswerRepository;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestion;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestionRepository;
import org.telegram.winena_bot.scenario.dto.ScenarioResponseDTO;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.List;

import static org.telegram.winena_bot.scenario.Scenario.DEFAULT;
import static org.telegram.winena_bot.scenario.Scenario.DRINK_TODAY_QUESTION;

@Service
@RequiredArgsConstructor
public class DrinkTodayQuestionScenarioProvider implements ScenarioProvider {
    private final DrinkTodayQuestionRepository questionRepository;
    private final DrinkTodayAnswerRepository answerRepository;

    private final String COMPLETE = "Завершить ввод вопроса";
    private final String REMOVE = "Удалить вопрос";

    @Override
    public boolean isSupported(Scenario scenario) {
        return scenario == DRINK_TODAY_QUESTION;
    }

    @Override
    public ScenarioResponseDTO getRequest(Message message) {
        SendMessage m;
        var question = questionRepository.findAllByAuthorIdAndCompleted(message.getFrom().getId(), false)
                .stream().findFirst();
        if(question.isEmpty()) {
            m = BotHelper.getSendMessage(message.getChatId(), "Напиши вопрос. Максимальный размер - 255 символов.");
        } else {
            m = BotHelper.getSendMessage(message.getChatId(), "Напиши ответы к вопросу\n" +
                     question.get().getText() + "\n" +
                    "в формате \ntext\npoints",
                    List.of(COMPLETE, REMOVE));
        }
        return ScenarioResponseDTO.builder()
                .message(m)
                .scenario(DRINK_TODAY_QUESTION)
                .build();
    }

    @Override
    public ScenarioResponseDTO getResponse(Message message) {
        var question = questionRepository.findAllByAuthorIdAndCompleted(message.getFrom().getId(), false)
                .stream().findFirst();

        if(question.isEmpty()) {
            questionRepository.save(new DrinkTodayQuestion()
                    .setAuthorId(message.getFrom().getId())
                    .setText(message.getText())
                    .setCompleted(false));
            return getSavedMessage(message.getChatId());
        }

        var answers = answerRepository.findAllByQuestionId(question.get().getId());
        if(message.getText().equals(COMPLETE)) {
            if(answers.isEmpty()) throw new InvalidAnswerException();
            questionRepository.save(question.get().setCompleted(true));
            return getCompleteMessage(message.getChatId(), "Вопрос сохранен");
        }
        if(message.getText().equals(REMOVE)) {
            questionRepository.delete(question.get());
            answerRepository.deleteAll(answers);
            return getCompleteMessage(message.getChatId(), "Вопрос удален");
        }
        try {
            var answer = message.getText().split("\n");
            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.get().getId())
                    .setText(answer[0])
                    .setPoints(Integer.parseInt(answer[1]))
            );
            return getSavedMessage(message.getChatId());
        } catch (Exception e) {
            throw new InvalidAnswerException();
        }
    }

    private ScenarioResponseDTO getSavedMessage(long chatId) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, "Сохранено", List.of(COMPLETE, REMOVE)))
                .scenario(DRINK_TODAY_QUESTION)
                .build();
    }

    private ScenarioResponseDTO getCompleteMessage(long chatId, String text) {
        return ScenarioResponseDTO.builder()
                .message(BotHelper.getSendMessage(chatId, text))
                .scenario(DEFAULT)
                .build();
    }
}
