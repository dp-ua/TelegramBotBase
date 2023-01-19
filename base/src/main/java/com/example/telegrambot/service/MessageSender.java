package com.example.telegrambot.service;

import com.example.telegrambot.bot.Bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendSticker;
import org.telegram.telegrambots.api.objects.Message;

import java.util.concurrent.BlockingQueue;

public class MessageSender implements Runnable, Constructed {
    private static final Logger log = LogManager.getLogger(MessageSender.class);
    private final Bot bot;

    private final BlockingQueue<Object> sendQueue;

    public MessageSender(Bot bot, BlockingQueue<Object> sendQueue) {
        this.bot = bot;
        this.sendQueue = sendQueue;
    }

    @Override
    public boolean isConstructed() {
        return bot != null &&
                sendQueue != null;
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgSender.  Bot class: " + bot);
        while (true) {
            try {
                Object object = sendQueue.take();
                log.debug("Get new msg to send " + object);
                send(object);
            } catch (InterruptedException e) {
                log.error("Take interrupt while operate msg list", e);
                return;
            } catch (Exception e) {
                log.error(e);
            }
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    log.debug("Use Execute for " + object);
                    bot.execute(message);
                    break;
                case STICKER:
                    SendSticker sendSticker = (SendSticker) object;
                    log.debug("Use SendSticker for " + object);
                    bot.sendSticker(sendSticker);
                    break;
                default:
                    log.warn("Cant detect type of object. " + object);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private MessageType messageType(Object object) {
        if (object instanceof SendSticker) return MessageType.STICKER;
        if (object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE, STICKER, NOT_DETECTED,
    }
}
