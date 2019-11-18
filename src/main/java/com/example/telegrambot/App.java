package com.example.telegrambot;

import com.example.telegrambot.bot.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

/**
 * Hello world!
 */
public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot test_habr_bot = new Bot("test_habr_bot", "1012522162:AAHLvPVqKF48LdqnsvS3l5YrJfvFey6dBa0");
        test_habr_bot.botConnect();
    }
}
