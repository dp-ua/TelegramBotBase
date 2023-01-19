package com.example.telegrambot.service;

import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class QueueProvider implements Constructed {
    private final BlockingQueue<Object> sendQueue;
    private final BlockingQueue<Object> receiveQueue;

    public QueueProvider() {
        sendQueue = new LinkedBlockingQueue<>();
        receiveQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public boolean isConstructed() {
        return sendQueue != null && receiveQueue != null;
    }
}
