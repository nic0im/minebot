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

            // Check if slot contains target item
            if (stack.getItem() == targetItem) {

                // Select hotbar slot
                mc.player.getInventory().selected = slot;

                System.out.println(
                        "Selected item in slot: " + slot
                );

                return;
            }
        }

        System.out.println("Item not found in hotbar");
    }
}
