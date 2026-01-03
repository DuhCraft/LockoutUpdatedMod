package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketCompassRotate {
    public PacketCompassRotate(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public PacketCompassRotate() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoSetup.rotateTrackingTable(((NetworkEvent.Context)ctx.get()).getSender().m_5446_().getString()));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
