# TelegramBotBase

Base for working Telegram Bot

## App.java
Starts the bot on the specified parameters "bot name" and "token"

## Class Bot.java
- Override basic methods of TelegramLongPollingBot.
- implement command botConnect. Register selected bot in TelegramAPI
- When receiving update only logged the event is not taking any action.

