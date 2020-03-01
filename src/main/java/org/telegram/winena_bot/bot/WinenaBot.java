package org.telegram.winena_bot.bot;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

public class WinenaBot extends DefaultAbsSender {
    private final String botToken;

    public WinenaBot(String botToken) {
        super(ApiContext.getInstance(DefaultBotOptions.class));
        this.botToken = botToken;
    }

    public void send(Collection<PartialBotApiMethod> messages) {
        messages.forEach(m -> {
            try {
                execute(m);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}