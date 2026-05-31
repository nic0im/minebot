package com.nic0im.minebot.handlers;

import com.nic0im.minebot.Enums.BotState;
import com.nic0im.minebot.Bots.RailBot;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

import static com.nic0im.minebot.Utils.PositionsUtils.isPlayerCloseTo;
import static com.nic0im.minebot.Utils.RotationUtils.lookYawPitch;

public class RailBotActions {

    private static final BlockPos halfWayTarget = new BlockPos(-5326, 3, -980);
    private static final BlockPos startingPoint = new BlockPos(-5201, 3, -983);

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

            boolean halfWayCheck = isPlayerCloseTo(halfWayTarget);
            boolean startingPointCheck = isPlayerCloseTo(startingPoint);

            System.out.println(halfWayCheck+" "+startingPointCheck);

            if (
                    !isPlayerCloseTo(halfWayTarget) &&
                            !isPlayerCloseTo(startingPoint)
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
