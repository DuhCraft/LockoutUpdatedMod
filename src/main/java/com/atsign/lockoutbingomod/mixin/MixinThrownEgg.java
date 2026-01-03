package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ThrownEgg.class})
public abstract class MixinThrownEgg extends ThrowableItemProjectile {
    public MixinThrownEgg(EntityType<? extends ThrownEgg> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public MixinThrownEgg(Level p_37481_, LivingEntity p_37482_) {
        super(EntityType.f_20483_, p_37482_, p_37481_);
    }

    public MixinThrownEgg(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(EntityType.f_20483_, p_37477_, p_37478_, p_37479_, p_37476_);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"onHit(Lnet/minecraft/world/phys/HitResult;)V"},
            cancellable = true
    )
    protected void onHit(HitResult p_37488_, CallbackInfo info) {
        if (LockoutBingoSetup.checkChickenEggSpawn) {
            info.cancel();
            super.m_6532_(p_37488_);
            if (!this.f_19853_.f_46443_) {
                if (this.f_19796_.m_188503_(8) == 0) {
                    int i = 1;
                    if (this.f_19796_.m_188503_(32) == 0) {
                        i = 4;
                    }

                    if (ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.chickenEggSpawnIndex, this.m_37282_().m_5446_().getString())) {
                        LockoutBingoSetup.checkChickenEggSpawn = false;
                    }

                    for(int j = 0; j < i; ++j) {
                        Chicken chicken = (Chicken)EntityType.f_20555_.m_20615_(this.f_19853_);
                        chicken.m_146762_(-24000);
                        chicken.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
                        this.f_19853_.m_7967_(chicken);
                    }
                }

                this.f_19853_.m_7605_(this, (byte)3);
                this.m_146870_();
            }
        }

    }
}
