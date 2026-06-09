package com.nic0im.minebot.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class HotbarUtils {

    public static void selectItem(Item targetItem) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        // Hotbar slots are 0-8
        for (int slot = 0; slot < 9; slot++) {

            ItemStack stack =
                    mc.player.getInventory().getItem(slot);

            if (stack.getItem() == targetItem) {

                mc.player.getInventory().selected = slot;

                return;
            }
        }

        System.out.println("Item not found in hotbar");
    }
}
