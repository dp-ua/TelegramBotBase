package com.example.telegrambot;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.command.CommandElement;
import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.handler.HandlerService;
import com.example.telegrambot.handler.impl.HandlerImpl;
import com.example.telegrambot.parser.ParserService;
import com.example.telegrambot.parser.impl.ParserImpl;
import com.example.telegrambot.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.generics.BotSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "321644283";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        String botName = System.getenv("test_bot_name");
        String botToken = System.getenv("test_bot_token");

        List<Constructed> checkList = new ArrayList<>();

        QueueProvider queueProvider = new QueueProvider();
        checkList.add(queueProvider);

        MsgService msgService = new MsgService();
        checkList.add(msgService);
        msgService.setQueueProvider(queueProvider);

        Map<CommandElement, Class<? extends AbstractHandler>> links = new HashMap<>();
        links.put(new StartCommand(), StartHandler.class);
        HandlerService handlerService = new HandlerImpl(links);
        checkList.add(handlerService);

        ParserService parser = new ParserImpl();
        parser.setBotName(botName);
        parser.setCommands(new ArrayList<>(links.keySet()));
        checkList.add(parser);

        Bot test_habr_bot = new Bot(botName, botToken);
        checkList.add(test_habr_bot);
        test_habr_bot.setMsgService(msgService);


        MessageReceiver messageReceiver = new MessageReceiver(test_habr_bot, queueProvider);
        checkList.add(messageReceiver);
        messageReceiver.setHandlerService(handlerService);
        messageReceiver.setParserService(parser);
        messageReceiver.setMsgService(msgService);

        MessageSender messageSender = new MessageSender(test_habr_bot, queueProvider.getSendQueue());
        checkList.add(messageSender);

        checkElementsBeforeStart(checkList);

        BotSession connect = test_habr_bot.connect();
        log.info("StartBotSession. Bot started. " + connect.toString());

        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReceiver");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();

        sendStartReport(msgService);
    }

    private static void checkElementsBeforeStart(List<Constructed> checkList) {
        checkList.forEach(element -> {
            if (element.isConstructed()) {
                log.info(element + " Constructed");
            } else {
                throw new IllegalArgumentException(element + " Not Constructed");
            }
        });
    }

    private static void sendStartReport(MsgService msgService) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(BOT_ADMIN);
        sendMessage.setText("Запустился");
        msgService.sendMessage(sendMessage);
    }
}
