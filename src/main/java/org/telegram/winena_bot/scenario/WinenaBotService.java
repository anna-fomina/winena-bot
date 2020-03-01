package org.telegram.winena_bot.scenario;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.winena_bot.bot.WinenaBot;
import org.telegram.winena_bot.helper.BotHelper;
import org.telegram.winena_bot.scenario.exception.InvalidAnswerException;
import org.telegram.winena_bot.scenario.jpa.UserScenario;
import org.telegram.winena_bot.scenario.jpa.UserScenarioRepository;

import java.util.Collection;

import static org.telegram.winena_bot.scenario.Scenario.DEFAULT;

@Service
@RequiredArgsConstructor
@Slf4j
public class WinenaBotService {
    private final WinenaBot bot;
    private final Collection<ScenarioProvider> providers;
    private final DefaultScenarioProvider defaultProvider;
    private final UserScenarioRepository repository;

    @SneakyThrows
    public void processMessage(Message message) {
        UserScenario user = repository.findById(message.getFrom().getId()).orElseGet(() ->
                repository.save(new UserScenario().setUserId(message.getFrom().getId()).setScenario(DEFAULT)));

        Scenario responseStatus = user.getScenario();

        if(message.getText() != null && message.getText().equals("/start")) {
            responseStatus = DEFAULT;
        } else {
            try {
                var response = getScenarioProvider(user.getScenario()).getResponse(message);
                responseStatus = response.scenario;
                if (response.message != null) bot.execute(response.message);
                if (response.photo != null) bot.execute(response.photo);
            } catch (InvalidAnswerException e) {
                bot.execute(BotHelper.getSendMessage(message.getChatId(), "Что-то непонятное...\uD83D\uDE35"));
            } catch (IllegalStateException e) {
                bot.execute(BotHelper.getSendMessage(message.getChatId(), "Что-то пошло не так. Попробуем сначала!\uD83D\uDE4F"));
                responseStatus = DEFAULT;
            }
        }

        var request = getScenarioProvider(responseStatus).getRequest(message);
        repository.save(user.setScenario(request.scenario));
        bot.execute(request.message);
        if(request.photo != null) bot.execute(request.photo);
    }

    private ScenarioProvider getScenarioProvider(Scenario scenario) {
        return providers.stream()
                .filter(p -> p.isSupported(scenario))
                .findFirst()
                .orElse(defaultProvider);
    }

}
