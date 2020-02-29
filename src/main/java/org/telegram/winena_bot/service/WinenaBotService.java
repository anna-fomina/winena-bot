package org.telegram.winena_bot.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.bot.WinenaBot;
import org.telegram.winena_bot.jpa.UserScenario;
import org.telegram.winena_bot.jpa.UserScenarioRepository;

import java.util.Collection;

import static org.telegram.winena_bot.service.Scenario.NEW;

@Service
@RequiredArgsConstructor
public class WinenaBotService {
    private final WinenaBot bot;
    private final Collection<ScenarioProvider> providers;
    private final DefaultScenarioProvider defaultProvider;
    private final UserScenarioRepository repository;

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public void processMessage(Message message) {
        UserScenario user = repository.findById(message.getFrom().getId()).orElseGet(() ->
                repository.save(new UserScenario().setUserId(message.getFrom().getId()).setScenario(NEW)));

        Scenario responseStatus = user.getScenario();

        if(user.getScenario() != NEW && message.getText() != null && !message.getText().equals("/start")) {
            var response = getScenarioProvider(user.getScenario()).getResponse(message);
            responseStatus = response.scenario;
            bot.execute(response.message);
        }

        var request = getScenarioProvider(responseStatus).getRequest(message);
        repository.save(user.setScenario(request.scenario));
        bot.execute(request.message);
    }

    private ScenarioProvider getScenarioProvider(Scenario scenario) {
        return providers.stream()
                .filter(p -> p.isSupported(scenario))
                .findFirst()
                .orElse(defaultProvider);
    }

}
