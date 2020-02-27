package org.telegram.winena_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.dto.BotResponseDTO;
import org.telegram.winena_bot.jpa.TelegramUser;
import org.telegram.winena_bot.jpa.TelegramUserRepository;

import java.util.Collection;

import static org.telegram.winena_bot.BotStatus.NEW;

@Service
@RequiredArgsConstructor
public class BotHandler {
    private final Collection<BotProvider> providers;
    private final StartBotProvider startBotProvider;
    private final TelegramUserRepository repository;

    public Collection<BotApiMethod> processMessage(Message message) {
        TelegramUser user = repository.findById(message.getFrom().getId()).orElseGet(() ->
                repository.save(new TelegramUser().setId(message.getFrom().getId()).setStatus(NEW)));

        BotResponseDTO response;
        //TODO если одни картинки - текста нет
        if(message.getText().equals("/start") || user.getStatus() == NEW) {
            response = startBotProvider.getRequest(message);
        } else {
            response = providers.stream()
                    .filter(p -> p.isSupported(user.getStatus()))
                    .findFirst()
                    .orElse(startBotProvider).getResponse(message);
        }
        repository.save(user.setStatus(response.status));
        return response.messages;
    }

}
