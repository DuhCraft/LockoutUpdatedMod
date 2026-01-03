package com.atsign.lockoutbingomod.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({DamageSource.class})
public class MixinDamageSource {
    public MixinDamageSource() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"explosion(Lnet/minecraft/world/level/Explosion;)Lnet/minecraft/world/damagesource/DamageSource;"},
            cancellable = true
    )
    private static void onExplosion(Explosion p_38664_, CallbackInfoReturnable<DamageSource> info) {
        if (p_38664_.getExploder() instanceof MinecartTNT) {
            info.cancel();
            info.setReturnValue((new EntityDamageSource("explosion", p_38664_.getExploder())).m_19386_().m_19375_());
        }

    }
}
