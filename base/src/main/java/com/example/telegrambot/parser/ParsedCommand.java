package com.example.telegrambot.parser;

import com.example.telegrambot.command.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParsedCommand {
    Command command = Command.NONE;
    String text="";
}
