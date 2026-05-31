package com.nic0im.minebot.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class PositionsUtils {

    public static boolean isPlayerCloseTo(BlockPos targetPos) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return false;

        BlockPos playerPos = mc.player.blockPosition();

        return playerPos.closerThan(targetPos, 5.0);
    }
}
