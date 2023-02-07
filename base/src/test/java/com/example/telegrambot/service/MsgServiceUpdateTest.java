package com.example.telegrambot.service;

import com.example.telegrambot.parser.AnalyzeResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;

import static com.example.telegrambot.parser.MessageType.MESSAGE;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MsgServiceUpdateTest {
    private static final String MESSAGE_TEXT = "Message text";
    private static final long CHAT_ID_MESSAGE = 1000001l;
    private static final long CHAT_ID_CALLBACK = 1000002l;
    MsgService msgService;

    Update update = mock(Update.class);

    AnalyzeResult analyzeResult;

    @Before
    public void setup() {
        msgService = new MsgService();
        analyzeResult = new AnalyzeResult(update);

        User user = mock(User.class);
        Message message = getMockMessage(user);
        when(update.getMessage()).thenReturn(message);
    }

    private static Message getMockMessage(User user) {
        Message message = mock(Message.class);
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn(MESSAGE_TEXT);
        when(message.hasText()).thenReturn(true);
        when(message.getChatId()).thenReturn(CHAT_ID_MESSAGE);
        return message;
    }

    // todo need more tests
    @Test
    public void shouldGetChatId_whenType_MESSAGE() {
        // when
        analyzeResult.setMessageType(MESSAGE);

        // then
        Assert.assertEquals(Long.toString(CHAT_ID_MESSAGE), msgService.getChatId(analyzeResult));
    }

    @Test
    public void shouldGetMessageText_whenType_MESSAGE() {
        // when
        analyzeResult.setMessageType(MESSAGE);

        // then
        Assert.assertEquals(MESSAGE_TEXT, msgService.getMessageText(analyzeResult));
    }
}