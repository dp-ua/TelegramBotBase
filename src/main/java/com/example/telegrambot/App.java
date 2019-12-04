package com.example.telegrambot;

import com.example.telegrambot.bot.Bot;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;

public class App {
    private static final Logger log = Logger.getLogger(App.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Bot test_habr_bot = new Bot("test_habr_bot", "1012522162:AAGfmwth5jQeZhLwiVEGs6bWbLHUTKuustE");
        test_habr_bot.botConnect();
    }
}
