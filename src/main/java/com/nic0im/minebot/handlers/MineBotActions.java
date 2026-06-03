package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Bots.AutoMineBot;
import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Utils.HotbarUtils;
import com.nic0im.minebot.Utils.RotationUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

import static com.nic0im.minebot.Utils.BlockScannerUtils.findBlock;
import static com.nic0im.minebot.Utils.RotationUtils.lookForward;

public class MineBotActions {

    private static int breakTicks = 0;
    private static BlockPos currentTarget;

    public static void handleWalking(){
        System.out.println("Searching blocks...");
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null)
            return;

        BlockPos center = mc.player.blockPosition();
                //.above();
        Direction forward = mc.player.getDirection();

        currentTarget = findBlock(mc.level, center, forward);

        if(currentTarget != null){
            mc.options.keyUp.setDown(false);
            AutoMineBot.currentState = BotState.LOOKING;
        }else {
            lookForward();
            mc.options.keyUp.setDown(true);
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

        AutoMineBot.currentState = BotState.BREAKING;
        /*if (state.is(Blocks.OAK_LOG)) {

        }*/

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
        //HotbarUtils.selectItem(Items.NETHERITE_PICKAXE);

        // Wait
        if (breakTicks >= 1) {

            breakTicks = 0;

            // Hold attack
            mc.options.keyAttack.setDown(true);

            // Check if block is gone
            if (mc.level.getBlockState(currentTarget).isAir()) {

                // Release attack
                mc.options.keyAttack.setDown(false);

                System.out.println("Block broken!");

                currentTarget = null;

                AutoMineBot.currentState = BotState.WALKING;
            }
        }
    }

}
