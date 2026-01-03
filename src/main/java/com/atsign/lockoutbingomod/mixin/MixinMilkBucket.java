package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({MilkBucketItem.class})
public class MixinMilkBucket {
    public MixinMilkBucket() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"finishUsingItem(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/item/ItemStack;"}
    )
    protected void onFinishUsingItem(ItemStack p_42923_, Level p_42924_, LivingEntity p_42925_, CallbackInfoReturnable<ItemStack> info) {
        if (p_42925_ instanceof ServerPlayer serverplayer) {
            if (LockoutBingoSetup.checkRemoveStatusEffect && serverplayer.m_21220_().size() > 0 && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.removeStatusEffectIndex, serverplayer.m_5446_().getString())) {
                LockoutBingoSetup.checkRemoveStatusEffect = false;
            }
        }

    }
}
