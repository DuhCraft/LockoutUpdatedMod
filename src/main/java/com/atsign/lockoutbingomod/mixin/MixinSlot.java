package com.atsign.lockoutbingomod.mixin;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Slot.class})
public abstract class MixinSlot {
    @Shadow
    @Final
    public Container f_40218_;
    @Shadow
    @Final
    private int f_40217_;

    public MixinSlot() {
    }

    @Shadow
    public abstract int m_6641_();

    @Inject(
            at = {@At("HEAD")},
            method = {"getMaxStackSize()I"},
            cancellable = true
    )
    protected void onGetMaxStackSize(CallbackInfoReturnable<Integer> info) {
        info.cancel();
        if (this.f_40218_.m_8020_(this.f_40217_).m_41741_() > 64) {
            info.setReturnValue(this.f_40218_.m_8020_(this.f_40217_).m_41741_());
        } else {
            info.setReturnValue(this.f_40218_.m_6893_());
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getMaxStackSize(Lnet/minecraft/world/item/ItemStack;)I"},
            cancellable = true
    )
    protected void onGetMaxStackSize(ItemStack p_40238_, CallbackInfoReturnable<Integer> info) {
        info.cancel();
        if (p_40238_.m_41741_() > 64) {
            info.setReturnValue(p_40238_.m_41741_());
        } else {
            info.setReturnValue(this.m_6641_());
        }

    }
}
