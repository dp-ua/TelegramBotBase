package com.example.telegrambot.bot;

import com.example.telegrambot.service.MsgService;
import com.google.common.base.Strings;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.generics.BotSession;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@NoArgsConstructor
public class Bot extends TelegramLongPollingBot {
    private static final Logger log = LogManager.getLogger(Bot.class);
    private final int RECONNECT_PAUSE = 10000;
    @Setter
    MsgService msgService;
    @Setter
    private String botName;

    @Setter
    private String botToken;

    public final boolean isConstructed() {
        return !Strings.isNullOrEmpty(botName) &&
                !Strings.isNullOrEmpty(botToken) &&
                msgService != null;
    }

    public final BlockingQueue<Object> sendQueue = new LinkedBlockingQueue<>();

    public Bot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
        log.info("Bot name: " + botName);
    }

    @Override
    public void onUpdateReceived(Update update) {
        msgService.acceptMessage(update);
    }

    @Override
    public String getBotUsername() {
        log.debug("Bot name: " + botName);
        return botName;
    }

    @Override
    public String getBotToken() {
        log.debug("Bot token: " + botToken);
        return botToken;
    }

    public BotSession connect() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        BotSession botSession = null;
        try {
            botSession = telegramBotsApi.registerBot(this);
            log.info("[STARTED] TelegramAPI. Bot Connected. Bot class: " + this);
        } catch (TelegramApiRequestException e) {
            log.error("Cant Connect. Pause " + RECONNECT_PAUSE / 1000 + "sec and try again. Error: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return null;
            }
            connect();
        }
        return botSession;
    }
}
