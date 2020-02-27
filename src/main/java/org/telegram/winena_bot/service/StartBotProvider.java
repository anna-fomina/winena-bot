package org.telegram.winena_bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.winena_bot.BotStatus;
import org.telegram.winena_bot.dto.BotResponseDTO;

import java.util.List;

import static org.telegram.winena_bot.BotStatus.START;

@Component
public class StartBotProvider implements BotProvider {

    @Override
    public boolean isSupported(BotStatus status) {
        return status == START;
    }

    @Override
    public BotResponseDTO getRequest(Message message) {
        KeyboardRow row = new KeyboardRow();
        row.add(0, "\uD83C\uDF77Можно немного винишка?");
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup()
                .setKeyboard(List.of(row))
                .setOneTimeKeyboard(true)
                .setResizeKeyboard(true);
        SendMessage m = new SendMessage()
                .setChatId(message.getChatId())
                .setText(
                        "Привет, красотка, я - Winena Бот!✌\n" +
                        "Если сомневаешься, пить сегодня винишко или нет - спроси у меня!\uD83D\uDE1C"
                )
                .setReplyMarkup(replyKeyboardMarkup);
        return BotResponseDTO.builder()
                .messages(List.of(m))
                .status(START)
                .build();
    }

    @Override
    public BotResponseDTO getResponse(Message message) {
        return BotResponseDTO.builder()
                .messages(List.of(BotHelper.getSendMessage(message.getChatId(), "Sorry, I can do nothing yet!\uD83D\uDE1E")))
                .status(START)
                .build();
    }
}
