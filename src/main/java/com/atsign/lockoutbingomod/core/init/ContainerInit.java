package com.atsign.lockoutbingomod.core.init;

import com.atsign.lockoutbingomod.LockoutBingoInterface.ContainerMainBingoBoard;
import com.atsign.lockoutbingomod.LockoutBingoInterface.MainBingoTileEntity;
import com.mojang.datafixers.types.Type;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {
    public static final DeferredRegister<MenuType<?>> CONTAINERS;
    public static final DeferredRegister<BlockEntityType<?>> TILES;
    public static final RegistryObject<MenuType<ContainerMainBingoBoard>> MAINBINGO_CONTAINER;
    public static final RegistryObject<BlockEntityType<MainBingoTileEntity>> MAIN_BINGO;

    public ContainerInit() {
    }

    static {
        CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "lockoutbingomod");
        TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "lockoutbingomod");
        MAINBINGO_CONTAINER = CONTAINERS.register("mainbingoboard", () -> IForgeMenuType.create((windowId, inv, data) -> new ContainerMainBingoBoard(windowId, inv)));
        MAIN_BINGO = TILES.register("mainbingo", () -> Builder.m_155273_(MainBingoTileEntity::new, new Block[0]).m_58966_((Type)null));
    }
}
