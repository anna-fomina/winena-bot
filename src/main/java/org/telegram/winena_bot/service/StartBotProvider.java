package org.telegram.winena_bot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
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
        return BotResponseDTO.builder()
                .messages(List.of(BotHelper.getSendMessage(message.getChatId(), "Hello, I'm Winena Bot!")))
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
