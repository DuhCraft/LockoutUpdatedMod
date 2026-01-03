package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoServerInfo;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketSoundPlayed {
    private final double x;
    private final double y;
    private final double z;
    private final String song;

    public PacketSoundPlayed(FriendlyByteBuf buf) {
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
        this.song = buf.m_130136_(32767);
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
        buf.m_130070_(this.song);
    }

    public PacketSoundPlayed(double x, double y, double z, String song) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.song = song;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoServerInfo.handleMusicPlayedEvent(((NetworkEvent.Context)ctx.get()).getSender(), this.x, this.y, this.z, this.song));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
