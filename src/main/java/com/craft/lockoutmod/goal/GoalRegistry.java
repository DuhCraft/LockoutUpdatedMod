package com.craft.lockoutmod.goal;

import com.craft.lockoutmod.goal.goals.CraftItemGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class GoalRegistry {

    private static final List<LockoutGoal> ALL_GOALS = new ArrayList<>();

    private GoalRegistry(){
        // no instances
    }

    /**
     * Called during common setup to register all possible goals.
     */
    public static void init(){
        // ---- ITEM GOALS ----
        register(new CraftItemGoal("craft_crafting_table", Items.CRAFTING_TABLE));
        register(new CraftItemGoal("craft_furnace", Items.FURNACE));
        register(new CraftItemGoal("craft_anvil", Items.ANVIL));
        register(new CraftItemGoal("craft_bread", Items.BREAD));

    }

    private static void register(LockoutGoal goal){
        ALL_GOALS.add(goal);
    }

    /**
     * Returns an immutable list of all goals
     */
    public static List<LockoutGoal> getAllGoals(){
        return Collections.unmodifiableList(ALL_GOALS);
    }
}
