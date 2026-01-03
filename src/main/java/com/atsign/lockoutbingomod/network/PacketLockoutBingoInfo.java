package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketLockoutBingoInfo {
    private final int numOfGoals;
    private final ArrayList<GoalTuple> goals;

    public PacketLockoutBingoInfo(FriendlyByteBuf buf) {
        this.numOfGoals = buf.readInt();
        this.goals = new ArrayList();

        for(int i = 0; i < this.numOfGoals; ++i) {
            this.goals.add(new GoalTuple(buf.m_130277_(), buf.m_130277_(), buf.readInt()));
        }

    }

    public void toBytes(FriendlyByteBuf buf) {
        if (this.numOfGoals != this.goals.size()) {
            throw new IllegalStateException("Goal Numbers don't match");
        } else {
            buf.writeInt(this.numOfGoals);

            for(int i = 0; i < this.numOfGoals; ++i) {
                buf.m_130070_(((GoalTuple)this.goals.get(i)).goal);
                buf.m_130070_(((GoalTuple)this.goals.get(i)).player_complete);
                buf.writeInt(((GoalTuple)this.goals.get(i)).goalIndex);
            }

        }
    }

    public PacketLockoutBingoInfo(int numOfGoals, ArrayList<GoalTuple> goals) {
        this.numOfGoals = numOfGoals;
        this.goals = new ArrayList(goals);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoClientInfo.updateClientBingoInfo(this.numOfGoals, this.goals));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }

    public static class GoalTuple implements Serializable {
        public String goal;
        public String player_complete;
        public int goalIndex;

        public GoalTuple(String goal, String player_complete, int goalIndex) {
            this.goal = goal;
            this.player_complete = player_complete;
            this.goalIndex = goalIndex;
        }
    }
}
