package org.telegram.winena_bot.scenario.drink_today;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.List;

@Component
public class CompanyQuestionProvider implements DrinkTodayQuestionProvider {
    private final String NAME = "company";
    private final String ALONE = "\uD83D\uDC78Одна";
    private final String CAT = "\uD83D\uDC08С кошкой";
    private final String BOYFRIEND = "\uD83D\uDC6BС парнем";
    private final String FRIEND = "\uD83D\uDC6FС подругой";
    private final String PARTY = "\uD83D\uDC7BУ нас целая вечеринка!";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public SendMessage getQuestion(long chatId) {
        return BotHelper.getSendMessage(
                chatId,
                "Ты одна или с компанией?\uD83D\uDE4B",
                List.of(ALONE),
                List.of(CAT),
                List.of(BOYFRIEND),
                List.of(FRIEND),
                List.of(PARTY)
        );

    }

    @Override
    public int checkResponse(String text) {
        if(text.equals(ALONE)) return 20;
        if(text.equals(CAT)) return 30;
        if(text.equals(BOYFRIEND)) return 40;
        if(text.equals(FRIEND)) return 60;
        if(text.equals(PARTY)) return 80;
        throw new InvalidAnswerException();
    }
}
