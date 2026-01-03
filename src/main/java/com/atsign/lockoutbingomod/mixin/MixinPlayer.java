package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.LinkedHeathServerEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Player.class})
public abstract class MixinPlayer {
    @Shadow
    @Final
    private static EntityDataAccessor<Float> f_36107_;

    public MixinPlayer() {
    }

    @Shadow
    public abstract Component m_5446_();

    @Inject(
            at = {@At("HEAD")},
            method = {"drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity;"},
            cancellable = true
    )
    protected void onDrop(ItemStack p_36179_, boolean p_36180_, boolean p_36181_, CallbackInfoReturnable<ItemEntity> info) {
        if (p_36179_.m_41720_() instanceof CompassItem) {
            info.cancel();
            info.setReturnValue((Object)null);
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setAbsorptionAmount(F)V"},
            cancellable = true
    )
    protected void onSetAbsortionAmount(float p_36396_, CallbackInfo info) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.m_5446_().getString())) {
            info.cancel();
            if (p_36396_ < 0.0F) {
                p_36396_ = 0.0F;
            }

            LinkedHeathServerEvents.absortion_amount_team1 = p_36396_;

            for(String player_name : LockoutBingoSetup.team1) {
                ServerPlayer player = LinkedHeathServerEvents.server.m_6846_().m_11255_(player_name);

                try {
                    if (player != null) {
                        player.m_20088_().m_135381_(f_36107_, LinkedHeathServerEvents.absortion_amount_team1);
                    }
                } catch (Exception var8) {
                }
            }
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.m_5446_().getString())) {
            info.cancel();
            if (p_36396_ < 0.0F) {
                p_36396_ = 0.0F;
            }

            LinkedHeathServerEvents.absortion_amount_team2 = p_36396_;

            for(String player_name : LockoutBingoSetup.team2) {
                ServerPlayer player = LinkedHeathServerEvents.server.m_6846_().m_11255_(player_name);

                try {
                    if (player != null) {
                        player.m_20088_().m_135381_(f_36107_, LinkedHeathServerEvents.absortion_amount_team2);
                    }
                } catch (Exception var7) {
                }
            }
        }

    }

    @Inject(
            at = {@At("RETURN")},
            method = {"getAbsorptionAmount()F"},
            cancellable = true
    )
    protected void onGetAbsorptionAmount(CallbackInfoReturnable<Float> info) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.m_5446_().getString())) {
            info.cancel();
            info.setReturnValue(LinkedHeathServerEvents.absortion_amount_team1);
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.m_5446_().getString())) {
            info.cancel();
            info.setReturnValue(LinkedHeathServerEvents.absortion_amount_team2);
        }

    }
}
