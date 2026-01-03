package com.atsign.lockoutbingomod.LockoutBingoInterface;

import com.atsign.lockoutbingomod.core.init.ContainerInit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MainBingoTileEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private final NonNullList<ItemStack> bingoBoardInventory;

    public MainBingoTileEntity(BlockPos p_155088_, BlockState p_155089_, int num_goals) {
        super((BlockEntityType)ContainerInit.MAIN_BINGO.get(), p_155088_, p_155089_);
        this.bingoBoardInventory = NonNullList.m_122780_(num_goals, ItemStack.f_41583_);
    }

    public MainBingoTileEntity(BlockPos p_155088_, BlockState p_155089_) {
        super((BlockEntityType)ContainerInit.MAIN_BINGO.get(), p_155088_, p_155089_);
        this.bingoBoardInventory = NonNullList.m_122780_(25, ItemStack.f_41583_);
    }

    public int @NotNull [] m_7071_(Direction p_19238_) {
        return new int[0];
    }

    public boolean m_7155_(int p_19235_, ItemStack p_19236_, @Nullable Direction p_19237_) {
        return false;
    }

    public boolean m_7157_(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return false;
    }

    protected @NotNull Component m_6820_() {
        return Component.m_237115_("screen.lockoutbingomod.mainbingo");
    }

    protected AbstractContainerMenu m_6555_(int p_58627_, Inventory p_58628_) {
        return new ContainerMainBingoBoard(p_58627_, p_58628_, this);
    }

    public int m_6643_() {
        return this.bingoBoardInventory.size();
    }

    public boolean m_7983_() {
        for(ItemStack itemstack : this.bingoBoardInventory) {
            if (!itemstack.m_41619_()) {
                return false;
            }
        }

        return false;
    }

    public @NotNull ItemStack m_8020_(int p_18941_) {
        return (ItemStack)this.bingoBoardInventory.get(p_18941_);
    }

    public @NotNull ItemStack m_7407_(int p_18942_, int p_18943_) {
        return !((ItemStack)this.bingoBoardInventory.get(p_18942_)).m_41619_() ? ContainerHelper.m_18969_(this.bingoBoardInventory, p_18942_, p_18943_) : ItemStack.f_41583_;
    }

    public @NotNull ItemStack m_8016_(int p_18951_) {
        ItemStack itemstack = (ItemStack)this.bingoBoardInventory.get(p_18951_);
        this.bingoBoardInventory.set(p_18951_, ItemStack.f_41583_);
        return itemstack;
    }

    public void m_6836_(int p_18944_, @NotNull ItemStack p_18945_) {
        if (p_18944_ < this.bingoBoardInventory.size()) {
            this.bingoBoardInventory.set(p_18944_, p_18945_);
        }
    }

    public boolean m_6542_(@NotNull Player p_18946_) {
        return false;
    }

    public void m_6211_() {
        this.bingoBoardInventory.clear();
    }
}
