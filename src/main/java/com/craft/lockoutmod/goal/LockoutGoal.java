package com.craft.lockoutmod.goal;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public abstract class LockoutGoal {

    private final String id;
    private final GoalCategory category;
    private final Component displayName;
    private final ItemStack icon;

    private boolean completed = false;
    private UUID completedBy = null;

    protected LockoutGoal(String id, GoalCategory category, Component displayName, ItemStack icon){
        this.id = id;
        this.category = category;
        this.displayName = displayName;
        this.icon = icon;
    }

     // ---------- Getters ----------

    public String getId(){
        return id;
    }

    public GoalCategory getCategory(){
        return category;
    }

    public Component getDisplayName(){
        return displayName;
    }

    public ItemStack getIcon(){
        return icon;
    }

    // --------- Completion ----------

    public void complete(UUID playerId){
        if(completed) return;
        this.completed = true;
        this.completedBy = playerId;
    }

    // --------- Matching Logic ------------

    /**
     * Called by event listeners to see if this goal should trigger.
     * Each concrete goal decides what event it cares about.
     */
    public abstract boolean matches(Object event);
}
