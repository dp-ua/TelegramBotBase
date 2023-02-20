package com.example.telegrambot.handler.impl;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.handler.HandlerService;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class HandlerImpl implements HandlerService {

    private final Map<CommandElement, Class<? extends AbstractHandler>> links;

    public HandlerImpl(@NotNull Map<CommandElement, Class<? extends AbstractHandler>> links) {
        this.links = links;
    }

    @Override
    public boolean isConstructed() {
        return links != null && links.size() != 0;
    }


    @Override
    public AbstractHandler getHandler(@NotNull CommandElement command) {
        if (links.containsKey(command)) {
            Class<? extends AbstractHandler> handlerClass = links.get(command);
            try {
                return handlerClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new IllegalArgumentException("Handler not found for command{" + command.toString());
        }
    }
}
