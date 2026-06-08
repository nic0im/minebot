package com.nic0im.minebot.Bots;

import com.nic0im.minebot.Enums.BotState;

import static com.nic0im.minebot.Helpers.Walker.cleanWalker;
import static com.nic0im.minebot.handlers.BrewerBotActions.*;

public class AutoBrewerBot {

    private static boolean enabled = false;

    public static BotState currentState = BotState.IDLE;

    public static void toggle() {

        enabled = !enabled;

        if (enabled) {
            cleanWalker();
            cleanUp();
            currentState = BotState.WALKING;

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

            case WALKING:

                handleWalking();
                break;

            case LOOKING:

                handleLooking();
                break;

            case FILLING:

                handleFilling();
                break;

            case ROTATING:
                handleRotating();
                break;
        }
    }
}
