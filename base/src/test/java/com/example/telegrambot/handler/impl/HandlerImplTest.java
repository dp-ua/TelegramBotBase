package com.example.telegrambot.handler.impl;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.handler.DefaultHandler;
import com.example.telegrambot.handler.HandlerService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.example.telegrambot.command.CommandMock.HELP;
import static com.example.telegrambot.command.CommandMock.START;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandlerImplTest {
    HandlerService handlerService;

    @Before
    public void setUp() {
        Map<CommandElement, Class<? extends AbstractHandler>> links = new HashMap<>();
        links.put(START, DefaultHandler.class);
        handlerService = new HandlerImpl(links);
    }

    @Ignore
    @Test
    public void test() {
        //*     todo нужно поправить. Стандартный конструктор хендлера требует указывать бота
        AbstractHandler handler = handlerService.getHandler(START);
        assertEquals(DefaultHandler.class, handler.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException() {
        AbstractHandler handler = handlerService.getHandler(HELP);
        assertTrue(true);
    }

}