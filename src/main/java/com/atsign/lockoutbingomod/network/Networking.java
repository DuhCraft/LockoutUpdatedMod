package com.atsign.lockoutbingomod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking {
    private static final String PROTOCOL_VERSION = "1";
    public static SimpleChannel INSTANCE;
    private static int ID = 0;

    public Networking() {
    }

    public static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation("lockoutbingomod", "lockoutbingomod"), () -> "1", "1"::equals, "1"::equals);
        INSTANCE.registerMessage(nextID(), PacketLockoutBingoInfo.class, PacketLockoutBingoInfo::toBytes, PacketLockoutBingoInfo::new, PacketLockoutBingoInfo::handle);
        INSTANCE.registerMessage(nextID(), PacketSoundPlayed.class, PacketSoundPlayed::toBytes, PacketSoundPlayed::new, PacketSoundPlayed::handle);
        INSTANCE.registerMessage(nextID(), PacketLockoutBingoStartClient.class, PacketLockoutBingoStartClient::toBytes, PacketLockoutBingoStartClient::new, PacketLockoutBingoStartClient::handle);
        INSTANCE.registerMessage(nextID(), PacketTriggerFullBingo.class, PacketTriggerFullBingo::toBytes, PacketTriggerFullBingo::new, PacketTriggerFullBingo::handle);
        INSTANCE.registerMessage(nextID(), PacketBingoLocation.class, PacketBingoLocation::toBytes, PacketBingoLocation::new, PacketBingoLocation::handle);
        INSTANCE.registerMessage(nextID(), PacketPlaySoundEffect.class, PacketPlaySoundEffect::toBytes, PacketPlaySoundEffect::new, PacketPlaySoundEffect::handle);
        INSTANCE.registerMessage(nextID(), PacketPlayerJumped.class, PacketPlayerJumped::toBytes, PacketPlayerJumped::new, PacketPlayerJumped::handle);
        INSTANCE.registerMessage(nextID(), PacketCompassRotate.class, PacketCompassRotate::toBytes, PacketCompassRotate::new, PacketCompassRotate::handle);
        INSTANCE.registerMessage(nextID(), PacketLockoutTimerUpdate.class, PacketLockoutTimerUpdate::toBytes, PacketLockoutTimerUpdate::new, PacketLockoutTimerUpdate::handle);
    }
}
