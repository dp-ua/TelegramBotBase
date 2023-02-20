package com.example.telegrambot;

import com.example.telegrambot.handler.AbstractHandler;
import com.example.telegrambot.parser.AnalyzeResult;
import com.example.telegrambot.parser.ParsedCommand;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import static com.example.telegrambot.service.MsgService.END_LINE;

public class StartHandler extends AbstractHandler {
    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return null;
    }

    @Override
    public String operate(AnalyzeResult analyzeResult) {
        String chatId = msgService.getChatId(analyzeResult);
        SendMessage message = getMessageStart(chatId);
        msgService.sendMessage(message);
        return null;
    }

    private SendMessage getMessageStart(String chatID) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.enableMarkdown(true);
        StringBuilder text = new StringBuilder();
        text.append("Hello. I'm  *").append(bot.getBotUsername()).append("*").append(END_LINE);
        text.append("I'm just example").append(END_LINE);
        text.append("All that I can do - you can see calling the command [/help](/help)");
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
