package org.telegram.winena_bot.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.validation.constraints.NotEmpty;

@Controller
@RequiredArgsConstructor
public class WinenaBotController {
    private final WinenaBot bot;

    @PostMapping("/{token}")
    public void updateMessage(@PathVariable @NotEmpty String token, @RequestBody Update update) {
        bot.updateMessage(token, update);
    }
}
