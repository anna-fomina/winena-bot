package org.telegram.winena_bot.scenario.drink_today;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswer;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayAnswerRepository;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestion;
import org.telegram.winena_bot.scenario.drink_today.jpa.DrinkTodayQuestionRepository;

@Component
@RequiredArgsConstructor
public class DefaultQuestionCreator {
    private final DrinkTodayQuestionRepository questionRepository;
    private final DrinkTodayAnswerRepository answerRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultCurrency() {
        if(questionRepository.count() == 0) {
            var question = questionRepository.save(new DrinkTodayQuestion()
                    .setAuthorId(0)
                    .setText("Может нам подскажет судьба\uD83C\uDFB0. Выбери любое число\uD83C\uDFB2")
                    .setCompleted(false)
            );

            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.getId())
                    .setText("1⃣")
                    .setPoints(0));
            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.getId())
                    .setText("2⃣")
                    .setPoints(0));
            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.getId())
                    .setText("3⃣")
                    .setPoints(0));
            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.getId())
                    .setText("4⃣")
                    .setPoints(0));
            answerRepository.save(new DrinkTodayAnswer()
                    .setQuestionId(question.getId())
                    .setText("1⃣0⃣0⃣5⃣0⃣0⃣")
                    .setPoints(0));

            questionRepository.save(question.setCompleted(true));
        }
    }
}