package com.nic0im.minebot.Bots;

import com.nic0im.minebot.Enums.BotState;

import static com.nic0im.minebot.handlers.FarmBotActions.*;

public class AutoFarmBot {

    private static boolean enabled = false;

    public static BotState currentState = BotState.IDLE;

    public static void toggle() {

        enabled = !enabled;

        if (enabled) {

            currentState = BotState.SEARCHING;

            System.out.println("Bot enabled");
        }
        else {

            currentState = BotState.IDLE;

            System.out.println("Bot disabled");
        }
    }

    public static void tick() {

        if (!enabled)
            return;

        switch (currentState) {

            case SEARCHING:
                handleSearching();
                break;

            case LOOKING:
                handleLooking();
                break;

            case BREAKING:
                handleBreaking();
                break;

            case REPLANTING:
                handleReplanting();
                break;

            case GROWING:
                handleGrowing();
                break;
        }
    }
}
