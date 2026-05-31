package com.nic0im.minebot.Bots;

import com.nic0im.minebot.Enums.BotState;

import static com.nic0im.minebot.handlers.RailBotActions.handleLooking;
import static com.nic0im.minebot.handlers.RailBotActions.handlePlacing;

public class AutoMineBot {

    private static boolean enabled = false;

    public static BotState currentState = BotState.IDLE;

    public static void toggle() {

        enabled = !enabled;

        if (enabled) {

            currentState = BotState.LOOKING;

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

            case PLACING:

                handlePlacing();
                break;
            case LOOKING:

                handleLooking();
                break;
        }
    }

}
