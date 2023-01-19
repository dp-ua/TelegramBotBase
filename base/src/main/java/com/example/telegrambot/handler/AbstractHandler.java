package com.example.telegrambot.handler;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.parser.ParsedCommand;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.Update;

public abstract class AbstractHandler {
    @Setter
    Bot bot;

    public final boolean isConstructed() {
        return bot != null;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
