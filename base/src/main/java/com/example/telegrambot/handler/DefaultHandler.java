package com.example.telegrambot.handler;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.parser.ParsedCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;

public class DefaultHandler extends AbstractHandler {
    private static final Logger log = LogManager.getLogger(DefaultHandler.class);

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "";
    }
}
