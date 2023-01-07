package com.example.telegrambot.command.impl;

import com.example.telegrambot.command.CommandElement;

public enum CommandProvider implements CommandElement {
    START, HELP,
    INTEXTCOMMAND("in text command",true);
    ;
    String command;
    boolean inText;

    CommandProvider() {
        command = this.toString().toLowerCase();
    }

    CommandProvider(String command, boolean inText) {
        this.inText = inText;
        this.command = command;
    }

    @Override
    public String command() {
        return command;
    }

    @Override
    public boolean isInTextCommand() {
        return inText;
    }
}
