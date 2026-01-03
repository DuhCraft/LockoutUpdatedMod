package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketLockoutTimerUpdate {
    private final int timer;

    public PacketLockoutTimerUpdate(FriendlyByteBuf buf) {
        this.timer = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.timer);
    }

    public PacketLockoutTimerUpdate(int timer) {
        this.timer = timer;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoClientInfo.timerLeft = this.timer);
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
