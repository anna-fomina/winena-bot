package org.telegram.winena_bot.scenario.drink_today;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.winena_bot.helper.BotHelper;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class RandomNumberQuestionProvider implements DrinkTodayQuestionProvider {
    private final String NAME = "randomNumber";
    private final String ONE = "1⃣";
    private final String TWO = "2⃣";
    private final String THREE = "3⃣";
    private final String FOUR = "4⃣";
    private final String FIVE = "5⃣";
    private final String SIX = "6⃣";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public SendMessage getQuestion(long chatId) {
        return BotHelper.getSendMessage(
                chatId,
                "Может нам подскажет судьба\uD83C\uDFB0\n. Выбери любое число\uD83C\uDFB2",
                List.of(ONE, TWO, THREE),
                List.of(FOUR, FIVE, SIX)
        );

    }

    @Override
    public int checkResponse(String text) {
        if(!List.of(ONE, TWO, THREE, FOUR, FIVE, SIX).contains(text)) return -1;
        return ThreadLocalRandom.current().nextInt(10, 100);
    }
}
