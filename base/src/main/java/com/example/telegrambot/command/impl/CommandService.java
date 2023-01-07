package com.example.telegrambot.command.impl;

import com.example.telegrambot.command.CommandElement;

public enum CommandService implements CommandElement {
    START, HELP,
    INTEXTCOMMAND("in text command",true);
    ;
    String command;
    boolean inText;

    CommandService() {
        command = this.toString().toLowerCase();
    }

    CommandService(String command, boolean inText) {
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
