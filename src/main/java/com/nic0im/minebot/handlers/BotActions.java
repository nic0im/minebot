package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Utils.HotbarUtils;
import com.nic0im.minebot.Utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import com.nic0im.minebot.Bots.TreeBot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static com.nic0im.minebot.Utils.RotationUtils.lookFloor;
import static com.nic0im.minebot.blockFinder.BlockFinder.detectNearbyBlocks;

public class BotActions {

    private static int searchTicks = 0;
    private static int breakTicks = 0;
    private static int replantingTicks = 0;
    private static int growingTicks = 0;
    private static BlockPos currentTarget;

    public static void handleIdle(){
        System.out.println("Bot Iddle");
    }


    public static void handleSearching(){
        searchTicks++;

        System.out.println("Searching blocks...");
        if (searchTicks >= 1) {
            searchTicks = 0;
            currentTarget = detectNearbyBlocks();
            if(currentTarget != null){
                TreeBot.currentState = BotState.LOOKING;
            }else {
                Minecraft mc = Minecraft.getInstance();
                if (mc.hitResult instanceof BlockHitResult blockHit) {
                    currentTarget = blockHit.getBlockPos();
                }
                TreeBot.currentState = BotState.REPLANTING;
            }

        }

    }

    public static void handleChangingTool(){

    }

    public static void handleLooking() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null ||
                mc.level == null ||
                currentTarget == null)
            return;

        RotationUtils.lookAt(currentTarget);

        BlockState state = mc.level.getBlockState(currentTarget);

        if (state.is(Blocks.OAK_LOG)) {
            TreeBot.currentState = BotState.BREAKING;
        }

        if (state.is(Blocks.OAK_SAPLING)) {
            TreeBot.currentState = BotState.GROWING;
        }

        System.out.println("Looking current target at "+ currentTarget);
    }

    public static void handleBreaking() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null ||
                mc.level == null ||
                currentTarget == null)
            return;

        breakTicks++;

        System.out.println("Mining...");
        HotbarUtils.selectItem(Items.DIAMOND_AXE);

        // Wait
        if (breakTicks >= 3) {

            breakTicks = 0;

            // Hold attack
            mc.options.keyAttack.setDown(true);

            // Check if block is gone
            if (mc.level.getBlockState(currentTarget).isAir()) {

                // Release attack
                mc.options.keyAttack.setDown(false);

                System.out.println("Block broken!");

                currentTarget = null;

                TreeBot.currentState = BotState.SEARCHING;
            }
        }
    }

    public static void handleReplanting() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        replantingTicks++;

        HotbarUtils.selectItem(Items.OAK_SAPLING);
        lookFloor();

        if (replantingTicks == 4) {
            mc.options.keyUse.setDown(true);
        }

        if (replantingTicks >= 8) {

            mc.options.keyUse.setDown(false);

            replantingTicks = 0;

            TreeBot.currentState = BotState.GROWING;
        }
    }

    public static void handleGrowing() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        growingTicks++;

        HotbarUtils.selectItem(Items.BONE_MEAL);

        if (growingTicks == 1) {
            mc.options.keyUse.setDown(true);
        }

        if (growingTicks >= 4) {

            mc.options.keyUse.setDown(false);

            growingTicks = 0;

            TreeBot.currentState = BotState.SEARCHING;
        }
    }


}
