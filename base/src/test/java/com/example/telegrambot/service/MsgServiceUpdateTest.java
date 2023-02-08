package com.example.telegrambot.service;

import com.example.telegrambot.UpdateMock;
import com.example.telegrambot.parser.AnalyzeResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.example.telegrambot.parser.MessageType.MESSAGE;
import static org.mockito.Mockito.when;

public class MsgServiceUpdateTest extends UpdateMock {
    protected static final String MESSAGE_TEXT = "Message text";
    protected static final long CHAT_ID_MESSAGE = 1000001l;
    MsgService msgService;
    AnalyzeResult analyzeResult;

    @Before
    public void setup() {
        clearSetup();

        msgService = new MsgService();
        analyzeResult = new AnalyzeResult(update);

        tuneMessage(user, MESSAGE_TEXT, CHAT_ID_MESSAGE);
        when(update.getMessage()).thenReturn(message);
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