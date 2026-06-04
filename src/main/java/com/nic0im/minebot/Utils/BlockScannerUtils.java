package com.nic0im.minebot.Utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlockScannerUtils {

    public static BlockPos findBlock(
            Level level,
            BlockPos center,
            Direction forward) {

        Direction right = forward.getClockWise();

        for (int distance = 1; distance <= 4; distance++) {
            for (int y = 0; y <= 2; y++) {
                for (int width = -1; width <= 1; width++) {

                    BlockPos pos = center
                            .relative(forward, distance)
                            .relative(right, width)
                            .above(y);

                    if (!level.getBlockState(pos).isAir()) {
                        return pos;
                    }
                }
            }
        }

        return null;
    }


}
