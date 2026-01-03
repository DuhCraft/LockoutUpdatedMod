package com.atsign.lockoutbingomod.event;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import com.atsign.lockoutbingomod.core.init.Keybinds;
import com.atsign.lockoutbingomod.network.Networking;
import com.atsign.lockoutbingomod.network.PacketPlayerJumped;
import com.atsign.lockoutbingomod.network.PacketSoundPlayed;
import com.atsign.lockoutbingomod.network.PacketTriggerFullBingo;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
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
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderNameTagEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
        modid = "lockoutbingomod",
        bus = Bus.FORGE,
        value = {Dist.CLIENT}
)
@OnlyIn(Dist.CLIENT)
public class ModClientEvents {
    static Hashtable<String, String> soundList = null;
    private static int tick = 0;
    public static boolean started = false;

    public ModClientEvents() {
    }

    public static void setUpSoundList() {
        soundList = new Hashtable();
        soundList.put(SoundEvents.f_12471_.m_11660_().m_135815_(), "BLOCK_SMITHING_TABLE_USE");
        soundList.put(SoundEvents.f_11763_.m_11660_().m_135815_(), "BLOCK_COMPOSTER_EMPTY");
        soundList.put(SoundEvents.f_12085_.m_11660_().m_135815_(), "MUSIC_DISC_13");
        soundList.put(SoundEvents.f_12140_.m_11660_().m_135815_(), "MUSIC_DISC_CAT");
        soundList.put(SoundEvents.f_12086_.m_11660_().m_135815_(), "MUSIC_DISC_BLOCKS");
        soundList.put(SoundEvents.f_12141_.m_11660_().m_135815_(), "MUSIC_DISC_CHIRP");
        soundList.put(SoundEvents.f_12142_.m_11660_().m_135815_(), "MUSIC_DISC_FAR");
        soundList.put(SoundEvents.f_12143_.m_11660_().m_135815_(), "MUSIC_DISC_MALL");
        soundList.put(SoundEvents.f_12144_.m_11660_().m_135815_(), "MUSIC_DISC_MELLOHI");
        soundList.put(SoundEvents.f_12146_.m_11660_().m_135815_(), "MUSIC_DISC_STAL");
        soundList.put(SoundEvents.f_12147_.m_11660_().m_135815_(), "MUSIC_DISC_STRAD");
        soundList.put(SoundEvents.f_12149_.m_11660_().m_135815_(), "MUSIC_DISC_WARD");
        soundList.put(SoundEvents.f_12084_.m_11660_().m_135815_(), "MUSIC_DISC_11");
        soundList.put(SoundEvents.f_12148_.m_11660_().m_135815_(), "MUSIC_DISC_WAIT");
        soundList.put(SoundEvents.f_184218_.m_11660_().m_135815_(), "MUSIC_DISC_OTHERSIDE");
        soundList.put(SoundEvents.f_12145_.m_11660_().m_135815_(), "MUSIC_DISC_PIGSTEP");
        soundList.put(SoundEvents.f_11671_.m_11660_().m_135815_(), "BLOCK_ANVIL_USE");
        soundList.put(SoundEvents.f_12492_.m_11660_().m_135815_(), "UI_LOOM_TAKE_RESULT");
    }

    @SubscribeEvent
    public static void getSoundPlayed(PlaySoundEvent event) {
        if (soundList == null) {
            setUpSoundList();
        }

        if (soundList.containsKey(event.getName())) {
            double x = event.getSound().m_7772_();
            double y = event.getSound().m_7780_();
            double z = event.getSound().m_7778_();
            Networking.INSTANCE.sendToServer(new PacketSoundPlayed(x, y, z, (String)soundList.get(event.getName())));
        }

    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(Keybinds.bingo);
    }

    @SubscribeEvent
    public static void openFullBingoBoard(InputEvent.Key event) {
        Options gamesettings = Minecraft.m_91087_().f_91066_;
        if (Keybinds.bingo.m_90859_() && !LockoutBingoClientInfo.isTestMode) {
            Networking.INSTANCE.sendToServer(new PacketTriggerFullBingo(true));
        }

        if (Minecraft.m_91087_().f_91074_ != null && Minecraft.m_91087_().f_91074_.m_20096_() && !Minecraft.m_91087_().f_91074_.m_6069_() && !Minecraft.m_91087_().f_91074_.m_5842_() && LockoutBingoClientInfo.checkJump && gamesettings.f_92089_.m_90859_()) {
            Networking.INSTANCE.sendToServer(new PacketPlayerJumped());
        }

    }

    @SubscribeEvent
    public static void SetNameColor(RenderNameTagEvent event) {
        if (event.getEntity() instanceof Player && LockoutBingoClientInfo.team1 != null && LockoutBingoClientInfo.team2 != null) {
            if (LockoutBingoClientInfo.team1.contains(event.getEntity().m_5446_().getString()) && LockoutBingoClientInfo.team1ColorValue != -1) {
                MutableComponent component = event.getContent().m_6881_();
                component.m_130948_(event.getContent().m_7383_().m_178520_(LockoutBingoClientInfo.team1ColorValue));
                event.setContent(component);
            } else if (LockoutBingoClientInfo.team2.contains(event.getEntity().m_5446_().getString()) && LockoutBingoClientInfo.team2ColorValue != -1) {
                MutableComponent component = event.getContent().m_6881_();
                component.m_130948_(event.getContent().m_7383_().m_178520_(LockoutBingoClientInfo.team2ColorValue));
                event.setContent(component);
            }
        }

    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (started) {
            if (tick != 60) {
                ++tick;
                return;
            }

            tick = 0;
            LockoutBingoClientInfo.cycleBingoInventory();
        }

    }

