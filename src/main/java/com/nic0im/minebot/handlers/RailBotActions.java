package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Bots.RailBot;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

import static com.nic0im.minebot.Utils.PositionsUtils.isPlayerCloseTo;
import static com.nic0im.minebot.Utils.RotationUtils.lookYawPitch;

public class RailBotActions {

    private static final Vec3 halfWayTarget =
            new Vec3(-5326.5, 3.0, -979.5);

    private static final Vec3 startingPoint =
            new Vec3(-5200.5, 3.0, -982.5);

    private static int placingTicks = 0;
    private static int round = 1;
    private static boolean halfWay = false;

    private static boolean reachedTarget = false;

    public static void handleLooking(){

        if (round%2==0 && !halfWay){
            lookYawPitch(40,0);
        }else if (round%2==0 && halfWay) {
            lookYawPitch(-146,0);
        }else if (round%2==1 && !halfWay) {
            lookYawPitch(146,0);
        }else if (round%2==1 && halfWay) {
            lookYawPitch(-40,0);
        }

        RailBot.currentState = BotState.PLACING;
    }

    public static void handlePlacing(){
        Minecraft mc = Minecraft.getInstance();

        if (mc.player == null)
            return;

        placingTicks++;

        //HotbarUtils.selectItem(Items.RAIL);

        if (placingTicks == 4) {
            mc.options.keyUse.setDown(true);
        }

        if (placingTicks >= 20) {

            placingTicks = 0;

            boolean halfWayCheck = isPlayerCloseTo(halfWayTarget, 5.0D);
            boolean startingPointCheck = isPlayerCloseTo(startingPoint, 5.0D);

            System.out.println(halfWayCheck+" "+startingPointCheck);

            if (
                    !isPlayerCloseTo(halfWayTarget, 5.0D) &&
                            !isPlayerCloseTo(startingPoint, 5.0D)
            ) {
                reachedTarget = false;
            }

            if (!reachedTarget) {

                if (halfWayCheck) {

                    reachedTarget = true;

                    halfWay = true;

                    mc.options.keyUse.setDown(false);

                    RailBot.currentState = BotState.LOOKING;
                }
                else if (startingPointCheck) {

                    reachedTarget = true;

                    halfWay = false;

                    mc.options.keyUse.setDown(false);

                    round++;

                    RailBot.currentState = BotState.LOOKING;
                }
            }
        }
    }

}
