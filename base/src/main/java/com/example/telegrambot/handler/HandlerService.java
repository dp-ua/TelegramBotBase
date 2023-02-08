package com.example.telegrambot.handler;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.service.Constructed;

public interface HandlerService extends Constructed {
    AbstractHandler getHandler(CommandElement command);

    @Override
    boolean isConstructed();
}
