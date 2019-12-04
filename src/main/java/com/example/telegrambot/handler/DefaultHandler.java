package com.example.telegrambot.handler;

import com.example.telegrambot.command.ParsedCommand;

public class DefaultHandler implements BasicHandler {
    @Override
    public String operate(String chatId, ParsedCommand parsedCommand) {
        return "";
    }
}
