package com.nic0im.minebot.blockFinder;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class BlockFinder {

    public static BlockPos detectNearbyBlocks() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.level == null)
            return null;

        BlockPos playerPos = mc.player.blockPosition();

        int radius = 5;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos checkPos = playerPos.offset(x, y, z);

                    Block block = mc.level.getBlockState(checkPos).getBlock();

                    // PRIORITY: saplings
                    if (block == Blocks.OAK_SAPLING) {
                        return checkPos;
                    }

                    // Only search logs if no saplings were found yet
                    else if (block == Blocks.OAK_LOG) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }
}