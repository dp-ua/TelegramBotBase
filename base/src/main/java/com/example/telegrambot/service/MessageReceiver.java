package com.example.telegrambot.service;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.handler.HandlerService;
import com.example.telegrambot.parser.AnalyzeResult;
import com.example.telegrambot.parser.ParserService;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.objects.Update;

import java.util.concurrent.BlockingQueue;


public class MessageReceiver implements Runnable, Constructed {
    private static final Logger log = LogManager.getLogger(MessageReceiver.class);
    private final Bot bot;
    private QueueProvider queueProvider;

    @Setter
    private ParserService parserService;

    @Setter
    private HandlerService handlerService;

    @Setter
    private MsgService msgService;

    public MessageReceiver(Bot bot, QueueProvider queueProvider) {
        this.bot = bot;
        this.queueProvider = queueProvider;
    }

    @Override
    public boolean isConstructed() {
        return bot != null &&
                queueProvider != null &&
                parserService != null &&
                handlerService != null &&
                msgService != null;
    }

    @Override
    public void run() {
        log.info("[STARTED] MsgReceiver.  Bot class: " + bot);
        BlockingQueue<Object> receiveQueue = queueProvider.getReceiveQueue();
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
            log.debug("Update received: " + update);
            analyzeUpdate(update);
        } else log.warn("Cant operate type of object: " + object.toString());
    }

    private void analyzeUpdate(Update update) {
        AnalyzeResult analyze = parserService.getUpdateAnalyse(update);
        analyze.getCommands().forEach(command -> {
            AbstractHandler handler = handlerService.getHandler(command);
            handler.setBot(bot);
            handler.setMsgService(msgService);
            if (handler.isConstructed()) {
                // результат работы хендлера никуда отправлять не будем.
                handler.operate(analyze);
            } else {
                handlerNotConstructed(handler);
            }
        });
    }

    private void handlerNotConstructed(AbstractHandler handler) {
        log.error("Not constructed handler: " + handler);
    }
}
