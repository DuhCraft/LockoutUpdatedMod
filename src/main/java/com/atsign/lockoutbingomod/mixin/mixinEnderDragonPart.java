package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.boss.EnderDragonPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EnderDragonPart.class})
public class mixinEnderDragonPart {
    @Final
    @Shadow
    public String f_31011_;

    public mixinEnderDragonPart() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"},
            cancellable = true
    )
    protected void onHurt(DamageSource p_31020_, float p_31021_, CallbackInfoReturnable<Boolean> info) {
        LockoutBingoMod.LOGGER.info("Inside EnderDragonPart Hurt Method, body part hurt = " + this.f_31011_);
    }
}
