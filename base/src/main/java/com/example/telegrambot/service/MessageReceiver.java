package com.example.telegrambot.service;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.command.Command;
import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.handler.DefaultHandler;
import com.example.telegrambot.handler.EmojiHandler;
import com.example.telegrambot.handler.SystemHandler;
import com.example.telegrambot.parser.ParsedCommand;
import com.example.telegrambot.parser.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.stickers.Sticker;

import java.util.concurrent.BlockingQueue;


public class MessageReceiver implements Runnable, Constructed {
    private static final Logger log = LogManager.getLogger(MessageReceiver.class);
    private final Bot bot;
    private final Parser parser;
    private final BlockingQueue<Object> receiveQueue;


    public MessageReceiver(Bot bot, BlockingQueue<Object> receiveQueue, Parser parser) {
        this.bot = bot;
        this.receiveQueue = receiveQueue;
        this.parser = parser;
    }

    @Override
    public boolean isConstructed() {
        return bot != null &&
                parser != null &&
                receiveQueue != null;
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgReciever.  Bot class: " + bot);
        while (true) {
            try {
                Object object = receiveQueue.take();
                log.debug("New object for analyze in queue " + object);
                analyze(object);
            } catch (InterruptedException e) {
                log.error("Catch interrupt. Exit", e);
                return;
            } catch (Exception e) {
                log.error("Exception in Receiver. ", e);
            }
        }
    }

    private void analyze(Object object) {
        if (object instanceof Update) {
            Update update = (Update) object;
            log.debug("Update recieved: " + update);
            analyzeForUpdateType(update);
        } else log.warn("Cant operate type of object: " + object.toString());
    }

    private void analyzeForUpdateType(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();

        ParsedCommand parsedCommand = new ParsedCommand(Command.NONE, "");

        if (message.hasText()) {
            parsedCommand = parser.getParsedCommand(message.getText());
        } else {
            Sticker sticker = message.getSticker();
            if (sticker != null) {
                parsedCommand = new ParsedCommand(Command.STICKER, sticker.getFileId());
            }
        }

        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());
        String operationResult = null;
        if (handlerForCommand.isConstructed()) {
            operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);
        }

        if (!"".equals(operationResult)) {
            SendMessage messageOut = new SendMessage();
            messageOut.setChatId(chatId);
            messageOut.setText(operationResult);
            bot.sendQueue.add(messageOut);
        }
    }

    private AbstractHandler getHandlerForCommand(Command command) {
        if (command == null) {
            log.warn("Null command accepted. This is not good scenario.");
            return getDefaultHandler();
        }
        switch (command) {
            case START:
            case HELP:
            case ID:
            case STICKER:
                SystemHandler systemHandler = new SystemHandler();
                systemHandler.setBot(bot);
                log.info("Handler for command[" + command + "] is: " + systemHandler);
                return systemHandler;
            case TEXT_CONTAIN_EMOJI:
                EmojiHandler emojiHandler = new EmojiHandler();
                emojiHandler.setBot(bot);
                log.info("Handler for command[" + command + "] is: " + emojiHandler);
                return emojiHandler;
            default:
                log.info("Handler for command[" + command + "] not Set. Return DefaultHandler");
                return getDefaultHandler();
        }
    }

    private DefaultHandler getDefaultHandler() {
        DefaultHandler defaultHandler = new DefaultHandler();
        defaultHandler.setBot(bot);
        return defaultHandler;
    }
}
