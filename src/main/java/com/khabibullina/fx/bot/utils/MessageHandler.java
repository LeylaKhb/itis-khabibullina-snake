package com.khabibullina.fx.bot.utils;


import java.util.HashMap;
import java.util.Map;



public class MessageHandler {
    private static final String GAME_STARTING_PHRASE = "You are going to game";
    private static final String HELP_ANSWER = "You can get game by /game";
    private static final String GAME_COMMAND = "/game";




    public String handleMessage(String message) {
        String answer = HELP_ANSWER;
        switch (message) {
            case GAME_COMMAND -> answer = GAME_STARTING_PHRASE;
        }
        return answer;
    }

    public String getGameStartingPhrase() {
        return GAME_STARTING_PHRASE;
    }
}
