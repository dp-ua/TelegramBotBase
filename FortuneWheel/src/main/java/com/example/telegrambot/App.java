package com.example.telegrambot;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.service.MessageReceiver;
import com.example.telegrambot.service.MessageSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.send.SendMessage;

public class App {
    private static final Logger log = LogManager.getLogger(App.class);
    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "321644283";

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot test_habr_bot = new Bot(System.getenv("test_bot_name"), System.getenv("test_bot_token"));

        MessageReceiver messageReceiver = new MessageReceiver(test_habr_bot);
        MessageSender messageSender = new MessageSender(test_habr_bot);

        test_habr_bot.connect();

        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();

        sendStartReport(test_habr_bot);
    }

    private static void sendStartReport(Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(BOT_ADMIN);
        sendMessage.setText("Запустился");
        bot.sendQueue.add(sendMessage);
    }
}
