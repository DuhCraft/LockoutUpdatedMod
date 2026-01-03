package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MinecartTNT.class})
public class MixinMinecartTNT {
    @Shadow
    private int f_38647_;

    public MixinMinecartTNT() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"destroy(Lnet/minecraft/world/damagesource/DamageSource;)V"}
    )
    protected void onDestroy(DamageSource p_38664_, CallbackInfo info) {
        if (LockoutBingoSetup.checkDetonateTNT && (p_38664_.m_19384_() || p_38664_.m_19372_()) && this.f_38647_ < 0 && p_38664_.m_7640_() instanceof Player) {
            Player player = (Player)p_38664_.m_7640_();
            if (ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.detonateTNTIndex, player.m_5446_().getString())) {
                LockoutBingoSetup.checkDetonateTNT = false;
            }
        }

    }
}