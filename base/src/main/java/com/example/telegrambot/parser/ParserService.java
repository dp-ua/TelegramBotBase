package com.example.telegrambot.parser;

import com.example.telegrambot.command.CommandElement;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

public interface ParserService {
    void setBotName(String botName);

    void setCommands(List<CommandElement> commands);

    AnalyzeResult getUpdateAnalyse(Update update);

    boolean isConstructed();
}
