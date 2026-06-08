package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Bots.AutoBrewerBot;
import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Enums.FillState;
import com.nic0im.minebot.Helpers.Walker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.item.Items;

import static com.mojang.text2speech.Narrator.LOGGER;
import static com.nic0im.minebot.Helpers.BlockInteraction.*;
import static com.nic0im.minebot.Helpers.InventoryInteraction.*;
import static com.nic0im.minebot.Utils.RotationUtils.*;

public class BrewerBotActions {

    private static int walkedBlocks;
    private static int lookingTicks;

    private static int rotatingCount;

    public static void handleWalking() {

        if (Walker.isWalking()) {

            var targetReached = Walker.handleWalking();

            if(targetReached){
                walkedBlocks++;
                AutoBrewerBot.currentState = BotState.LOOKING;
                LOGGER.info("target Reached");
            }

        }else{
            Walker.walkToFrontBlock();
        }

    }

    public static void handleLooking(){
        lookingTicks++;
        if(lookingTicks>=5){
            LOGGER.info("handle looking");
            lookRight();
            lookingTicks=0;
            AutoBrewerBot.currentState = BotState.FILLING;
        }

    }

    private static FillState fillState = FillState.OPENING;
    private static int waitTicks = 0;

    public static void handleFilling() {

        switch (fillState) {

            case OPENING -> {

                openLookedAtBlock();

                fillState = FillState.WAITING_FOR_MENU;
                waitTicks = 0;
            }

            case WAITING_FOR_MENU -> {

                if (isBrewingStandOpen()) {
                    fillState = FillState.THROWING_ITEMS;
                } else if (++waitTicks > 20) {
                    // timeout after 1 second
                    fillState = FillState.OPENING;
                }
            }

            case THROWING_ITEMS -> {
                if (isBrewingStandOpen()) {
                    //ThrowItem(0);
                    fillState = FillState.MOVING_ITEMS;
                } else if (++waitTicks > 20) {
                    // timeout after 1 second
                    fillState = FillState.OPENING;
                }
            }

            case MOVING_ITEMS -> {

                int lap = rotatingCount / 2;

                switch (lap) {

                    case 0 -> {
                        int slot = getContainerSlotByItem(Items.SUGAR);
                        //int slot = getContainerSlotByItem(Items.NETHER_WART);
                        ThrowItem(0);
                        //ThrowItem(1);
                        //ThrowItem(2);
                        MoveOneItem(slot, 3);
                        //
                        rotatingCount = 0;
                    }

                    case 1 -> {
                        int slot = getContainerSlotByItem(Items.SUGAR);
                        MoveOneItem(slot, 3);
                    }

                    case 2 -> {
                        int slot = getContainerSlotByItem(Items.REDSTONE);
                        MoveOneItem(slot, 3);
                    }

                    case 3 -> {
                        int slot = getContainerSlotByItem(Items.GUNPOWDER);
                        MoveOneItem(slot, 3);

                        LOGGER.info("Finished last ingredient, implement retrieval");
                    }
                    case 4 -> {
                        int slot = getContainerSlotByItem(Items.NETHER_WART);
                        ThrowItem(0);
                        MoveOneItem(slot, 3);
                        rotatingCount = 0;
                    }

                }

                fillState = FillState.CLOSING;
                waitTicks = 0;
            }

            case CLOSING -> {

                CloseInventoryMenu();

                fillState = FillState.DONE;
            }

            case DONE -> {
                if(walkedBlocks%10==0){
                    AutoBrewerBot.currentState = BotState.ROTATING;
                }else{
                    AutoBrewerBot.currentState = BotState.WALKING;
                    lookLeft();
                }

                fillState = FillState.OPENING;
            }
        }
    }

    public static void handleRetrieving(){

    }

    public static void handleRotating(){
        rotatingCount++;
        rotate180();
        AutoBrewerBot.currentState = BotState.FILLING;
        walkedBlocks++;
    }

    public static void cleanUp(){
        walkedBlocks = 0;
        lookingTicks = 0;
        rotatingCount = 0;
    }

}
