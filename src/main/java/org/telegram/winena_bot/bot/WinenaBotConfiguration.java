package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.util.WebhookUtils;

@Configuration
@RequiredArgsConstructor
public class WinenaBotConfiguration {
    private final WinenaBotProperties properties;

    @Bean
    @SneakyThrows
    WinenaBot winenaBot() {
        ApiContextInitializer.init();

        WinenaBot bot = new WinenaBot(properties.getToken());
        WebhookUtils.setWebhook(bot, properties.getUrl() + properties.getToken(), null);

        return bot;
    }
}