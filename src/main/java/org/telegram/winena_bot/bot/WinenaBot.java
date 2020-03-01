package org.telegram.winena_bot.bot;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

public class WinenaBot extends DefaultAbsSender {
    private final String botToken;

    public WinenaBot(String botToken) {
        super(ApiContext.getInstance(DefaultBotOptions.class));
        this.botToken = botToken;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}