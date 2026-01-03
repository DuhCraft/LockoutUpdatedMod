package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.LinkedHeathServerEvents;
import com.atsign.lockoutbingomod.interfaces.FoodDataExtra;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({FoodData.class})
public abstract class MixinFoodData implements FoodDataExtra {
    private String playerName = "";
    @Shadow
    private int f_38699_;

    public MixinFoodData() {
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    private int get_previous_food_level() {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            return LinkedHeathServerEvents.previous_food_level_team1;
        } else {
            return LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName) ? LinkedHeathServerEvents.previous_food_level_team2 : -10;
        }
    }

    private void set_previous_food_level(int value) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            LinkedHeathServerEvents.previous_food_level_team1 = value;
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName)) {
            LinkedHeathServerEvents.previous_food_level_team2 = value;
        }

    }

    private int get_food_level() {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            return LinkedHeathServerEvents.food_level_team1;
        } else {
            return LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName) ? LinkedHeathServerEvents.food_level_team2 : -10;
        }
    }

    private void set_food_level(int value) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            LinkedHeathServerEvents.food_level_team1 = value;
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName)) {
            LinkedHeathServerEvents.food_level_team2 = value;
        }

    }

    private float get_exhaustion_level() {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            return LinkedHeathServerEvents.exhaustion_level_team1;
        } else {
            return LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName) ? LinkedHeathServerEvents.exhaustion_level_team2 : -10.0F;
        }
    }

    private void set_exhaustion_level(float value) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            LinkedHeathServerEvents.exhaustion_level_team1 = value;
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName)) {
            LinkedHeathServerEvents.exhaustion_level_team2 = value;
        }

    }

    private float get_saturation_level() {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            return LinkedHeathServerEvents.saturation_level_team1;
        } else {
            return LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName) ? LinkedHeathServerEvents.saturation_level_team2 : -10.0F;
        }
    }

    private void set_saturation_level(float value) {
        if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
            LinkedHeathServerEvents.saturation_level_team1 = value;
        } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName)) {
            LinkedHeathServerEvents.saturation_level_team2 = value;
        }

    }

    @Inject(
            at = {@At("HEAD")},
            method = {"eat(IF)V"},
            cancellable = true
    )
    protected void onEat(int p_38708_, float p_38709_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(this.playerName)) {
                LinkedHeathServerEvents.food_level_team1 = Math.min(p_38708_ + LinkedHeathServerEvents.food_level_team1, 20);
                LinkedHeathServerEvents.saturation_level_team1 = Math.min(LinkedHeathServerEvents.saturation_level_team1 + (float)p_38708_ * p_38709_ * 2.0F, (float)LinkedHeathServerEvents.food_level_team1);
                info.cancel();
            } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(this.playerName)) {
                LinkedHeathServerEvents.food_level_team2 = Math.min(p_38708_ + LinkedHeathServerEvents.food_level_team2, 20);
                LinkedHeathServerEvents.saturation_level_team2 = Math.min(LinkedHeathServerEvents.saturation_level_team2 + (float)p_38708_ * p_38709_ * 2.0F, (float)LinkedHeathServerEvents.food_level_team2);
                info.cancel();
            }

        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"tick(Lnet/minecraft/world/entity/player/Player;)V"},
            cancellable = true
    )
    protected void onFoodDataTick(Player p_38711_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            Difficulty difficulty = p_38711_.f_19853_.m_46791_();
            this.set_previous_food_level(this.get_food_level());
            if (this.get_exhaustion_level() > 4.0F) {
                this.set_exhaustion_level(this.get_exhaustion_level() - 4.0F);
                if (this.get_saturation_level() > 0.0F) {
                    this.set_saturation_level(Math.max(this.get_saturation_level() - 1.0F, 0.0F));
                } else if (difficulty != Difficulty.PEACEFUL) {
                    this.set_food_level(Math.max(this.get_food_level() - 1, 0));
                }
            }

            String current_player = p_38711_.m_5446_().getString();
            if (LinkedHeathServerEvents.is_team1_linked && LockoutBingoSetup.team1.contains(current_player)) {
                if (!((String)LockoutBingoSetup.team1.get(0)).equals(current_player)) {
                    return;
                }
            } else if (LinkedHeathServerEvents.is_team2_linked && LockoutBingoSetup.team2.contains(current_player) && !((String)LockoutBingoSetup.team2.get(0)).equals(current_player)) {
                return;
            }

            boolean flag = p_38711_.f_19853_.m_46469_().m_46207_(GameRules.f_46139_);
            if (flag && this.get_saturation_level() > 0.0F && p_38711_.m_36325_() && this.get_food_level() >= 20) {
                ++this.f_38699_;
                if (this.f_38699_ >= 10) {
                    float f = Math.min(this.get_saturation_level(), 6.0F);
                    p_38711_.m_5634_(f / 6.0F);
                    this.m_38703_(f);
                    this.f_38699_ = 0;
                }
            } else if (flag && this.get_food_level() >= 18 && p_38711_.m_36325_()) {
                ++this.f_38699_;
                if (this.f_38699_ >= 80) {
                    p_38711_.m_5634_(1.0F);
                    this.m_38703_(6.0F);
                    this.f_38699_ = 0;
                }
            } else if (this.get_food_level() <= 0) {
                ++this.f_38699_;
                if (this.f_38699_ >= 80) {
                    if (p_38711_.m_21223_() > 10.0F || difficulty == Difficulty.HARD || p_38711_.m_21223_() > 1.0F && difficulty == Difficulty.NORMAL) {
                        p_38711_.m_6469_(DamageSource.f_19313_, 1.0F);
                    }

                    this.f_38699_ = 0;
                }
            } else {
                this.f_38699_ = 0;
            }

        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"addExhaustion(F)V"},
            cancellable = true
    )
    protected void onAddExhaustion(float p_38704_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            this.set_exhaustion_level(Math.min(this.get_exhaustion_level() + p_38704_, 40.0F));
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setExhaustion(F)V"},
            cancellable = true
    )
    protected void onSetExhaustion(float p_150379_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            this.set_exhaustion_level(p_150379_);
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setSaturation(F)V"},
            cancellable = true
    )
    protected void onSetSaturation(float p_38718_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            this.set_saturation_level(p_38718_);
        }
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"setFoodLevel(I)V"},
            cancellable = true
    )
    protected void onSetFoodLevel(int p_38706_, CallbackInfo info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            this.set_food_level(p_38706_);
        }
    }

    @Shadow
    public abstract void m_38703_(float var1);

    @Inject(
            at = {@At("RETURN")},
            method = {"getFoodLevel()I"},
            cancellable = true
    )
    protected void onGetFoodLevel(CallbackInfoReturnable<Integer> info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            info.setReturnValue(this.get_food_level());
        }
    }

    @Inject(
            at = {@At("RETURN")},
            method = {"getLastFoodLevel()I"},
            cancellable = true
    )
    protected void onGetLastFoodLevel(CallbackInfoReturnable<Integer> info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            info.setReturnValue(this.get_previous_food_level());
        }
    }

    @Inject(
            at = {@At("RETURN")},
            method = {"needsFood()Z"},
            cancellable = true
    )
    protected void onNeedsFood(CallbackInfoReturnable<Boolean> info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            info.setReturnValue(this.get_food_level() < 20);
        }
    }

    @Inject(
            at = {@At("RETURN")},
            method = {"getExhaustionLevel()F"},
            cancellable = true
    )
    protected void onGetExhaustionLevel(CallbackInfoReturnable<Float> info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            info.setReturnValue(this.get_exhaustion_level());
        }
    }

    @Inject(
            at = {@At("RETURN")},
            method = {"getSaturationLevel()F"},
            cancellable = true
    )
    protected void onGetSaturationLevel(CallbackInfoReturnable<Float> info) {
        if (!this.playerName.equals("")) {
            info.cancel();
            info.setReturnValue(this.get_saturation_level());
        }
    }
}
