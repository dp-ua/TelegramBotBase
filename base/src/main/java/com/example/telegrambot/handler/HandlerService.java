package com.example.telegrambot.handler;

import com.example.telegrambot.command.CommandElement;

public interface HandlerService {

    AbstractHandler getHandler(CommandElement command);
}
