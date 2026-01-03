package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoServerInfo;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketTriggerFullBingo {
    boolean trigger;

    public PacketTriggerFullBingo(FriendlyByteBuf buf) {
        this.trigger = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(this.trigger);
    }

    public PacketTriggerFullBingo(boolean trigger) {
        this.trigger = trigger;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoServerInfo.triggerFullInventory(((NetworkEvent.Context)ctx.get()).getSender()));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
