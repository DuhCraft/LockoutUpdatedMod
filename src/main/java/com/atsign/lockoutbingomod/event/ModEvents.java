package com.atsign.lockoutbingomod.event;

import com.atsign.lockoutbingomod.command.impl.LockoutBingoCommands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
        modid = "lockoutbingomod"
)
public class ModEvents {
    public ModEvents() {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        LockoutBingoCommands.register(event.getDispatcher());
    }
}
