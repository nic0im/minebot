package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Bots.AutoFarmBot;
import com.nic0im.minebot.Bots.TreeBot;
import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Utils.HotbarUtils;
import com.nic0im.minebot.Utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import static com.nic0im.minebot.Utils.RotationUtils.lookFloor;
import static com.nic0im.minebot.blockFinder.BlockFinder.detectNearbyBlocks;
import static com.nic0im.minebot.blockFinder.BlockFinder.searchTargetBlock;

public class FarmBotActions {

    private static int searchTicks = 0;
    private static int breakTicks = 0;
    private static int replantingTicks = 0;
    private static int growingTicks = 0;
    private static BlockPos currentTarget;


    public static void handleSearching(){
        searchTicks++;


        if (searchTicks >= 2) {

            searchTicks = 0;
            System.out.println("handle searching");
            var potatoBlock = searchTargetBlock(Blocks.POTATOES);

            if(potatoBlock!=null){
                currentTarget = potatoBlock;
                AutoFarmBot.currentState = BotState.LOOKING;
            }

        }

    }

    public static void handleLooking() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null ||
                mc.level == null ||
                currentTarget == null)
            return;

        RotationUtils.lookAt(currentTarget);

        BlockState state = mc.level.getBlockState(currentTarget);

        if (state.is(Blocks.POTATOES)) {
            int age = state.getValue(CropBlock.AGE);
            if(age==7){
                AutoFarmBot.currentState = BotState.BREAKING;
            }else if (age<7){
                AutoFarmBot.currentState = BotState.GROWING;
            }
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
        // Wait
        if (breakTicks >= 2) {

            breakTicks = 0;

            // Hold attack
            mc.options.keyAttack.setDown(true);

            // Check if block is gone
            if (mc.level.getBlockState(currentTarget).isAir()) {

                // Release attack
                mc.options.keyAttack.setDown(false);

                System.out.println("Block broken!");

                currentTarget = null;

                AutoFarmBot.currentState = BotState.REPLANTING;
            }
        }
    }

    public static void handleReplanting() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        replantingTicks++;

        HotbarUtils.selectItem(Items.POTATO);
        //lookFloor();

        if (replantingTicks == 2) {
            mc.options.keyUse.setDown(true);
        }

        if (replantingTicks >= 4) {

            mc.options.keyUse.setDown(false);

            replantingTicks = 0;

            AutoFarmBot.currentState = BotState.GROWING;
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

        if (growingTicks >= 2) {

            mc.options.keyUse.setDown(false);

            growingTicks = 0;

            AutoFarmBot.currentState = BotState.SEARCHING;
        }
    }
}
