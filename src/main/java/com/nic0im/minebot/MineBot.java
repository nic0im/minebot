package com.nic0im.minebot;
import com.nic0im.minebot.Bots.RailBot;
import com.nic0im.minebot.Bots.TreeBot;
import com.nic0im.minebot.modKeyBinds.ModKeyBinds;
import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(MineBot.MODID)
public class MineBot {

    public static final String MODID = "minebot";

    private static final Logger LOGGER = LogUtils.getLogger();

    public MineBot(FMLJavaModLoadingContext context) {

        IEventBus modEventBus = context.getModEventBus();

        // Common setup
        modEventBus.addListener(this::commonSetup);

        // Register keybinds
        modEventBus.addListener(ModKeyBinds::register);

        // Config
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    // Client-only MOD bus events
    @Mod.EventBusSubscriber(
            modid = MODID,
            bus = Mod.EventBusSubscriber.Bus.MOD,
            value = Dist.CLIENT
    )
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }

    // Client-only FORGE bus events
    @Mod.EventBusSubscriber(
            modid = MODID,
            value = Dist.CLIENT
    )
    public static class ClientForgeEvents {

        private static int tickCounter = 0;

        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {

            if (event.phase != TickEvent.Phase.END)
                return;

            tickCounter++;

            if (tickCounter >= 20) {

                tickCounter = 0;

                LOGGER.info("Executed after 1 second");
            }

            // Toggle bot
            if (ModKeyBinds.TEST_KEY.consumeClick()) {
                TreeBot.toggle();
            }else if (ModKeyBinds.toogleRailBot.consumeClick()) {
                RailBot.toggle();
            }

            // Bot update
            TreeBot.tick();

            RailBot.tick();
        }
    }
}