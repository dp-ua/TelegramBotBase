package com.example.telegrambot;

import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UpdateMock {
    protected Update update;
    protected User user;
    protected Message message;

    protected void clearSetup() {
        update = mock(Update.class);
        user = mock(User.class);
        message = mock(Message.class);
    }

    protected void tuneMessage(User user, String messageText, long chatId) {
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn(messageText);
        when(message.hasText()).thenReturn(true);
        when(message.getChatId()).thenReturn(chatId);
    }

    protected void tuneUser(String username, int userid) {
        when(user.getBot()).thenReturn(false);
        when(user.getUserName()).thenReturn(username);
        when(user.getId()).thenReturn(userid);
    }

}
