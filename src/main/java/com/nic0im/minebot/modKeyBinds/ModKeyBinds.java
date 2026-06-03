package com.nic0im.minebot.modKeyBinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

public class ModKeyBinds {

    public static final KeyMapping TEST_KEY = new KeyMapping(
            "key.examplemod.test",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_1,
            "key.categories.examplemod"
    );

    public static final KeyMapping toogleRailBot = new KeyMapping(
            "key.examplemod.togglerailbot",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_2,
            "key.categories.examplemod"
    );

    public static final KeyMapping toogleAutoMineBot = new KeyMapping(
            "key.examplemod.toggleAutoMineBot",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_3,
            "key.categories.examplemod"
    );

    public static void register(RegisterKeyMappingsEvent event) {
        event.register(TEST_KEY);
        event.register(toogleRailBot);
        event.register(toogleAutoMineBot);
    }

}
