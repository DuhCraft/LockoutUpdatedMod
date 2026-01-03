package com.atsign.lockoutbingomod;

import com.atsign.lockoutbingomod.LockoutBingoInterface.MainBingoScreen;
import com.atsign.lockoutbingomod.core.init.ContainerInit;
import com.atsign.lockoutbingomod.core.init.ItemInit;
import com.atsign.lockoutbingomod.network.Networking;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lockoutbingomod")
public class LockoutBingoMod {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String Mod_ID = "lockoutbingomod";

    public LockoutBingoMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        ItemInit.ITEMS.register(bus);
        ContainerInit.CONTAINERS.register(bus);
        ContainerInit.TILES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        Networking.registerMessages();
        bus.addListener(this::doClientStuff);
    }

    private void doClientStuff(FMLClientSetupEvent event) {
        MenuScreens.m_96206_((MenuType)ContainerInit.MAINBINGO_CONTAINER.get(), MainBingoScreen::new);
    }

    private void setup(FMLCommonSetupEvent event) {
    }
}
