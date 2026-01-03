package com.atsign.lockoutbingomod.event;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "lockoutbingomod",
        value = {Dist.CLIENT},
        bus = Bus.MOD
)
public class ModClientSetupEvents {
    public static final IGuiOverlay HUD_BINGO = (gui, poseStack, partialTick, width, height) -> {
        if (LockoutBingoClientInfo.bingoGUI != null && !LockoutBingoClientInfo.bingoGUI.mc.f_91066_.f_92062_) {
            gui.setupOverlayRenderState(false, false);
            if (!LockoutBingoClientInfo.isTestMode && LockoutBingoClientInfo.bingoGUI != null) {
                ModClientEvents.started = true;
                LockoutBingoClientInfo.bingoGUI.renderIngameGui(poseStack, partialTick);
            }
        }

    };

    public ModClientSetupEvents() {
    }

    @SubscribeEvent
    public static void registerBingoOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("bingo", HUD_BINGO);
    }
}