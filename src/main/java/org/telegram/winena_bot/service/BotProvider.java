package org.telegram.winena_bot.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.BotStatus;
import org.telegram.winena_bot.dto.BotResponseDTO;

public interface BotProvider {
    boolean isSupported(BotStatus status);
    BotResponseDTO getRequest(Message message);
    BotResponseDTO getResponse(Message message);
}
