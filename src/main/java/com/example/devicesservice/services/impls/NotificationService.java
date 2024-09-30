package com.example.devicesservice.services.impls;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@RequiredArgsConstructor
public class NotificationService extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "7162404537:AAG1YU4mKEwIC2wfQEtgQ1g8wRnFYIu2ur0";

    private final String BOT_USERNAME = "DomotiQBot";

//    chatIds: 6142006691

    @PostConstruct
    private void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();

            Long chatId = message.getChatId();
            Long userId = message.getFrom().getId();
            String username = message.getFrom().getUserName();
            String text = message.getText();
            Integer messageId = message.getMessageId();
            Message messageReplied = message.getReplyToMessage();

            System.out.println("Chat ID: " + chatId);
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException ex) {
            System.out.println("Send message to telegram failed");
        }
    }

}
