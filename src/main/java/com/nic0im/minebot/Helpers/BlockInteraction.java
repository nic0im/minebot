package com.nic0im.minebot.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.BrewingStandMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BlockInteraction {

    public static void openBlock(BlockPos targetBlockPos){

        Minecraft mc = Minecraft.getInstance();

        mc.gameMode.useItemOn(
                mc.player,
                InteractionHand.MAIN_HAND,
                new BlockHitResult(
                        Vec3.atCenterOf(targetBlockPos),
                        Direction.UP,
                        targetBlockPos,
                        false
                )
        );
    }

    public static void openLookedAtBlock() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.hitResult instanceof BlockHitResult blockHit) {
            openBlock(blockHit.getBlockPos());
        }
    }

    public static void QuickMoveItem(int slot) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.gameMode == null) {
            return;
        }

        mc.gameMode.handleInventoryMouseClick(
                mc.player.containerMenu.containerId,
                slot,
                0,
                ClickType.QUICK_MOVE,
                mc.player
        );
    }

    public static int getInventorySlotByItem(Item item) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            return -1;
        }

        for (int slot = 0; slot < mc.player.getInventory().items.size(); slot++) {
            ItemStack stack = mc.player.getInventory().items.get(slot);

            if (!stack.isEmpty() && stack.is(item)) {
                return slot;
            }
        }

        return -1;
    }

    public static int getContainerSlotByItem(Item item) {

        Minecraft mc = Minecraft.getInstance();

        for (int i = 0; i < mc.player.containerMenu.slots.size(); i++) {

            ItemStack stack =
                    mc.player.containerMenu.slots.get(i).getItem();

            if (!stack.isEmpty() && stack.is(item)) {
                return i;
            }
        }

        return -1;
    }

    public static boolean isBrewingStandOpen(){
        Minecraft mc = Minecraft.getInstance();

        if (mc.player.containerMenu instanceof BrewingStandMenu brewingMenu) {
            return true;
        }
        return false;
    }



}
