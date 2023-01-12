package com.example.telegrambot.parser;

import com.example.telegrambot.command.CommandElement;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.api.objects.Update;

import java.util.List;

@Getter
public class AnalyzeResult {

    private Update update;
    @Setter
    private MessageType messageType;

    @Setter
    private List<CommandElement> commands;

    public AnalyzeResult(Update update) {
        this.update = update;
    }
}
