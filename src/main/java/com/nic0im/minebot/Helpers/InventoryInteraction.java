package com.nic0im.minebot.Helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.world.inventory.ClickType;

public class InventoryInteraction {

    public static void CloseInventoryMenu() {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null) {
            return;
        }

        mc.player.closeContainer();
    }

    public static void LeftClickSlot(int slot) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.gameMode == null) {
            return;
        }

        mc.gameMode.handleInventoryMouseClick(
                mc.player.containerMenu.containerId,
                slot,
                0, // left click
                ClickType.PICKUP,
                mc.player
        );
    }

    public static void RightClickSlot(int slot) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.gameMode == null) {
            return;
        }

        mc.gameMode.handleInventoryMouseClick(
                mc.player.containerMenu.containerId,
                slot,
                1, // right click
                ClickType.PICKUP,
                mc.player
        );
    }

    public static void MoveOneItem(int fromSlot, int toSlot) {

        // Pick up stack
        LeftClickSlot(fromSlot);

        // Place one item
        RightClickSlot(toSlot);

        // Return remaining stack
        LeftClickSlot(fromSlot);
    }

    public static void ThrowItem(int slot) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null || mc.gameMode == null) {
            return;
        }

        mc.gameMode.handleInventoryMouseClick(
                mc.player.containerMenu.containerId,
                slot,
                0,
                ClickType.THROW,
                mc.player
        );
    }
}
