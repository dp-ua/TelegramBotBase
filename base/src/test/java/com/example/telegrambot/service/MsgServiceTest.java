package com.example.telegrambot.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import static org.junit.Assert.assertEquals;

public class MsgServiceTest {
    MsgService msgService;
    QueueProvider queueProvider;

    @Before
    public void setup() {
        msgService = new MsgService();
        queueProvider = new QueueProvider();
        msgService.setQueueProvider(queueProvider);
    }

    @Test
    public void shouldPutNewMessageToSendQueue() {
        // should be 0 size in send queue when start
        assertEquals(0, queueProvider.getSendQueue().size());

        // when call sendMessage
        SendMessage message = new SendMessage();
        msgService.sendMessage(message);

        // then queue should contain it
        assertEquals(1, queueProvider.getSendQueue().size());
        assertEquals(message, queueProvider.getSendQueue().peek());
    }

    @Test
    public void shouldPutNewUpdateToReceiveQueue() {
        // should be 0 size in send queue when start
        assertEquals(0, queueProvider.getReceiveQueue().size());

        // when call sendMessage
        Update updateMock = Mockito.mock(Update.class);
        msgService.acceptMessage(updateMock);

        // then queue should contain it
        assertEquals(1, queueProvider.getReceiveQueue().size());
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case1() {
        assertEquals(true, msgService.contains("instr", "in big instr string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case2() {
        assertEquals(true, msgService.contains("instr", "instr in big  string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case3() {
        assertEquals(true, msgService.contains("instr", "in big string instr"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case4() {
        assertEquals(true, msgService.contains("instr", "in big instr, string"));
    }

    @Test
    public void shouldContainsAStringWithDelimeters_case5() {
        assertEquals(true, msgService.contains("long instr text", "in big long instr text. string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case1() {
        assertEquals(false, msgService.contains("instr", "in biginstr, string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case2() {
        assertEquals(false, msgService.contains("instr", "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case3() {
        assertEquals(false, msgService.contains(" ", "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case3a() {
        assertEquals(false, msgService.contains(".", "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case4() {
        assertEquals(false, msgService.contains(null, "in big inst r string"));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case5() {
        assertEquals(false, msgService.contains(null, null));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case6() {
        assertEquals(false, msgService.contains("str", null));
    }

    @Test
    public void shouldNotContainsAStringWithDelimeters_case7() {
        assertEquals(false, msgService.contains("", "in big inst r string"));
    }
}