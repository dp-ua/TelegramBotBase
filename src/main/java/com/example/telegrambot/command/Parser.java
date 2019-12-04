package com.example.telegrambot.command;

import javafx.util.Pair;

public class Parser {
    String botName;

    public Parser(String botName) {
        this.botName = botName;
    }

    public ParsedCommand getParsedCommand(String text) {
        ParsedCommand result = new ParsedCommand();
        if (text == null || "".equals(text.trim())) return result;
        Pair<String, String> commandText = new Pair<>("", "");

        if (text.trim().contains(" ")) {
        }
        if (text.trim().startsWith("/")) {
        }


        return result;
    }
}
