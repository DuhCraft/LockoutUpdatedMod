package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketPlaySoundEffect {
    SoundsEffect soundsEffect;

    public PacketPlaySoundEffect(FriendlyByteBuf buf) {
        this.soundsEffect = PacketPlaySoundEffect.SoundsEffect.valueOf(buf.readInt());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.soundsEffect.getValue());
    }

    public PacketPlaySoundEffect(SoundsEffect soundsEffect) {
        this.soundsEffect = soundsEffect;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoClientInfo.playSoundEffect(this.soundsEffect));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }

    public static enum SoundsEffect {
        win(1),
        lose(2),
        team_complete(3),
        opp_complete(4),
        match_point(5);

        private int value;
        private static Map map = new HashMap();

        private SoundsEffect(int value) {
            this.value = value;
        }

        public static SoundsEffect valueOf(int pageType) {
            return (SoundsEffect)map.get(pageType);
        }

        public int getValue() {
            return this.value;
        }

        static {
            for(SoundsEffect soundsEffect : values()) {
                map.put(soundsEffect.value, soundsEffect);
            }

        }
    }
}