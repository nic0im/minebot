package com.nic0im.minebot.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

public class Walker {

    private static Vec3 targetPos;
    private static boolean walking;

    public static void walkToFrontBlock() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        BlockPos playerPos = mc.player.blockPosition();
        Direction facing = mc.player.getDirection();

        BlockPos frontBlock = playerPos.relative(facing);

        targetPos = new Vec3(
                frontBlock.getX() + 0.5,
                mc.player.getY(),
                frontBlock.getZ() + 0.5
        );

        walking = true;
    }

    public static boolean handleWalking() {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || !walking)
            return false;

        Vec3 playerPos = mc.player.position();

        double dx = targetPos.x - playerPos.x;
        double dz = targetPos.z - playerPos.z;

        double distance = Math.sqrt(dx * dx + dz * dz);

        // Arrived
        if (distance <= 0.10D) {

            mc.player.setPos(
                    targetPos.x,
                    playerPos.y,
                    targetPos.z
            );

            mc.player.setDeltaMovement(
                    0,
                    mc.player.getDeltaMovement().y,
                    0
            );

            walking = false;
            targetPos = null;

            return true;
        }

        Vec3 direction = new Vec3(dx, 0, dz).normalize();

        double speed = Math.min(0.10D, distance);

        mc.player.setDeltaMovement(
                direction.x * speed,
                mc.player.getDeltaMovement().y,
                direction.z * speed
        );

        return false;

    }

    public static void cleanWalker(){
        walking = false;
        targetPos = null;
    }
    public static boolean isWalking() {
        return walking;
    }
}