    @SubscribeEvent
    public static void clientPre(CustomizeGuiOverlayEvent.DebugText event) {
        List<String> listL = event.getLeft();
        List<String> listR = event.getRight();
        PoseStack mStack = event.getPoseStack();
        Minecraft minecraft = Minecraft.m_91087_();
        Font font = minecraft.f_91062_;
        int width = minecraft.m_91268_().m_85445_();
        int height = minecraft.m_91268_().m_85446_();
        List<String> cleanListL = new ArrayList();

        for(String entry : listL) {
            if (!entry.contains("NoiseRouter")) {
                if (entry.contains("C:") && entry.contains("D:")) {
                    cleanListL.add(entry.split("D:")[0]);
                } else if (entry.contains("E:") && entry.contains("B:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("XYZ:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("Block:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("Chunk:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("Facing:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("Client Light:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("Biome:")) {
                    cleanListL.add(entry);
                } else if (entry.contains("M:") && entry.contains("C:")) {
                    String[] var10001 = entry.split("M:")[1].split("A:");
                    cleanListL.add("M:" + var10001[0]);
                }
            }
        }

        boolean isBee_Nest = false;
        List<String> cleanListR = new ArrayList();

        for(String entry : listR) {
            if (entry.contains("Targeted Block:")) {
                cleanListR.add(entry);
            } else if (entry.contains("minecraft:") && entry.contains("bee_nest")) {
                isBee_Nest = true;
                cleanListR.add(entry);
            } else if (entry.contains("honey_level:")) {
                cleanListR.add(entry);
            }
        }

        listL.clear();
        listR.clear();
        int top = LockoutBingoClientInfo.location_id == 1 ? 120 : 0;
        int i = 0;

        while(true) {
            Objects.requireNonNull(font);
            if (i >= top / 9) {
                for(String msg : cleanListL) {
                    if (msg != null) {
                        listL.add(msg);
                    }
                }

                top = LockoutBingoClientInfo.location_id == 2 ? 120 : 0;
                i = 0;

                while(true) {
                    Objects.requireNonNull(font);
                    if (i >= top / 9) {
                        for(String msg : cleanListR) {
                            if (msg != null) {
                                listR.add(msg);
                            }
                        }

                        return;
                    }

                    listR.add(" ");
                    ++i;
                }
            }

            listL.add(" ");
            ++i;
        }
    }

    @SubscribeEvent
    public static void piglinBarter(LootTableLoadEvent event) {
        if (event.getName().m_135815_().equals(BuiltInLootTables.f_78738_.m_135815_())) {
            LootTable.Builder builder = LootTable.m_79147_().m_79161_(LootPool.m_79043_().name("main").m_165133_(ConstantValue.m_165692_(1.0F)).m_79076_(LootItem.m_79579_(Items.f_42517_).m_79707_(5).m_79078_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42471_).m_79707_(8).m_79078_((new EnchantRandomlyFunction.Builder()).m_80444_(Enchantments.f_44976_))).m_79076_(LootItem.m_79579_(Items.f_42589_).m_79707_(10).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_))).m_79076_(LootItem.m_79579_(Items.f_42736_).m_79707_(10).m_79078_(SetPotionFunction.m_193075_(Potions.f_43610_))).m_79076_(LootItem.m_79579_(Items.f_42749_).m_79707_(10).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(9.0F, 36.0F)))).m_79076_(LootItem.m_79579_(Items.f_42584_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 8.0F)))).m_79076_(LootItem.m_79579_(Items.f_42401_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 24.0F)))).m_79076_(LootItem.m_79579_(Items.f_42692_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_41999_).m_79707_(40)).m_79076_(LootItem.m_79579_(Items.f_42754_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 3.0F)))).m_79076_(LootItem.m_79579_(Items.f_42613_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(1.0F, 5.0F)))).m_79076_(LootItem.m_79579_(Items.f_42454_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 10.0F)))).m_79076_(LootItem.m_79579_(Items.f_42049_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42691_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(4.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42525_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(5.0F, 12.0F)))).m_79076_(LootItem.m_79579_(Items.f_41832_).m_79707_(40).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(8.0F, 16.0F)))).m_79076_(LootItem.m_79579_(Items.f_42542_).m_79707_(20).m_79078_(SetItemCountFunction.m_165412_(UniformGenerator.m_165780_(2.0F, 6.0F)))));
            LootTable table = builder.m_79167_();
            LootTable piglin_table = event.getTable();
            piglin_table.removePool("main");
            piglin_table.addPool(table.getPool("main"));
        }

    }
}
