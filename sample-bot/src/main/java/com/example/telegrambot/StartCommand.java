package com.example.telegrambot;

import com.example.telegrambot.command.CommandElement;

public class StartCommand implements CommandElement {
    @Override
    public String command() {
        return "START";
    }

    @Override
    public boolean isInTextCommand() {
        return false;
    }
}
