package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EnderDragon.class})
public class mixinEnderDragon extends Mob {
    public mixinEnderDragon(EntityType<? extends Mob> p_21368_, Level p_21369_) {
        super(p_21368_, p_21369_);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"},
            cancellable = true
    )
    protected void onHurt(DamageSource p_31113_, float p_31114_, CallbackInfoReturnable<Boolean> info) {
        if (!this.f_19853_.m_5776_()) {
            LockoutBingoMod.LOGGER.info("Inside EnderDragon Hurt Method, setting hurt part = body");
        } else {
            LockoutBingoMod.LOGGER.info("Inside EnderDragon Hurt Method, setting hurt output = false");
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"hurt(Lnet/minecraft/world/entity/boss/EnderDragonPart;Lnet/minecraft/world/damagesource/DamageSource;F)Z"},
            cancellable = true
    )
    protected void onHurt(EnderDragonPart p_31121_, DamageSource p_31122_, float p_31123_, CallbackInfoReturnable<Boolean> info) {
        LockoutBingoMod.LOGGER.info("Inside EnderDragon Official Hurt Method, dealing damage to " + p_31121_.f_31011_);
    }
}
