package org.telegram.winena_bot.scenario.drink_today.questions;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface DrinkTodayQuestionProvider {
    String getName();
    SendMessage getQuestion(long chatId);
    int checkResponse(String text);
}