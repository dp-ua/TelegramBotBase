package com.example.telegrambot.command.impl;

import com.example.telegrambot.command.CommandElement;
import org.junit.Test;

import static com.example.telegrambot.command.impl.CommandProvider.*;
import static org.junit.Assert.*;

public class CommandProviderTest {
    CommandElement command;

    @Test
    public void commandTest_case_Start() {
        command = START;
        assertEquals("start", command.command());
        assertFalse(command.isInTextCommand());
    }

    @Test
    public void commandTest_case_Help() {
        command = HELP;
        assertEquals("help", command.command());
        assertFalse(HELP.isInTextCommand());
    }
    @Test
    public void commandTest_case_InTextCommand() {
        command = INTEXTCOMMAND;
        assertEquals("in text command", command.command());
        assertTrue(command.isInTextCommand());
    }
}