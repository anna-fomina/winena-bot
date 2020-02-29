package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.winena_bot.service.MessageHandler;

@RequiredArgsConstructor
public class WinenaBot extends TelegramWebhookBot {
    private final String botName;
    private final String botToken;
    private final MessageHandler handler;

    @SuppressWarnings("unchecked")
    public void updateMessage(String token, Update update) {
        if(!token.equals(botToken)) throw new IllegalArgumentException("Invalid token");
        handler.processMessage(update.getMessage()).forEach(m -> {
            try {
                execute(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {
        return null;
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public String getBotPath() {
        return botToken;
    }
}