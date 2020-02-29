package org.telegram.winena_bot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.Scenario;
import org.telegram.winena_bot.jpa.UserScenario;
import org.telegram.winena_bot.jpa.UserScenarioRepository;

import java.util.ArrayList;
import java.util.Collection;

import static org.telegram.winena_bot.Scenario.NEW;

@Service
@RequiredArgsConstructor
public class MessageHandler {
    private final Collection<ScenarioProvider> providers;
    private final DefaultScenarioProvider defaultProvider;
    private final UserScenarioRepository repository;

    public Collection<BotApiMethod> processMessage(Message message) {
        UserScenario user = repository.findById(message.getFrom().getId()).orElseGet(() ->
                repository.save(new UserScenario().setUserId(message.getFrom().getId()).setScenario(NEW)));

        Scenario responseStatus = user.getScenario();
        var messages = new ArrayList<BotApiMethod>();

        if(user.getScenario() != NEW && message.getText() != null && !message.getText().equals("/start")) {
            var response = getScenarioProvider(user.getScenario()).getResponse(message);
            responseStatus = response.scenario;
            messages.add(response.message);
        }

        var request = getScenarioProvider(responseStatus).getRequest(message);
        repository.save(user.setScenario(request.scenario));
        messages.add(request.message);
        return messages;
    }

    private ScenarioProvider getScenarioProvider(Scenario scenario) {
        return providers.stream()
                .filter(p -> p.isSupported(scenario))
                .findFirst()
                .orElse(defaultProvider);
    }

}
