package com.example.telegrambot.parser;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.service.Constructed;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public interface ParserService extends Constructed {
    void setBotName(String botName);

    void setCommands(List<CommandElement> commands);

    AnalyzeResult getUpdateAnalyse(Update update);

    @Override
    boolean isConstructed();
}
