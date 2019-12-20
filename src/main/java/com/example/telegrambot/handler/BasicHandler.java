package com.example.telegrambot.handler;

import com.example.telegrambot.command.ParsedCommand;

public interface BasicHandler {
    String operate(String chatId, ParsedCommand parsedCommand);
}
