package com.example.telegrambot.parser.impl;

import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.parser.AnalyzeResult;
import com.example.telegrambot.parser.MessageType;
import com.example.telegrambot.parser.ParserService;
import com.example.telegrambot.service.MsgService;
import com.google.common.base.Strings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ParserImpl implements ParserService {
    private static final Logger log = LogManager.getLogger(ParserService.class);
    private final String DELIMITER_COMMAND_BOTNAME = "@";
    private final MsgService msgService = new MsgService();

    private String botName;
    private List<CommandElement> commands;


    @Override
    public void setBotName(String botName) {
        this.botName = botName;
    }

    @Override
    public void setCommands(List<CommandElement> commands) {
        this.commands = commands;
    }

    @Override
    public AnalyzeResult getUpdateAnalyse(Update update) {
        AnalyzeResult result = prepareClearAnalyzeResult(update);

        try {
            result.setMessageType(getMessageType(update));

            switch (result.getMessageType()) {
                case MESSAGE:
                    String messageText = msgService.getMessageText(update, result.getMessageType());
                    List<CommandElement> detectedCommands = getCommandsFromText(messageText);
                    result.setCommands(detectedCommands);
                    break;
            }
        } catch (Exception e) {
            log.error("Something goes wrong:{" + e.getMessage() + "} " + update.toString(), e);
        }

        return result;
    }

    private List<CommandElement> getCommandsFromText(String text) {
        List<CommandElement> result = new ArrayList<>();

        String preparedText = prepareText(text);
        if (isCommand(preparedText)) {
            Map.Entry<String, String> commandAndText = msgService.parseBotCommandAndTextFromFullText(preparedText);
            if (isCommandForMe(commandAndText.getKey())) {
                String command = cutCommandFromFullText(commandAndText.getKey());
                commands.stream()
                        .filter(element -> !element.isInTextCommand())
                        .forEach(element -> {
                            if (element.command().equals(command)) result.add(element);
                        });
            }
        } else {
            commands.stream()
                    .filter(CommandElement::isInTextCommand)
                    .forEach(element -> {
                        if (preparedText.equals(element.command()) || preparedText.contains(element.command())) {
                            result.add(element);
                        }
                    });
        }
        return result;
    }

    private String prepareText(String text) {
        return text
                .trim()
                .toLowerCase();
    }

    private String cutCommandFromFullText(String text) {
        return text.contains(DELIMITER_COMMAND_BOTNAME) ?
                text.substring(1, text.indexOf(DELIMITER_COMMAND_BOTNAME)) :
                text.substring(1);
    }

    private boolean isCommand(String text) {
        return text.startsWith(MsgService.PREFIX_FOR_COMMAND);
    }

    private boolean isCommandForMe(String text) {
        if (text.contains(DELIMITER_COMMAND_BOTNAME)) {
            String botNameForEqual = text.substring(text.indexOf(DELIMITER_COMMAND_BOTNAME) + 1);
            return botName.equals(botNameForEqual);
        }
        return true;
    }

    @Override
    public boolean isConstructed() {
        if (Strings.isNullOrEmpty(botName)) return false;
        return commands != null && commands.size() != 0;
    }


    private AnalyzeResult prepareClearAnalyzeResult(Update update) {
        AnalyzeResult result = new AnalyzeResult(update);
        result.setCommands(Collections.EMPTY_LIST);
        return result;
    }

    private MessageType getMessageType(Update update) {
        return msgService.getMessageType(update);
    }
}
