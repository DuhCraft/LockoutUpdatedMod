package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.command.impl.LockoutBingoCommands;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({PlayerList.class})
public class MixinPlayerList {
    @Shadow
    @Final
    private Map<UUID, PlayerAdvancements> f_11203_;
    @Shadow
    @Final
    private MinecraftServer f_11195_;
    @Shadow
    @Final
    private Map<UUID, ServerStatsCounter> f_11202_;

    public MixinPlayerList() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getPlayerAdvancements(Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/server/PlayerAdvancements;"},
            cancellable = true
    )
    protected void onGetPlayerAdvancements(ServerPlayer p_11297_, CallbackInfoReturnable<PlayerAdvancements> info) {
        if (LockoutBingoCommands.reset_player) {
            LockoutBingoMod.LOGGER.info("RESET ADVANCEMENTS");
            info.cancel();
            UUID uuid = p_11297_.m_20148_();
            PlayerAdvancements playeradvancements = null;
            File file1 = this.f_11195_.m_129843_(LevelResource.f_78174_).toFile();

            try {
                Files.deleteIfExists(Paths.get(file1.getPath(), uuid + ".json"));
            } catch (IOException var7) {
            }

            File file2 = new File(file1, uuid + ".json");
            playeradvancements = new PlayerAdvancements(this.f_11195_.m_129933_(), this.f_11195_.m_6846_(), this.f_11195_.m_129889_(), file2, p_11297_);
            this.f_11203_.put(uuid, playeradvancements);
            if (!(p_11297_ instanceof FakePlayer)) {
                playeradvancements.m_135979_(p_11297_);
            }

            info.setReturnValue(playeradvancements);
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getPlayerStats(Lnet/minecraft/world/entity/player/Player;)Lnet/minecraft/stats/ServerStatsCounter;"},
            cancellable = true
    )
    protected void ongetPlayerStats(Player p_11240_, CallbackInfoReturnable<ServerStatsCounter> info) {
        if (LockoutBingoCommands.reset_player) {
            LockoutBingoMod.LOGGER.info("RESET STATS");
            info.cancel();
            UUID uuid = p_11240_.m_20148_();
            ServerStatsCounter serverstatscounter = null;
            File file1 = this.f_11195_.m_129843_(LevelResource.f_78175_).toFile();

            try {
                Files.deleteIfExists(Paths.get(file1.getPath(), uuid + ".json"));
            } catch (IOException var7) {
            }

            File file2 = new File(file1, uuid + ".json");
            serverstatscounter = new ServerStatsCounter(this.f_11195_, file2);
            this.f_11202_.put(uuid, serverstatscounter);
            info.setReturnValue(serverstatscounter);
        }

    }
}
