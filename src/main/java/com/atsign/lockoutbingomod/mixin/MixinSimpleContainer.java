package com.atsign.lockoutbingomod.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({SimpleContainer.class})
public abstract class MixinSimpleContainer {
    @Shadow
    @Final
    private NonNullList<ItemStack> f_19147_;

    public MixinSimpleContainer() {
    }

    @Shadow
    public abstract void m_6596_();

    @Inject(
            at = {@At("HEAD")},
            method = {"setItem(ILnet/minecraft/world/item/ItemStack;)V"},
            cancellable = true
    )
    protected void onSetItem(int p_19162_, ItemStack p_19163_, CallbackInfo info) {
        if (p_19163_.m_41741_() > 64) {
            info.cancel();
            this.f_19147_.set(p_19162_, p_19163_);
            if (!p_19163_.m_41619_() && p_19163_.m_41613_() > p_19163_.m_41741_()) {
                p_19163_.m_41764_(p_19163_.m_41741_());
            }

            this.m_6596_();
        }

    }
}