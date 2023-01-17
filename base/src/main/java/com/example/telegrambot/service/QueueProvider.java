package com.example.telegrambot.service;

import lombok.Getter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Getter
public class QueueProvider {
    private final BlockingQueue<Object> sendQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Object> receiveQueue = new LinkedBlockingQueue<>();
}
