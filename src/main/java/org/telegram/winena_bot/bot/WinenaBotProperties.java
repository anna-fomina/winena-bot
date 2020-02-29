package org.telegram.winena_bot.bot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "winena-bot")
@Getter @Setter
public class WinenaBotProperties {
    private String token;
    private String url;
}