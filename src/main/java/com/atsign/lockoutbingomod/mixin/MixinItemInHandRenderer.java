package com.atsign.lockoutbingomod.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin({ItemInHandRenderer.class})
public class MixinItemInHandRenderer {
    @Final
    @Shadow
    private Minecraft f_109299_;
    @Shadow
    private ItemStack f_109300_;
    @Shadow
    private ItemStack f_109301_;
    @Shadow
    private float f_109303_;
    @Shadow
    private float f_109302_;
    @Shadow
    private float f_109305_;
    @Shadow
    private float f_109304_;

    public MixinItemInHandRenderer() {
    }

    private boolean shouldCauseReequipAnimation(@NotNull ItemStack from, @NotNull ItemStack to, int slot) {
        if (from.m_41720_() instanceof CompassItem && to.m_41720_() instanceof CompassItem && CompassItem.m_40736_(from) && CompassItem.m_40736_(to)) {
            CompoundTag from_tag = from.m_41784_();
            CompoundTag to_tag = to.m_41784_();
            if (from_tag.m_128441_("PlayerTracking") && to_tag.m_128441_("PlayerTracking") && from_tag.m_128461_("PlayerTracking").equals(to_tag.m_128461_("PlayerTracking"))) {
                return false;
            }
        }

        return ForgeHooksClient.shouldCauseReequipAnimation(from, to, slot);
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"tick()V"},
            cancellable = true
    )
    protected void onTick(CallbackInfo info) {
        info.cancel();
        this.f_109303_ = this.f_109302_;
        this.f_109305_ = this.f_109304_;
        LocalPlayer localplayer = this.f_109299_.f_91074_;
        ItemStack itemstack = localplayer.m_21205_();
        ItemStack itemstack1 = localplayer.m_21206_();
        if (ItemStack.m_41728_(this.f_109300_, itemstack)) {
            this.f_109300_ = itemstack;
        }

        if (ItemStack.m_41728_(this.f_109301_, itemstack1)) {
            this.f_109301_ = itemstack1;
        }

        if (localplayer.m_108637_()) {
            this.f_109302_ = Mth.m_14036_(this.f_109302_ - 0.4F, 0.0F, 1.0F);
            this.f_109304_ = Mth.m_14036_(this.f_109304_ - 0.4F, 0.0F, 1.0F);
        } else {
            float f = localplayer.m_36403_(1.0F);
            boolean requipM = this.shouldCauseReequipAnimation(this.f_109300_, itemstack, localplayer.m_150109_().f_35977_);
            boolean requipO = this.shouldCauseReequipAnimation(this.f_109301_, itemstack1, -1);
            if (!requipM && this.f_109300_ != itemstack) {
                this.f_109300_ = itemstack;
            }

            if (!requipO && this.f_109301_ != itemstack1) {
                this.f_109301_ = itemstack1;
            }

            this.f_109302_ += Mth.m_14036_((!requipM ? f * f * f : 0.0F) - this.f_109302_, -0.4F, 0.4F);
            this.f_109304_ += Mth.m_14036_((float)(!requipO ? 1 : 0) - this.f_109304_, -0.4F, 0.4F);
        }

        if (this.f_109302_ < 0.1F) {
            this.f_109300_ = itemstack;
        }

        if (this.f_109304_ < 0.1F) {
            this.f_109301_ = itemstack1;
        }

    }
}
