package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoServerInfo;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketPlayerJumped {
    public PacketPlayerJumped(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public PacketPlayerJumped() {
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoServerInfo.handlePlayerJumpedEvent(((NetworkEvent.Context)ctx.get()).getSender()));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
