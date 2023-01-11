package com.example.telegrambot.service;

import com.example.telegrambot.parser.MessageType;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.AbstractMap;
import java.util.Map;

public class MsgService {

    public static final String LINE_END = "\n";
    public static final String PREFIX_FOR_COMMAND = "/";


    public MessageType getMessageType(Update update) {
        if (update.hasMessage()) return MessageType.MESSAGE;
        throw new IllegalStateException("Can't detect message type");
    }

    // todo test me
    public String getMessageText(Update update, MessageType messageType) {
        switch (messageType) {
            case MESSAGE:
                Message message = update.getMessage();
                if (message.hasText()) return message.getText();
                break;
        }
        throw new IllegalStateException("Can't get message text");
    }

    // todo test me
    public Map.Entry<String, String> parseBotCommandAndTextFromFullText(String text) {
        Map.Entry<String, String> commandText;

        if (text.contains(" ") || text.contains(LINE_END)) {
            int indexOfSpace = text.indexOf(" ");
            int indexOfNewLine = text.indexOf(LINE_END);
            int indexOfCommandEnd;
            if (indexOfNewLine != -1 && indexOfSpace != -1)
                indexOfCommandEnd = Math.min(indexOfNewLine, indexOfSpace);
            else if (indexOfNewLine != -1) indexOfCommandEnd = indexOfNewLine;
            else indexOfCommandEnd = indexOfSpace;

            commandText = new AbstractMap.SimpleImmutableEntry<>(text.substring(0, indexOfCommandEnd), text.substring(indexOfCommandEnd + 1));
        } else commandText = new AbstractMap.SimpleImmutableEntry<>(text, "");
        return commandText;
    }
}
