package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.winena_bot.service.MessageHandler;

@RequiredArgsConstructor
public class WinenaBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private final MessageHandler handler;

    @SuppressWarnings("unchecked")
    @Override
    public void onUpdateReceived(Update update) {
        handler.processMessage(update.getMessage()).forEach(m -> {
            try {
                execute(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}