package com.atsign.lockoutbingomod.mixin;

import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Zombie.class})
public abstract class MixinZombieKillVillager {
    Random random = new Random();

    public MixinZombieKillVillager() {
    }

    @Inject(
            at = {@At("RETURN")},
            method = {"wasKilled(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/LivingEntity;)Z"},
            cancellable = true
    )
    protected void onWasKilled(ServerLevel p_219160_, LivingEntity p_219161_, CallbackInfoReturnable<Boolean> cir) {
        boolean flag = (Boolean)cir.getReturnValue();
        cir.cancel();
        Zombie z = (Zombie)this;
        if (p_219160_.m_46791_() == Difficulty.EASY && p_219161_ instanceof Villager villager && ForgeEventFactory.canLivingConvert(p_219161_, EntityType.f_20530_, (timer) -> {
        })) {
            ZombieVillager zombievillager = (ZombieVillager)villager.m_21406_(EntityType.f_20530_, false);
            zombievillager.m_6518_(p_219160_, p_219160_.m_6436_(zombievillager.m_20183_()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag)null);
            zombievillager.m_34375_(villager.m_7141_());
            zombievillager.m_34391_((Tag)villager.m_35517_().m_26179_(NbtOps.f_128958_).getValue());
            zombievillager.m_34411_(villager.m_6616_().m_45388_());
            zombievillager.m_34373_(villager.m_7809_());
            ForgeEventFactory.onLivingConvert(p_219161_, zombievillager);
            flag = false;
        }

        cir.setReturnValue(flag);
    }
}
