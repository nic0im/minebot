package com.nic0im.minebot.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class PositionsUtils {

    public static boolean isPlayerCloseTo(Vec3 target, double margin) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return false;

        return mc.player.position().distanceTo(target) <= margin;
    }

    public static BlockPos getPlayerBlockPosition(){
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return null;

        return mc.player.blockPosition();
    }

    public static BlockPos getFloorFrontBlock(){

        Minecraft mc = Minecraft.getInstance();

        if(mc.player == null){
            return null;
        }

        BlockPos playerPos = mc.player.blockPosition();

        BlockPos groundPos = playerPos.below();

        Direction facing = mc.player.getDirection();

        return groundPos.relative(facing);
    }

}
