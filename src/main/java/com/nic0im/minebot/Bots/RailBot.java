package com.nic0im.minebot.Bots;


import com.nic0im.minebot.Enums.BotState;

import static com.nic0im.minebot.handlers.RailBotActions.*;

public class RailBot {

    private static boolean enabled = false;

    public static BotState currentState = BotState.IDLE;

    public static void toggle() {

        enabled = !enabled;

        if (enabled) {

            currentState = BotState.LOOKING;

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

            case PLACING:

                handlePlacing();
                break;
            case LOOKING:
                System.out.println("Handle Looking");
                handleLooking();
                break;
        }
    }

}
