package org.telegram.winena_bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class BotHelper {
    static SendMessage getSendMessage(long chatId, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        return s;
    }

    static SendMessage getSendMessage(long chatId, String text, String... answers) {
        SendMessage s = getSendMessage(chatId, text);

        var buttons = Stream.of(answers).map(a -> {
            KeyboardRow row = new KeyboardRow();
            row.add(0, a);
            return row;
        }).collect(toList());
        s.setReplyMarkup(new ReplyKeyboardMarkup()
                .setKeyboard(buttons)
                .setOneTimeKeyboard(true)
                .setResizeKeyboard(true)
        );

        return s;
    }
}
