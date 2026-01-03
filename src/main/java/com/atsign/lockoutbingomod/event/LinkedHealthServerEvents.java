package com.atsign.lockoutbingomod.event;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.interfaces.FoodDataExtra;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "lockoutbingomod",
        bus = Bus.FORGE,
        value = {Dist.DEDICATED_SERVER}
)
public class LinkedHeathServerEvents {
    private static float shared_health_team1 = -1.0F;
    public static int food_level_team1 = -1;
    public static int previous_food_level_team1 = -1;
    public static float saturation_level_team1 = -1.0F;
    public static float exhaustion_level_team1 = -1.0F;
    public static float absortion_amount_team1 = 0.0F;
    private static float shared_health_team2 = -1.0F;
    public static int food_level_team2 = -1;
    public static int previous_food_level_team2 = -1;
    public static float saturation_level_team2 = -1.0F;
    public static float exhaustion_level_team2 = -1.0F;
    public static float absortion_amount_team2 = 0.0F;
    public static boolean is_team1_linked = false;
    public static boolean is_team2_linked = false;
    public static MinecraftServer server;

    public LinkedHeathServerEvents() {
    }

    public static void startLinkTeam1() {
        LockoutBingoMod.LOGGER.info("Enter Link Team1");
        ServerPlayer team1_captian = server.m_6846_().m_11255_((String)LockoutBingoSetup.team1.get(0));
        LockoutBingoMod.LOGGER.info("Got Team 1 captain");
        shared_health_team1 = team1_captian.m_21223_();
        food_level_team1 = team1_captian.m_36324_().m_38702_();
        saturation_level_team1 = team1_captian.m_36324_().m_38722_();
        exhaustion_level_team1 = team1_captian.m_36324_().m_150380_();
        previous_food_level_team1 = team1_captian.m_36324_().m_150377_();
        absortion_amount_team1 = team1_captian.m_6103_();
        LockoutBingoMod.LOGGER.info("Set Levels");

        for(String player_name : LockoutBingoSetup.team1) {
            ServerPlayer player = server.m_6846_().m_11255_(player_name);
            LockoutBingoMod.LOGGER.info("Try set player name " + player_name);
            ((FoodDataExtra)player.m_36324_()).setPlayerName(player_name);
            LockoutBingoMod.LOGGER.info("Set player name " + player_name);
        }

        is_team1_linked = true;
    }

    public static void startLinkTeam2() {
        ServerPlayer team2_captian = server.m_6846_().m_11255_((String)LockoutBingoSetup.team2.get(0));
        shared_health_team2 = team2_captian.m_21223_();
        food_level_team2 = team2_captian.m_36324_().m_38702_();
        saturation_level_team2 = team2_captian.m_36324_().m_38722_();
        exhaustion_level_team2 = team2_captian.m_36324_().m_150380_();
        previous_food_level_team2 = team2_captian.m_36324_().m_150377_();
        absortion_amount_team2 = team2_captian.m_6103_();

        for(String player_name : LockoutBingoSetup.team2) {
            ServerPlayer player = server.m_6846_().m_11255_(player_name);
            ((FoodDataExtra)player.m_36324_()).setPlayerName(player_name);
        }

        is_team2_linked = true;
    }

    @SubscribeEvent
    public static void sync(TickEvent.ServerTickEvent event) {
        if (is_team1_linked) {
            for(String player_name : LockoutBingoSetup.team1) {
                ServerPlayer player = server.m_6846_().m_11255_(player_name);

                try {
                    if (player.m_21224_()) {
                        player = null;
                    }
                } catch (Exception var8) {
                }

                if (player != null) {
                    try {
                        player.m_21153_(shared_health_team1);
                        if (player.m_21224_()) {
                            player.m_6667_(DamageSource.f_19318_);
                        }
                    } catch (Exception var7) {
                    }
                }
            }
        }

        if (is_team2_linked) {
            for(String player_name : LockoutBingoSetup.team2) {
                ServerPlayer player = server.m_6846_().m_11255_(player_name);

                try {
                    if (player.m_21224_()) {
                        player = null;
                    }
                } catch (Exception var6) {
                }

                if (player != null) {
                    try {
                        player.m_21153_(shared_health_team2);
                        if (player.m_21224_()) {
                            player.m_6667_(DamageSource.f_19318_);
                        }
                    } catch (Exception var5) {
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void damageTaken(LivingDamageEvent event) {
        if ((is_team1_linked || is_team2_linked) && event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            String name = player.m_5446_().getString();
            if (is_team1_linked && LockoutBingoSetup.team1.contains(name)) {
                shared_health_team1 -= event.getAmount();
            } else if (is_team2_linked && LockoutBingoSetup.team2.contains(name)) {
                shared_health_team2 -= event.getAmount();
            }
        }

    }

    @SubscribeEvent
    public static void heal(LivingHealEvent event) {
        if ((is_team1_linked || is_team2_linked) && event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            String name = player.m_5446_().getString();
            if (is_team1_linked && LockoutBingoSetup.team1.contains(name)) {
                shared_health_team1 += event.getAmount();
                event.setAmount(0.0F);
            } else if (is_team2_linked && LockoutBingoSetup.team2.contains(name)) {
                shared_health_team2 += event.getAmount();
                event.setAmount(0.0F);
            }
        }

    }

    @SubscribeEvent
    public static void respawn(PlayerEvent.PlayerRespawnEvent event) {
        if (is_team1_linked || is_team2_linked) {
            Player player = event.getEntity();
            String name = player.m_5446_().getString();
            if (!event.isEndConquered() && is_team1_linked && LockoutBingoSetup.team1.contains(name)) {
                ((FoodDataExtra)player.m_36324_()).setPlayerName(name);
                shared_health_team1 = 20.0F;
                food_level_team1 = 20;
                exhaustion_level_team1 = 0.0F;
                saturation_level_team1 = 5.0F;
            } else if (!event.isEndConquered() && is_team2_linked && LockoutBingoSetup.team2.contains(name)) {
                ((FoodDataExtra)player.m_36324_()).setPlayerName(name);
                shared_health_team2 = 20.0F;
                food_level_team2 = 20;
                exhaustion_level_team2 = 0.0F;
                saturation_level_team2 = 5.0F;
            }
        }

    }
}
