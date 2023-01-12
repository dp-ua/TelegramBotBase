package com.example.telegrambot.parser.impl;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.parser.AnalyzeResult;
import com.example.telegrambot.parser.ParserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

import static com.example.telegrambot.command.impl.CommandProvider.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ParserImplTest {
    private static final String BOTNAME = "botname";
    private static final String WRONG_BOTNAME = "wrongBotname";
    Update update;
    Message message;

    ParserService parser;

    @Before
    public void setup() {
        update = Mockito.mock(Update.class);
        message = Mockito.mock(Message.class);
        Mockito.when(update.hasMessage()).thenReturn(true);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.hasText()).thenReturn(true);

        parser = new ParserImpl();
        parser.setBotName(BOTNAME);
        parser.setCommands(getCommands());
    }

    /* todo
        добавить тесты для команд внутри текста
     */

    @Test
    public void shouldBeConstructed() {
        assertTrue(parser.isConstructed());
    }

    @Test
    public void shouldRecognize_START_clear_case1() {
        mockMessageText("/start");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case2() {
        mockMessageText("/start ");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case3() {
        mockMessageText("/start\n");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case4() {
        mockMessageText("/start \n");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case5() {
        mockMessageText("/start sometext");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case6() {
        mockMessageText("/start " + INTEXTCOMMAND.command());
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_clear_case7() {
        mockMessageText("/start\n" + INTEXTCOMMAND.command());
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_with_BotName_case1() {
        mockMessageText("/start" + "@" + BOTNAME);
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_with_BotName_case2() {
        mockMessageText("/start" + "@" + BOTNAME + " ");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_with_BotName_case3() {
        mockMessageText("/start" + "@" + BOTNAME + "\n");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_with_BotName_case4() {
        mockMessageText("/start" + "@" + BOTNAME + " \n");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldRecognize_START_with_BotName_case5() {
        mockMessageText("/start" + "@" + BOTNAME + " sometext");
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(1, commands.size());
        assertEquals(START, commands.get(0));
    }

    @Test
    public void shouldNotRecognize_START_with_WrongBotName_case1() {
        mockMessageText("/start" + "@" + WRONG_BOTNAME);
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(0, commands.size());
    }

    @Test
    public void shouldNotRecognize_START_with_WrongBotName_case2() {
        mockMessageText("/start" + "@" + WRONG_BOTNAME + " " + INTEXTCOMMAND.command());
        AnalyzeResult updateAnalyse = parser.getUpdateAnalyse(update);
        List<CommandElement> commands = updateAnalyse.getCommands();
        assertEquals(0, commands.size());
    }


    private List<CommandElement> getCommands() {
        List<CommandElement> list = new ArrayList<>();
        list.add(START);
        list.add(HELP);
        list.add(INTEXTCOMMAND);
        return list;
    }

    private void mockMessageText(String text) {
        Mockito.when(message.getText()).thenReturn(text);
    }
}