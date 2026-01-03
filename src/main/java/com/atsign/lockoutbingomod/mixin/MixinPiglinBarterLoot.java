package com.atsign.lockoutbingomod.mixin;

import java.util.function.BiConsumer;
import net.minecraft.data.loot.PiglinBarterLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({PiglinBarterLoot.class})
public class MixinPiglinBarterLoot {
    public MixinPiglinBarterLoot() {
    }

    @Inject(
            at = {@At("HEAD")},
            method = {"accept(Ljava/util/function/BiConsumer;)V"},
            cancellable = true
    )
    protected void onEat(BiConsumer<ResourceLocation, LootTable.Builder> p_124468_, CallbackInfo info) {
        info.cancel();
        p_124468_.accept(BuiltInLootTables.f_78738_, LootTable.m_79147_().m_79161_(LootPool.m_79043_().m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42517_).m_79707_(5).m_79078_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42471_).m_79707_(8).m_79078_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42589_).m_79707_(10).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_))).m_79076_(LootItem.m_79579_(Items.f_42736_).m_79707_(10).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_))).m_79076_(LootItem.m_79579_(Items.f_42749_).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(9.0F, 36.0F)))).m_79076_(LootItem.m_79579_(Items.f_42584_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 8.0F)))).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 24.0F)))).m_79076_(LootItem.m_79579_(Items.f_42692_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_41999_).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42754_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42613_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42454_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42049_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42691_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42525_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 12.0F)))).m_79076_(LootItem.m_79579_(Items.f_41832_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42542_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 6.0F))))));
    }
}
