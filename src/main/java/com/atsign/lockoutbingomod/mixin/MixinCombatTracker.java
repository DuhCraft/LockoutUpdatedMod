package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({CombatTracker.class})
public class MixinCombatTracker {
    @Shadow
    @Final
    private LivingEntity f_19277_;

    public MixinCombatTracker() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getFallLocation(Lnet/minecraft/world/damagesource/CombatEntry;)Ljava/lang/String;"}
    )
    protected void onGetFallLocation(CombatEntry p_19288_, CallbackInfoReturnable<String> info) {
        if (LockoutBingoSetup.checkDieVines && this.f_19277_ instanceof Player && p_19288_.m_19266_() != null && (p_19288_.m_19266_().contains("vines") || p_19288_.m_19266_().equals("other_climbable")) && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.dieVinesIndex, this.f_19277_.m_5446_().getString())) {
            LockoutBingoSetup.checkDieVines = false;
        }

    }
}
