package com.atsign.lockoutbingomod.core;

import com.atsign.lockoutbingomod.network.PacketLockoutBingoInfo;
import java.io.Serializable;
import java.util.ArrayList;

public class LockoutBingoSaveData implements Serializable {
    public boolean isTestMode;
    public int numPlayersPerTeam;
    public ArrayList<String> team1;
    public ArrayList<String> team2;
    public int numOfGoals;
    public ArrayList<PacketLockoutBingoInfo.GoalTuple> goals;

    public LockoutBingoSaveData(boolean isTestMode, int numPlayersPerTeam, ArrayList<String> team1, ArrayList<String> team2, int numOfGoals, ArrayList<PacketLockoutBingoInfo.GoalTuple> goals) {
        this.isTestMode = isTestMode;
        this.numPlayersPerTeam = numPlayersPerTeam;
        this.team1 = new ArrayList(team1);
        this.team2 = new ArrayList(team2);
        this.numOfGoals = numOfGoals;
        this.goals = new ArrayList(goals);
    }
}