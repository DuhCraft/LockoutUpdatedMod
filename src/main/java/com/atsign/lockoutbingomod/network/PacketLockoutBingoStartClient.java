package com.atsign.lockoutbingomod.network;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import java.util.ArrayList;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class PacketLockoutBingoStartClient {
    private final boolean isTestMode;
    private final int numPlayersPerTeam;
    private final int numPlayersPerTeam1;
    private final int numPlayersPerTeam2;
    private final ArrayList<String> team1;
    private final ArrayList<String> team2;
    private final int numOfGoals;
    private final ArrayList<PacketLockoutBingoInfo.GoalTuple> goals;
    private final int timer;
    private final int team1Color;
    private final int team2Color;

    public PacketLockoutBingoStartClient(FriendlyByteBuf buf) {
        this.isTestMode = buf.readBoolean();
        this.timer = buf.readInt();
        this.team1Color = buf.readInt();
        this.team2Color = buf.readInt();
        this.numPlayersPerTeam1 = buf.readInt();
        this.numPlayersPerTeam2 = buf.readInt();
        this.numPlayersPerTeam = this.numPlayersPerTeam1;
        this.team1 = new ArrayList();
        this.team2 = new ArrayList();

        for(int i = 0; i < this.numPlayersPerTeam1; ++i) {
            this.team1.add(buf.m_130277_());
        }

        for(int i = 0; i < this.numPlayersPerTeam2; ++i) {
            this.team2.add(buf.m_130277_());
        }

        this.numOfGoals = buf.readInt();
        this.goals = new ArrayList();

        for(int i = 0; i < this.numOfGoals; ++i) {
            this.goals.add(new PacketLockoutBingoInfo.GoalTuple(buf.m_130277_(), buf.m_130277_(), buf.readInt()));
        }

    }

    public void toBytes(FriendlyByteBuf buf) {
        if (this.numOfGoals == this.goals.size() && this.team1.size() == this.numPlayersPerTeam1 && this.team2.size() == this.numPlayersPerTeam2) {
            buf.writeBoolean(this.isTestMode);
            buf.writeInt(this.timer);
            buf.writeInt(this.team1Color);
            buf.writeInt(this.team2Color);
            buf.writeInt(this.numPlayersPerTeam1);
            buf.writeInt(this.numPlayersPerTeam2);

            for(int i = 0; i < this.numPlayersPerTeam1; ++i) {
                buf.m_130070_((String)this.team1.get(i));
            }

            for(int i = 0; i < this.numPlayersPerTeam2; ++i) {
                buf.m_130070_((String)this.team2.get(i));
            }

            buf.writeInt(this.numOfGoals);

            for(int i = 0; i < this.numOfGoals; ++i) {
                buf.m_130070_(((PacketLockoutBingoInfo.GoalTuple)this.goals.get(i)).goal);
                buf.m_130070_(((PacketLockoutBingoInfo.GoalTuple)this.goals.get(i)).player_complete);
                buf.writeInt(((PacketLockoutBingoInfo.GoalTuple)this.goals.get(i)).goalIndex);
            }

        } else {
            throw new IllegalStateException("Numbers don't match");
        }
    }

    public PacketLockoutBingoStartClient(boolean isTestMode, int timer, int team1Color, int team2Color, int numPlayersPerTeam, ArrayList<String> team1, ArrayList<String> team2, int numOfGoals, ArrayList<PacketLockoutBingoInfo.GoalTuple> goals) {
        this.isTestMode = isTestMode;
        this.timer = timer;
        this.team1Color = team1Color;
        this.team2Color = team2Color;
        this.numPlayersPerTeam = numPlayersPerTeam;
        this.numPlayersPerTeam1 = team1.size();
        this.numPlayersPerTeam2 = team2.size();
        this.team1 = new ArrayList(team1);
        this.team2 = new ArrayList(team2);
        this.numOfGoals = numOfGoals;
        this.goals = new ArrayList(goals);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> LockoutBingoClientInfo.startClientBingo(this.isTestMode, this.timer, this.team1Color, this.team2Color, this.numPlayersPerTeam, this.team1, this.team2, this.numOfGoals, this.goals));
        ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
    }
}
