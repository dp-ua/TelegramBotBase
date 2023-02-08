package com.example.telegrambot.builder;

import com.example.telegrambot.bot.Bot;
import com.example.telegrambot.service.MessageReceiver;
import com.example.telegrambot.service.MessageSender;
import com.example.telegrambot.service.QueueProvider;
import com.google.common.base.Strings;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.generics.BotSession;

import java.awt.*;

public class BotBuilder {
    private static final Logger log = LogManager.getLogger(BotBuilder.class);

    private static final int PRIORITY_FOR_SENDER = 1;
    private static final int PRIORITY_FOR_RECEIVER = 3;
    private static final String BOT_ADMIN = "321644283";

    private Bot bot;
    @Setter
    private String botAdmin;

    MessageReceiver messageReceiver;
    MessageSender messageSender;

    QueueProvider queueProvider = new QueueProvider();
    Thread msgSender;
    Thread msgReceiver;
    BotSession botSession;

/* todo вынести отдельно
    -модуль статистики
 */
/* todo подготовлено:
    -вынесены треды сендера и ресивера. Нужно для подключения отдельного модуля, который будет наблюдать за жизнью бота
    -бот сессия. так же вынесена, по ней можно так же вести лайвлог
 */

    public BotBuilder build() {

        messageReceiver = new MessageReceiver(bot, queueProvider);
        messageSender = new MessageSender(bot, queueProvider.getSendQueue());

        return null;
    }

    public Bot setupBot(String botName, String botToken) {
        checkString(botName, "botName");
        return new Bot(botName, botToken);
    }

    private void checkString(String botName, String variableName) {
        if (Strings.isNullOrEmpty(botName)) {
            log.error(variableName + " is null or empty");
            throw new IllegalArgumentException(variableName + " is null or empty");
        }
    }

    public void start() throws IllegalComponentStateException {
        if (!isConstructed()) {
            log.error("BotBuilder didn't construct");
            throw new IllegalComponentStateException("BotBuilder didn't construct");
        }
        ApiContextInitializer.init();

        msgSender = startSender();
        msgReceiver = startReceiver();

        botSession = bot.connect();

        sendStartReport(bot);
    }

    private boolean isConstructed() {
        // todo нужно предумать механизм, как мы будем чекать все ли добавлено
        // и можно ли как-то использоваться еще флаг Constructed
        return false;
    }

    private void sendStartReport(Bot bot) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getAdminId());
        sendMessage.setText("Запустился");
        bot.sendQueue.add(sendMessage);
    }

    private String getAdminId() {
        return Strings.isNullOrEmpty(botAdmin) ? BOT_ADMIN : botAdmin;
    }


    private Thread startSender() {
        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.setPriority(PRIORITY_FOR_SENDER);
        sender.start();
        return sender;
    }

    private Thread startReceiver() {
        Thread receiver = new Thread(messageReceiver);
        receiver.setDaemon(true);
        receiver.setName("MsgReciever");
        receiver.setPriority(PRIORITY_FOR_RECEIVER);
        receiver.start();
        return receiver;
    }
}
