package org.telegram.winena_bot.dto;

import lombok.Builder;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.winena_bot.BotStatus;

import java.util.Collection;

@Builder
public class BotResponseDTO {
    public final Collection<BotApiMethod> messages;
    public final BotStatus status;
}
