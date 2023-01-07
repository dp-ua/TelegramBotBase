package com.example.telegrambot.command;

public interface CommandElement {
    String command();

    boolean isInTextCommand();
}
