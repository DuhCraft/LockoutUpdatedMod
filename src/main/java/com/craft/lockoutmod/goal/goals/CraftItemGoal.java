package com.craft.lockoutmod.goal.goals;

import com.craft.lockoutmod.goal.GoalCategory;
import com.craft.lockoutmod.goal.LockoutGoal;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class CraftItemGoal extends LockoutGoal {

    private final Item targetItem;

    public CraftItemGoal(String id, Item targetItem){
        super(id,
                GoalCategory.ITEMS,
                Component.literal("Craft ").append(new ItemStack(targetItem).getHoverName()),
                new ItemStack(targetItem)
        );
        this.targetItem = targetItem;

    }

    @Override
    public boolean matches(Object event) {
        if(!(event instanceof PlayerEvent.ItemCraftedEvent crafted)){
            return false;
        }

        return crafted.getCrafting().is(targetItem);
    }
}
