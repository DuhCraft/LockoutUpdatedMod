package com.craft.lockoutmod.core;

import com.craft.lockoutmod.goal.LockoutGoal;

import java.util.*;

public class LockoutBoard {

    public static final int SIZE = 5;

    private final long seed;
    private final List<LockoutGoal> goals;

    public LockoutBoard(long seed, List<LockoutGoal> availableGoals){
        this.seed = seed;
        this.goals = generate(seed, availableGoals);
    }

    // --------- Board generation ---------

    private List<LockoutGoal> generate(long seed, List<LockoutGoal> pool){
        if(pool.size() < SIZE * SIZE){
            throw new IllegalArgumentException("Not enough goals to generate a Lockout board");
        }

        List<LockoutGoal> shuffled = new ArrayList<>(pool);
        Collections.shuffle(shuffled, new Random(seed));

        return shuffled.subList(0, SIZE * SIZE);
    }

    // -------- Access -----------

    public LockoutGoal getGoal(int x, int y){
        if(x < 0 || x >= SIZE || y < 0 || y >= SIZE){
            throw new IndexOutOfBoundsException("Invalid board position");
        }
        return goals.get(y * SIZE + x);
    }

    public List<LockoutGoal> getGoals(){
        return Collections.unmodifiableList(goals);
    }

    public long getSeed(){
        return seed;
    }
}
