package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ArmorStand.class})
public abstract class MixinArmorStand {
    public MixinArmorStand() {
    }

    @Shadow
    public abstract ItemStack m_6844_(EquipmentSlot var1);

    @Inject(
            at = {@At("RETURN")},
            method = {"interactAt(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/InteractionResult;"}
    )
    protected void onInteract(Player p_31594_, Vec3 p_31595_, InteractionHand p_31596_, CallbackInfoReturnable<InteractionResult> info) {
        if (LockoutBingoSetup.checkArmorStand) {
            InteractionResult flag = (InteractionResult)info.getReturnValue();
            if (flag == InteractionResult.SUCCESS && this.m_6844_(EquipmentSlot.FEET) != ItemStack.f_41583_ && this.m_6844_(EquipmentSlot.LEGS) != ItemStack.f_41583_ && this.m_6844_(EquipmentSlot.CHEST) != ItemStack.f_41583_ && this.m_6844_(EquipmentSlot.HEAD) != ItemStack.f_41583_ && p_31594_ instanceof ServerPlayer && ModServerEvents.triggerClientBoardUpdate(LockoutBingoSetup.armorStandIndex, p_31594_.m_5446_().getString())) {
                LockoutBingoSetup.checkArmorStand = false;
            }
        }

    }
}
