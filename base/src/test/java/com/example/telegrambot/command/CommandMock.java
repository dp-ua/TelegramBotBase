package com.example.telegrambot.command;

import com.example.telegrambot.command.CommandElement;

public enum CommandMock implements CommandElement {

    START("start", false),
    HELP("help", false),
    INTEXT("in text command", true);

    final String command;
    final boolean inText;

    CommandMock(String command, boolean inText) {
        this.command = command;
        this.inText = inText;
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
