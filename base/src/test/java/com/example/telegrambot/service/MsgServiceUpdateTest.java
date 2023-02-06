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

        Message message = mock(Message.class);
        User user = mock(User.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getFrom()).thenReturn(user);
        when(message.getText()).thenReturn(MESSAGE_TEXT);
        when(message.getChatId()).thenReturn(CHAT_ID_MESSAGE);
    }

    // todo FIX ME
    @Test
    public void shouldGetChatId_whenType_MESSAGE() {
        // when
        analyzeResult.setMessageType(MESSAGE);

        // then
        Assert.assertEquals(CHAT_ID_MESSAGE, msgService.getChatId(analyzeResult));
    }
}