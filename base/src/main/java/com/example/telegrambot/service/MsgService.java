package com.example.telegrambot.service;

import com.example.telegrambot.parser.AnalyzeResult;
import com.example.telegrambot.parser.MessageType;
import com.google.common.base.Strings;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.AbstractMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class MsgService implements Constructed {
    private static final Logger log = LogManager.getLogger(MsgService.class);
    public static final String END_LINE = "\n";
    public static final String PREFIX_FOR_COMMAND = "/";

    @Setter
    QueueProvider queueProvider;

    @Override
    public boolean isConstructed() {
        return queueProvider != null;
    }

    public final void acceptMessage(Update update) {
        BlockingQueue<Object> queue = queueProvider.getReceiveQueue();
        queue.add(update);
        log.debug("Receive new Update. Queue size: " + queue.size() + " updateID: " + update.getUpdateId());
    }

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

        if (text.contains(" ") || text.contains(END_LINE)) {
            int indexOfSpace = text.indexOf(" ");
            int indexOfNewLine = text.indexOf(END_LINE);
            int indexOfCommandEnd;
            if (indexOfNewLine != -1 && indexOfSpace != -1)
                indexOfCommandEnd = Math.min(indexOfNewLine, indexOfSpace);
            else if (indexOfNewLine != -1) indexOfCommandEnd = indexOfNewLine;
            else indexOfCommandEnd = indexOfSpace;

            commandText = new AbstractMap.SimpleImmutableEntry<>(text.substring(0, indexOfCommandEnd), text.substring(indexOfCommandEnd + 1));
        } else commandText = new AbstractMap.SimpleImmutableEntry<>(text, "");
        return commandText;
    }

    public boolean contains(String find, String where) {
        if (Strings.isNullOrEmpty(find) || Strings.isNullOrEmpty(where)) return false;
        if (find.split("\\w+").length == find.length()) return false;
        return transformString(where)
                .contains
                        (transformString(find));
    }

    private String transformString(String where) {
        String mask = "!.f&?";
        String[] split = where.split("\\W+");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : split) {
            stringBuilder
                    .append(mask)
                    .append(word)
                    .append(mask);
        }
        return stringBuilder.toString();
    }

    //todo Test me
    public String getChatId(AnalyzeResult analyzeResult) {
        // todo уметь вытаскивать айди чата из любого типа сообщений
        throw new NotImplementedException();
    }

    public void sendMessage(SendMessage message) {
        try {
            queueProvider.getSendQueue().put(message);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
