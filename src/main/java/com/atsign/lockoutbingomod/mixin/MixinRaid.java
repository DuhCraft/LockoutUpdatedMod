package com.atsign.lockoutbingomod.mixin;

import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Raid.class})
public class MixinRaid {
    public MixinRaid() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getNumGroups(Lnet/minecraft/world/Difficulty;)I"},
            cancellable = true
    )
    protected void onGetNumGroups(Difficulty p_37725_, CallbackInfoReturnable<Integer> info) {
        info.cancel();
        switch (p_37725_) {
            case EASY:
            case NORMAL:
                info.setReturnValue(5);
                return;
            case HARD:
                info.setReturnValue(7);
                return;
            default:
                info.setReturnValue(0);
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"getPotentialBonusSpawns(Lnet/minecraft/world/entity/raid/Raid$RaiderType;Lnet/minecraft/util/RandomSource;ILnet/minecraft/world/DifficultyInstance;Z)I"},
            cancellable = true
    )
    protected void onGetPotentialBonusSpawns(Raid.RaiderType p_219829_, RandomSource p_219830_, int p_219831_, DifficultyInstance p_219832_, boolean p_219833_, CallbackInfoReturnable<Integer> info) {
        info.cancel();
        Difficulty difficulty = Difficulty.NORMAL;
        boolean flag = difficulty == Difficulty.EASY;
        boolean flag1 = difficulty == Difficulty.NORMAL;
        int i;
        switch (p_219829_) {
            case WITCH:
                if (flag || p_219831_ <= 2 || p_219831_ == 4) {
                    info.setReturnValue(0);
                    return;
                }

                i = 1;
                break;
            case PILLAGER:
            case VINDICATOR:
                if (flag) {
                    i = p_219830_.m_188503_(2);
                } else if (flag1) {
                    i = 1;
                } else {
                    i = 2;
                }
                break;
            case RAVAGER:
                i = !flag && p_219833_ ? 1 : 0;
                break;
            default:
                info.setReturnValue(0);
                return;
        }

        info.setReturnValue(i > 0 ? p_219830_.m_188503_(i + 1) : 0);
    }
}
