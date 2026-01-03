package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketBingoLocation {
    int location;

    public PacketBingoLocation(FriendlyByteBuf buf) {
        this.location = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.location);
    }

    public PacketBingoLocation(int location) {
        this.location = location;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoClientInfo.location_id = this.location);
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
