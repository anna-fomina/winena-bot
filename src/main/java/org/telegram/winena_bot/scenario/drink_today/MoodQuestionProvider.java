package org.telegram.winena_bot.scenario.drink_today;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;

import java.util.List;

@Component
public class MoodQuestionProvider implements DrinkTodayQuestionProvider {
    private final String NAME = "mood";
    private final String COOL = "\uD83C\uDF1EВсе замечтально";
    private final String NORMAL = "\uD83D\uDE0AНормально";
    private final String TIRED = "\uD83D\uDE11Очень устала";
    private final String COMPLICATED = "\uD83D\uDDA4На сердце боль, взгляд смотрит в небо...";
    private final String BAD = "\uD83C\uDF1AВсе плохо";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public SendMessage getQuestion(long chatId) {
        return BotHelper.getSendMessage(
                chatId,
                "Как настроение?\uD83D\uDE4B",
                List.of(COOL, NORMAL),
                List.of(TIRED, BAD),
                List.of(COMPLICATED)
                );

    }

    @Override
    public int checkResponse(String text) {
        if(text.equals(COOL)) return 20;
        if(text.equals(NORMAL)) return 40;
        if(text.equals(TIRED)) return 60;
        if(text.equals(COMPLICATED)) return 60;
        if(text.equals(BAD)) return 80;
        throw new InvalidAnswerException();
    }
}
