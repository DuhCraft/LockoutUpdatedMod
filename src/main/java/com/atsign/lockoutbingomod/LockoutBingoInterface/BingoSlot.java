package com.atsign.lockoutbingomod.LockoutBingoInterface;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;

public class BingoSlot extends Slot {
    public BingoSlot(Container inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    public boolean m_8010_(Player playerIn) {
        return false;
    }
}
