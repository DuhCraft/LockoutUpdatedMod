package com.atsign.lockoutbingomod.core;

import com.atsign.lockoutbingomod.event.ModServerEvents;
import java.util.List;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.NetworkHooks;

public class LockoutBingoServerInfo {
    public LockoutBingoServerInfo() {
    }

    public static void handleMusicPlayedEvent(ServerPlayer playerEntity, double x, double y, double z, String song) {
        if (LockoutBingoSetup.useSoundGoals.containsKey(song)) {
            List<Player> playersNearby = playerEntity.f_19853_.m_45976_(Player.class, new AABB(x - (double)3.0F, y - (double)3.0F, z - (double)3.0F, x + (double)3.0F, y + (double)3.0F, z + (double)3.0F));
            Player nearestPlayer = playerEntity;
            if (playersNearby.size() > 1) {
                double min_dist = Double.MAX_VALUE;

                for(Player playerEntity1 : playersNearby) {
                    double dist = Math.sqrt(Math.pow(playerEntity1.m_20185_() - x, (double)2.0F) + Math.pow(playerEntity1.m_20186_() - y, (double)2.0F) + Math.pow(playerEntity1.m_20189_() - z, (double)2.0F));
                    if (dist < min_dist) {
                        min_dist = dist;
                        nearestPlayer = playerEntity1;
                    }
                }
            } else if (playersNearby.size() == 1) {
                nearestPlayer = (Player)playersNearby.get(0);
            }

            if (ModServerEvents.triggerClientBoardUpdate((Integer)LockoutBingoSetup.useSoundGoals.get(song), nearestPlayer.m_5446_().getString())) {
                if (LockoutBingoSetup.discList.contains(song)) {
                    for(String disc : LockoutBingoSetup.discList) {
                        LockoutBingoSetup.useSoundGoals.remove(disc);
                    }
                } else {
                    LockoutBingoSetup.useSoundGoals.remove(song);
                }
            }
        }

    }

    public static void handlePlayerJumpedEvent(ServerPlayer playerEntity) {
        if (LockoutBingoSetup.checkOpponentJumps) {
            if (LockoutBingoSetup.team1.contains(playerEntity.m_5446_().getString())) {
                if (ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.opponentJumpsIndex, (String)LockoutBingoSetup.team2.get(0))) {
                    LockoutBingoSetup.checkOpponentJumps = false;
                }
            } else if (LockoutBingoSetup.team2.contains(playerEntity.m_5446_().getString()) && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.opponentJumpsIndex, (String)LockoutBingoSetup.team1.get(0))) {
                LockoutBingoSetup.checkOpponentJumps = false;
            }
        }

    }

    public static void triggerFullInventory(ServerPlayer playerEntity) {
        NetworkHooks.openScreen(playerEntity, LockoutBingoClientInfo.bingoInventory);
    }
}
