package com.atsign.lockoutbingomod.mixin;

import com.atsign.lockoutbingomod.network.Networking;
import com.atsign.lockoutbingomod.network.PacketCompassRotate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.world.item.CompassItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({MouseHandler.class})
public class MixinMouseHandler {
    @Shadow
    @Final
    private Minecraft f_91503_;
    @Shadow
    private int f_91509_;
    @Shadow
    private int f_91510_;
    @Shadow
    private int f_91512_;
    private int twice = 0;

    public MixinMouseHandler() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"onPress(JIII)V"}
    )
    private void onOnPress(long p_91531_, int p_91532_, int p_91533_, int p_91534_, CallbackInfo info) {
        int mouseButton = p_91532_;
        if (p_91531_ == this.f_91503_.m_91268_().m_85439_() && this.twice == 0) {
            Options gamesettings = this.f_91503_.f_91066_;
            boolean flag = p_91533_ == 1;
            if (Minecraft.f_91002_ && p_91532_ == 0) {
                if (flag) {
                    if ((p_91534_ & 2) == 2) {
                        mouseButton = 1;
                    }
                } else if (this.f_91509_ > 0) {
                    mouseButton = 1;
                }
            }

            if (flag) {
                if ((Boolean)this.f_91503_.f_91066_.m_231828_().m_231551_() && this.f_91512_ > 0) {
                    return;
                }
            } else if (this.f_91510_ != -1 && (Boolean)this.f_91503_.f_91066_.m_231828_().m_231551_() && this.f_91512_ - 1 > 0) {
                return;
            }

            ++this.twice;
            if (mouseButton == gamesettings.f_92095_.getKey().m_84873_() && this.f_91503_.f_91074_ != null && this.f_91503_.f_91074_.m_21205_().m_41720_() instanceof CompassItem) {
                Networking.INSTANCE.sendToServer(new PacketCompassRotate());
            }
        } else {
            this.twice = 0;
        }

    }
}
