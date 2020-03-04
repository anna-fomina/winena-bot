package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswer;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswerRepository;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestionRepository;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.concurrent.ThreadLocalRandom;

import static java.util.stream.Collectors.toList;

@Component
@RequiredArgsConstructor
public class QuestionProvider {
    private final DrinkTodayQuestionRepository questionRepository;
    private final DrinkTodayAnswerRepository answerRepository;

    SendMessage getQuestion(long questionId, long chatId) {
        var question = questionRepository.findById(questionId).orElseThrow();
        var answers = answerRepository.findAllByQuestionId(questionId)
                .stream().map(DrinkTodayAnswer::getText).collect(toList());
        return BotHelper.getSendMessage(
                chatId,
                question.getText(),
                answers
        );

    }

    int checkResponse(long questionId, String text) {
        var points = answerRepository.findAllByQuestionIdAndText(questionId, text).stream().findFirst()
                .map(DrinkTodayAnswer::getPoints).orElseThrow(InvalidAnswerException::new);
        if(points == 0) {
            points = ThreadLocalRandom.current().nextInt(10, 100);
        }
        return points;
    }
}
