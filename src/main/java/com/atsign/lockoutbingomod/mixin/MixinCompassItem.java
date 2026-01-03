package com.atsign.lockoutbingomod.mixin;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({CompassItem.class})
public class MixinCompassItem {
    public MixinCompassItem() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"inventoryTick(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/Entity;IZ)V"},
            cancellable = true
    )
    protected void onInventoryTick(ItemStack p_40720_, Level p_40721_, Entity p_40722_, int p_40723_, boolean p_40724_, CallbackInfo info) {
        info.cancel();
        if (!p_40721_.f_46443_ && CompassItem.m_40736_(p_40720_)) {
            CompoundTag compoundtag = p_40720_.m_41784_();
            if (compoundtag.m_128441_("LodestoneTracked") && !compoundtag.m_128471_("LodestoneTracked")) {
                return;
            }

            Optional<ResourceKey<Level>> optional = getLodestoneDimension(compoundtag);
            if (optional.isPresent() && optional.get() == p_40721_.m_46472_() && compoundtag.m_128441_("LodestonePos")) {
                BlockPos blockpos = NbtUtils.m_129239_(compoundtag.m_128469_("LodestonePos"));
                if (!p_40721_.m_46739_(blockpos)) {
                    compoundtag.m_128473_("LodestonePos");
                }
            }
        }

    }

    private static Optional<ResourceKey<Level>> getLodestoneDimension(CompoundTag p_40728_) {
        return Level.f_46427_.parse(NbtOps.f_128958_, p_40728_.m_128423_("LodestoneDimension")).result();
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"useOn(Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;"},
            cancellable = true
    )
    protected void onUseOn(UseOnContext p_40726_, CallbackInfoReturnable<InteractionResult> info) {
        info.cancel();
        Level level = p_40726_.m_43725_();
        Player player = p_40726_.m_43723_();
        BlockPos blockpos = p_40726_.m_8083_();
        level.m_5594_((Player)null, blockpos, SoundEvents.f_12107_, SoundSource.PLAYERS, 1.0F, 1.0F);
        info.setReturnValue(InteractionResult.m_19078_(level.f_46443_));
    }
}
