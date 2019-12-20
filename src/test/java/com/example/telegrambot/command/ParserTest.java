package com.example.telegrambot.command;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    private static final String botName = "bot";
    private Parser parser;

    @Before
    public void setParser() {
        parser = new Parser(botName);
    }

    @Test
    public void getParsedCommand_None() {
        String text = "just text";
        ParsedCommand parsedCommandAndText = parser.getParsedCommand(text);
        assertEquals(Command.NONE, parsedCommandAndText.command);
        assertEquals(text, parsedCommandAndText.text);
    }

    @Test
    public void getParsedCommand_NotForMe() {
        String text = "/test@another_Bot just text";
        ParsedCommand parsedCommandAndText = parser.getParsedCommand(text);
        assertEquals(Command.NOTFORME, parsedCommandAndText.command);
    }

    @Test
    public void getParsedCommand_NoneButForMe() {
        String text = "/test@" + botName + " just text";
        ParsedCommand parsedCommandAndText = parser.getParsedCommand(text);
        assertEquals(Command.NONE, parsedCommandAndText.command);
        assertEquals("just text", parsedCommandAndText.text);
    }

}