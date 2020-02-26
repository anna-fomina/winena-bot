package org.telegram.winena_bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

class BotHelper {
    static SendMessage getSendMessage(long chatId, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        return s;
    }
}
