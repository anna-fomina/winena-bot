package org.telegram.winena_bot.helper;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class BotHelper {
    public static SendMessage getSendMessage(long chatId, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(chatId);
        s.setText(text);
        return s;
    }

    @SafeVarargs
    public static SendMessage getSendMessage(long chatId, String text, List<String>... answers) {
        SendMessage s = getSendMessage(chatId, text);

        var buttons = Stream.of(answers).map(a -> {
            KeyboardRow row = new KeyboardRow();
            for (int i = 0; i < a.size(); i++) {
                row.add(i, a.get(i));
            }
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
