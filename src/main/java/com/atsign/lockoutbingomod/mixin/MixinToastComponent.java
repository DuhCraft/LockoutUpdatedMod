package com.atsign.lockoutbingomod.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ToastComponent.class})
public class MixinToastComponent {
    public MixinToastComponent() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"render(Lcom/mojang/blaze3d/vertex/PoseStack;)V"},
            cancellable = true
    )
    protected void onRender(PoseStack p_94921_, CallbackInfo info) {
        info.cancel();
    }
}
