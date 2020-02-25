package org.telegram.winena_bot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@RequiredArgsConstructor
public class WinenaBot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;

    @SneakyThrows
    private void sendMessage(Message message, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(message.getChatId());
        s.setText(text);
        execute(s);
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message received = update.getMessage();
        String text = received.getText();
        if (text.equals("/start")) {
            sendMessage(received, "Hello, I'm Winena Bot!");
        } else {
            sendMessage(received, "Sorry, I can do nothing yet!\uD83D\uDE1E");
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}