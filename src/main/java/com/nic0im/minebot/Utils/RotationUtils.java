package com.nic0im.minebot.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class RotationUtils {

    public static void lookAt(BlockPos targetPos) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        Vec3 eyes = mc.player.getEyePosition();

        double targetX = targetPos.getX() + 0.5;
        double targetY = targetPos.getY() + 0.5;
        double targetZ = targetPos.getZ() + 0.5;

        double dx = targetX - eyes.x;
        double dy = targetY - eyes.y;
        double dz = targetZ - eyes.z;

        double horizontalDistance =
                Math.sqrt(dx * dx + dz * dz);

        float yaw = (float)
                (Math.toDegrees(Math.atan2(dz, dx)) - 90F);

        float pitch = (float)
                -Math.toDegrees(
                        Math.atan2(dy, horizontalDistance)
                );

        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);

        mc.player.setYHeadRot(yaw);
        mc.player.yBodyRot = yaw;
    }

    public static void lookYawPitch(int yaw, int pitch){
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);

        mc.player.setYHeadRot(yaw);
        mc.player.yBodyRot = yaw;
    }

    public static void lookFloor(){
        Minecraft mc = Minecraft.getInstance();
        if(mc.player == null){
            return;
        }

        BlockPos playerPos = mc.player.blockPosition();

        // Block under player
        BlockPos groundPos = playerPos.below();

        // Block in front of player
        Direction facing = mc.player.getDirection();

        BlockPos plantPos = groundPos.relative(facing);

        RotationUtils.lookAt(plantPos);
    }
}