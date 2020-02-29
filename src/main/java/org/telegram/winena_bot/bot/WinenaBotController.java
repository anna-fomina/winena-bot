package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.winena_bot.scenario.WinenaBotService;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
public class WinenaBotController {
    private final WinenaBotProperties properties;
    private final WinenaBotService service;

    @PostMapping("/{token}")
    public void updateMessage(@PathVariable @NotEmpty String token, @RequestBody Update update) {
        if(!token.equals(properties.getToken())) throw new IllegalArgumentException("Invalid token");

        if(update.hasMessage()) service.processMessage(update.getMessage());
    }
}
