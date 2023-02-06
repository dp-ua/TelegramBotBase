package com.example.telegrambot.handler;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.parser.ParsedCommand;
import com.example.telegrambot.service.MsgService;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.Update;

public abstract class AbstractHandler {
    @Setter
    protected Bot bot;
    @Setter
    protected MsgService msgService;


    public final boolean isConstructed() {
        return bot != null &&
                msgService != null;

    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
