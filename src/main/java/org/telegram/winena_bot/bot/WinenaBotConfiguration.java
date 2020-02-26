package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.winena_bot.service.BotHandler;

@Configuration
@RequiredArgsConstructor
public class WinenaBotConfiguration {
    private final BotHandler botHandler;
    @Value("${bot.name}")
    private String botName;
    @Value("${bot.token}")
    private String botToken;

    @Bean
    @SneakyThrows
    WinenaBot winenaBot() {
        ApiContextInitializer.init();
        WinenaBot bot = new WinenaBot(botName, botToken, botHandler);

        TelegramBotsApi botApi = new TelegramBotsApi();
        botApi.registerBot(bot);

        return bot;
    }
}