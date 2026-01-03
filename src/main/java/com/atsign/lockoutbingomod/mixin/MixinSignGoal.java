package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SignBlock.class})
public class MixinSignGoal {
    @Final
    @Shadow
    private WoodType f_56270_;

    public MixinSignGoal() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"use(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"},
            cancellable = true
    )
    protected void onUse(BlockState p_56278_, Level p_56279_, BlockPos p_56280_, Player p_56281_, InteractionHand p_56282_, BlockHitResult p_56283_, CallbackInfoReturnable<InteractionResult> info) {
        if (LockoutBingoSetup.checkGlowSign) {
            ItemStack itemstack = p_56281_.m_21120_(p_56282_);
            Item item = itemstack.m_41720_();
            boolean flag = item instanceof DyeItem;
            boolean flag1 = itemstack.m_150930_(Items.f_151056_);
            boolean flag2 = itemstack.m_150930_(Items.f_42532_);
            boolean flag3 = (flag1 || flag || flag2) && p_56281_.m_150110_().f_35938_;
            if (!p_56279_.f_46443_) {
                BlockEntity blockentity = p_56279_.m_7702_(p_56280_);
                if (blockentity instanceof SignBlockEntity) {
                    SignBlockEntity signblockentity = (SignBlockEntity)blockentity;
                    boolean flag4 = signblockentity.m_155727_();
                    if ((!flag1 || !flag4) && (!flag2 || flag4) && flag3 && flag1 && p_56281_ instanceof ServerPlayer && this.f_56270_ == WoodType.f_61836_ && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.glowSignIndex, p_56281_.m_5446_().getString())) {
                        LockoutBingoSetup.checkGlowSign = false;
                    }
                }
            }
        }

    }
}
