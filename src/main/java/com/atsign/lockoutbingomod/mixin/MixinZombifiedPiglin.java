package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ZombifiedPiglin.class})
public class MixinZombifiedPiglin {
    public MixinZombifiedPiglin() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setTarget(Lnet/minecraft/world/entity/LivingEntity;)V"}
    )
    protected void onSetTarget(@Nullable LivingEntity p_34478_, CallbackInfo info) {
        if (LockoutBingoSetup.checkEnrageZombiePiglin && p_34478_ != null && p_34478_ instanceof Player player) {
            if (ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.enrageZombiePiglinIndex, player.m_5446_().getString())) {
                LockoutBingoSetup.checkEnrageZombiePiglin = false;
            }
        }

    }
}
