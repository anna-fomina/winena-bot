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

    private void saveAnswer(long id, String text) {
        answerRepository.save(new DrinkTodayAnswer()
                .setQuestionId(id)
                .setText(text)
                .setPoints(0));
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultCurrency() {
        if(questionRepository.count() == 0) {
            var question = questionRepository.save(new DrinkTodayQuestion()
                    .setAuthorId(0)
                    .setText("Может нам подскажет судьба\uD83C\uDFB0. Выбери любое число\uD83C\uDFB2")
                    .setCompleted(false)
            );

            saveAnswer(question.getId(), "1⃣");
            saveAnswer(question.getId(), "2⃣");
            saveAnswer(question.getId(), "3⃣");
            saveAnswer(question.getId(), "4⃣");
            saveAnswer(question.getId(), "5⃣");
            saveAnswer(question.getId(), "6⃣");
            saveAnswer(question.getId(), "1⃣0⃣0⃣5⃣0⃣0⃣");

            questionRepository.save(question.setCompleted(true));
        }
    }
}