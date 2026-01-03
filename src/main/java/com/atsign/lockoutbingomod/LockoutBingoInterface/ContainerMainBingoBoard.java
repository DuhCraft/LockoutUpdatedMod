package com.atsign.lockoutbingomod.LockoutBingoInterface;

import com.atsign.lockoutbingomod.core.init.ContainerInit;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class ContainerMainBingoBoard extends AbstractContainerMenu {
    public ContainerMainBingoBoard(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(25));
    }

    public ContainerMainBingoBoard(int id, Inventory player, Container container) {
        super((MenuType)ContainerInit.MAINBINGO_CONTAINER.get(), id);
        int index = 0;

        for(int y = 0; y < 5; ++y) {
            for(int x = 0; x < 5; ++x) {
                this.m_38897_(new BingoSlot(container, index, 8 + x * 18, 18 + y * 18));
                ++index;
            }
        }

    }

    public ItemStack m_7648_(Player p_38941_, int p_38942_) {
        return ItemStack.f_41583_;
    }

    public boolean m_6875_(Player p_38874_) {
        return true;
    }
}
