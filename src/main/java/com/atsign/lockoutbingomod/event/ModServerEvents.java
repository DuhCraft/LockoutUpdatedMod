package com.atsign.lockoutbingomod.event;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import com.atsign.lockoutbingomod.core.LockoutBingoSaveData;
import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.core.LockoutGoals;
import com.atsign.lockoutbingomod.interfaces.FoodDataExtra;
import com.atsign.lockoutbingomod.network.Networking;
import com.atsign.lockoutbingomod.network.PacketLockoutBingoInfo;
import com.atsign.lockoutbingomod.network.PacketLockoutBingoStartClient;
import com.atsign.lockoutbingomod.network.PacketLockoutTimerUpdate;
import com.atsign.lockoutbingomod.network.PacketPlaySoundEffect;
import com.atsign.lockoutbingomod.network.PacketPlaySoundEffect.SoundsEffect;
import com.mojang.serialization.DataResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.commands.TimeCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.BadRespawnPointDamage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.SleepFinishedTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.NetworkDirection;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(
        modid = "lockoutbingomod",
        bus = Bus.FORGE,
        value = {Dist.DEDICATED_SERVER}
)
public class ModServerEvents {
    public static MinecraftServer server = null;
    public static int GameTimer = -1;
    public static int InGameTime = 0;
    public static int winPoints = 13;
    public static int realMostKelp = 0;
    public static String realMostKelpPlayer = "";
    public static int realMostHoppers = 0;
    public static String realMostHoppersPlayer = "";
    public static int realMostLevels = 0;
    public static String realMostLevelsPlayer = "";
    public static int goalMostKelp = 0;
    public static String goalMostKelpPlayer = "";
    public static int goalMostHoppers = 0;
    public static String goalMostHoppersPlayer = "";
    public static int goalMostLevels = 0;
    public static String goalMostLevelsPlayer = "";
    private static Hashtable<String, Integer> pumpkinTimeRecord = new Hashtable();
    private static String[] undead = new String[]{"drowned", "husk", "phantom", "skeleton", "skeleton_horse", "stray", "wither", "wither_skeleton", "zoglin", "zombie", "zombie_horse", "zombie_villager", "zombified_piglin"};
    private static String[] arthropods = new String[]{"bee", "cave_spider", "spider", "endermite", "silverfish", "spider"};
    public static Hashtable<Position, String> eggThrown = new Hashtable();
    public static ArrayList<Position> chickenSpawn = new ArrayList();
    private static int tick = 0;
    private static int seconds = 0;
    public static boolean inStartPhase = false;
    public static int waitTime = 60;
    private static int cycleTick = 0;
    public static boolean started = false;

    public ModServerEvents() {
    }

    public static boolean triggerClientBoardUpdate(int goal_index, String player_name) {
        boolean game_won = LockoutBingoSetup.team2Score == winPoints || LockoutBingoSetup.team1Score == winPoints;
        if (game_won) {
            if (LinkedHeathServerEvents.is_team1_linked) {
                LinkedHeathServerEvents.is_team1_linked = false;

                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                        player.m_36324_().m_38705_(LinkedHeathServerEvents.food_level_team1);
                        player.m_36324_().m_150378_(LinkedHeathServerEvents.exhaustion_level_team1);
                        player.m_36324_().m_38717_(LinkedHeathServerEvents.saturation_level_team1);
                        ((FoodDataExtra)player.m_36324_()).setPlayerName("");
                    }
                }
            }

            if (LinkedHeathServerEvents.is_team2_linked) {
                LinkedHeathServerEvents.is_team2_linked = false;

                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                        player.m_36324_().m_38705_(LinkedHeathServerEvents.food_level_team2);
                        player.m_36324_().m_150378_(LinkedHeathServerEvents.exhaustion_level_team2);
                        player.m_36324_().m_38717_(LinkedHeathServerEvents.saturation_level_team2);
                        ((FoodDataExtra)player.m_36324_()).setPlayerName("");
                    }
                }
            }
        }

        if (game_won && goal_index == -1) {
            if (LockoutBingoSetup.team1Score == winPoints && !LockoutBingoSetup.test_mode) {
                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                        boolean success = LockoutBingoSetup.team1.contains(player.m_5446_().getString());
                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(success ? SoundsEffect.win : SoundsEffect.lose), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                        player.m_213846_(Component.m_237110_("commands.completedgame", new Object[]{LockoutBingoSetup.team1.get(0)}));
                    }
                }
            } else if (LockoutBingoSetup.team2Score == winPoints && !LockoutBingoSetup.test_mode) {
                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                        boolean success = LockoutBingoSetup.team2.contains(player.m_5446_().getString());
                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(success ? SoundsEffect.win : SoundsEffect.lose), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                        player.m_213846_(Component.m_237110_("commands.completedgame", new Object[]{LockoutBingoSetup.team2.get(0)}));
                    }
                }
            }
        }

        if (!LockoutBingoSetup.team1.contains(player_name) && !LockoutBingoSetup.team2.contains(player_name)) {
            return false;
        } else if (!((PacketLockoutBingoInfo.GoalTuple)LockoutBingoSetup.boardGoalsCompletion.get(goal_index)).player_complete.equals("")) {
            return true;
        } else {
            ((PacketLockoutBingoInfo.GoalTuple)LockoutBingoSetup.boardGoalsCompletion.get(goal_index)).player_complete = player_name;
            boolean isTeam1 = LockoutBingoSetup.team1.contains(player_name);
            if (isTeam1) {
                ++LockoutBingoSetup.team1Score;
            } else {
                ++LockoutBingoSetup.team2Score;
            }

            LockoutBingoSaveData saveData = new LockoutBingoSaveData(LockoutBingoSetup.test_mode, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
            int k = 0;

            for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                    if (k == 0) {
                        try {
                            Path filename = Paths.get(player.m_20194_().m_6237_().toString(), "lockoutSaveData.txt");
                            FileOutputStream file = new FileOutputStream(filename.toString());
                            ObjectOutputStream out = new ObjectOutputStream(file);
                            out.writeObject(saveData);
                            out.close();
                            file.close();
                        } catch (IOException var11) {
                            LockoutBingoMod.LOGGER.info("Can't save Bingo Data");
                        }
                    }

                    boolean success = LockoutBingoSetup.team1.contains(player.m_5446_().getString()) == isTeam1;
                    Networking.INSTANCE.sendTo(new PacketLockoutBingoInfo(LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                    player.m_213846_(Component.m_237110_("commands.completedgoal", new Object[]{player_name, ((PacketLockoutBingoInfo.GoalTuple)LockoutBingoSetup.boardGoalsCompletion.get(goal_index)).goal}));
                    if (!game_won) {
                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(success ? SoundsEffect.team_complete : SoundsEffect.opp_complete), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                    }

                    ++k;
                }
            }

            if (LockoutBingoSetup.team1Score == winPoints && !LockoutBingoSetup.test_mode) {
                started = false;

                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                        boolean success = LockoutBingoSetup.team1.contains(player.m_5446_().getString()) == isTeam1;
                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(success ? SoundsEffect.win : SoundsEffect.lose), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                        player.m_213846_(Component.m_237110_("commands.completedgame", new Object[]{LockoutBingoSetup.team1.get(0)}));
                    }
                }
            } else if (LockoutBingoSetup.team2Score == winPoints && !LockoutBingoSetup.test_mode) {
                started = false;

                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                        boolean success = LockoutBingoSetup.team1.contains(player.m_5446_().getString()) == isTeam1;
                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(success ? SoundsEffect.win : SoundsEffect.lose), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                        player.m_213846_(Component.m_237110_("commands.completedgame", new Object[]{LockoutBingoSetup.team2.get(0)}));
                    }
                }
            }

            return true;
        }
    }

    public static void ClearGoal(int goal_index) {
        ClearGoal(goal_index, true);
    }

    public static void ClearGoal(int goal_index, boolean reset) {
        String old_player = ((PacketLockoutBingoInfo.GoalTuple)LockoutBingoSetup.boardGoalsCompletion.get(goal_index)).player_complete;
        if (LockoutBingoSetup.team1.contains(old_player)) {
            --LockoutBingoSetup.team1Score;
        }

        if (LockoutBingoSetup.team2.contains(old_player)) {
            --LockoutBingoSetup.team2Score;
        }

        ((PacketLockoutBingoInfo.GoalTuple)LockoutBingoSetup.boardGoalsCompletion.get(goal_index)).player_complete = "";
        if (reset) {
            LockoutBingoSetup.resetGoalSearches();
        }

        LockoutBingoSaveData saveData = new LockoutBingoSaveData(LockoutBingoSetup.test_mode, LockoutBingoSetup.team1.size(), LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
        int k = 0;

        for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
            if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                if (k == 0) {
                    try {
                        Path filename = Paths.get(player.m_20194_().m_6237_().toString(), "lockoutSaveData.txt");
                        FileOutputStream file = new FileOutputStream(filename.toString());
                        ObjectOutputStream out = new ObjectOutputStream(file);
                        out.writeObject(saveData);
                        out.close();
                        file.close();
                    } catch (IOException var10) {
                        LockoutBingoMod.LOGGER.info("Can't save Bingo Data");
                    }
                }

                Networking.INSTANCE.sendTo(new PacketLockoutBingoInfo(LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                ++k;
            }
        }

    }

    @SubscribeEvent
    public static void playerJoinedWorld(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer serverPlayerEntity = event.getEntity().m_20194_().m_6846_().m_11259_(event.getEntity().m_20148_());
        if (!LockoutBingoSetup.currentPlayers.contains(serverPlayerEntity.m_5446_().getString())) {
            LockoutBingoSetup.currentPlayers.put(serverPlayerEntity.m_5446_().getString(), serverPlayerEntity);
            if (LockoutBingoSetup.boardGoalsCompletion.size() != 0) {
                PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(LockoutBingoSetup.test_mode, GameTimer, LockoutBingoClientInfo.team1ColorValue, LockoutBingoClientInfo.team2ColorValue, LockoutBingoSetup.team1.size(), LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
                Networking.INSTANCE.sendTo(packet, serverPlayerEntity.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                LockoutBingoClientInfo.setupFakeClientBingo(LockoutBingoSetup.test_mode, LockoutBingoClientInfo.team1ColorValue, LockoutBingoClientInfo.team2ColorValue, LockoutBingoSetup.team1.size(), LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
            }
        }

    }

    @SubscribeEvent
    public static void playerLeftWorld(PlayerEvent.PlayerLoggedOutEvent event) {
        ServerPlayer serverPlayerEntity = event.getEntity().m_20194_().m_6846_().m_11259_(event.getEntity().m_20148_());
        if (LockoutBingoSetup.currentPlayers.contains(serverPlayerEntity.m_5446_().getString())) {
            LockoutBingoSetup.currentPlayers.remove(serverPlayerEntity.m_5446_().getString());
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

    @SubscribeEvent
    public static void advancementRecieved(AdvancementEvent event) {
        Advancement advancement = event.getAdvancement();
        Player player = event.getEntity();
        String advancement_text = advancement.m_138330_().getString().toLowerCase();
        ArrayList<LockoutBingoSetup.AdvancementGoal> toRemove = new ArrayList();

        for(LockoutBingoSetup.AdvancementGoal advancementGoal : LockoutBingoSetup.advancementGoals) {
            for(String advancement_text_goal : advancementGoal.advancement_text) {
                if (advancement_text.equals("[" + advancement_text_goal.toLowerCase() + "]") && triggerClientBoardUpdate(advancementGoal.goal_index, player.m_5446_().getString())) {
                    toRemove.add(advancementGoal);
                }
            }
        }

        for(LockoutBingoSetup.AdvancementGoal advancementGoalToRemove : toRemove) {
            LockoutBingoSetup.advancementGoals.remove(advancementGoalToRemove);
        }

        if (advancement_text.startsWith("[") && advancement_text.endsWith("]") && (LockoutBingoSetup.checkGet15Adv || LockoutBingoSetup.checkGet25Adv || LockoutBingoSetup.checkGet35Adv)) {
            Hashtable<String, HashSet<String>> unique_advancment = null;

            try {
                Path filename = Paths.get(player.m_20194_().m_6237_().toString(), "lockoutAdvancementData.txt");

                try {
                    FileInputStream file_in = new FileInputStream(filename.toString());
                    ObjectInputStream in = new ObjectInputStream(file_in);
                    unique_advancment = (Hashtable)in.readObject();
                    LockoutBingoMod.LOGGER.info("Unique Adv Already exists");
                    in.close();
                    file_in.close();
                } catch (FileNotFoundException var9) {
                    LockoutBingoMod.LOGGER.info("Make new unique adv");
                    unique_advancment = new Hashtable();
                }

                LockoutBingoMod.LOGGER.info("Logging Advancement: " + advancement_text);
                if (unique_advancment.containsKey(player.m_5446_().getString())) {
                    ((HashSet)unique_advancment.get(player.m_5446_().getString())).add(advancement_text);
                } else {
                    unique_advancment.put(player.m_5446_().getString(), new HashSet());
                    ((HashSet)unique_advancment.get(player.m_5446_().getString())).add(advancement_text);
                }

                if (LockoutBingoSetup.checkGet15Adv && ((HashSet)unique_advancment.get(player.m_5446_().getString())).size() >= 15 && triggerClientBoardUpdate(LockoutBingoSetup.get15AdvIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkGet15Adv = false;
                }

                if (LockoutBingoSetup.checkGet25Adv && ((HashSet)unique_advancment.get(player.m_5446_().getString())).size() >= 25 && triggerClientBoardUpdate(LockoutBingoSetup.get25AdvIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkGet25Adv = false;
                }

                if (LockoutBingoSetup.checkGet35Adv && ((HashSet)unique_advancment.get(player.m_5446_().getString())).size() >= 35 && triggerClientBoardUpdate(LockoutBingoSetup.get35AdvIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkGet35Adv = false;
                }

                FileOutputStream file_out = new FileOutputStream(filename.toString());
                ObjectOutputStream out = new ObjectOutputStream(file_out);
                out.writeObject(unique_advancment);
                out.close();
                file_out.close();
            } catch (ClassNotFoundException | IOException var10) {
                LockoutBingoMod.LOGGER.info("Can't save Advancement Data");
            }
        }

    }

    @SubscribeEvent
    public static void animalTameEvent(AnimalTameEvent event) {
        Player player = event.getTamer();
        Animal animalEntity = event.getAnimal();
        String animalName = animalEntity.m_7755_().getString().toLowerCase();

        for(LockoutBingoSetup.TameGoal tameGoal : LockoutBingoSetup.tameGoals) {
            if (animalName.equals(tameGoal.animal_name)) {
                if (triggerClientBoardUpdate(tameGoal.goal_index, player.m_5446_().getString())) {
                    LockoutBingoSetup.tameGoals.remove(tameGoal);
                }
                break;
            }
        }

    }

    public static void itemGoalCheck(Item item, Player player) {
        if (LockoutBingoSetup.itemGoals.containsKey(item) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.itemGoals.get(item), player.m_5446_().getString())) {
            LockoutBingoSetup.itemGoals.remove(item);
        }

        if (LockoutBingoSetup.opponentItemGoals.containsKey(item)) {
            if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                if (triggerClientBoardUpdate((Integer)LockoutBingoSetup.opponentItemGoals.get(item), (String)LockoutBingoSetup.team2.get(0))) {
                    if (LockoutBingoSetup.opponenetSeedList.contains(item)) {
                        for(Item seed : LockoutBingoSetup.opponenetSeedList) {
                            LockoutBingoSetup.opponentItemGoals.remove(seed);
                        }
                    } else {
                        LockoutBingoSetup.opponentItemGoals.remove(item);
                    }
                }
            } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.opponentItemGoals.get(item), (String)LockoutBingoSetup.team1.get(0))) {
                if (LockoutBingoSetup.opponenetSeedList.contains(item)) {
                    for(Item seed : LockoutBingoSetup.opponenetSeedList) {
                        LockoutBingoSetup.opponentItemGoals.remove(seed);
                    }
                } else {
                    LockoutBingoSetup.opponentItemGoals.remove(item);
                }
            }
        }

        int j = 0;

        for(Hashtable<Item, Integer> groupGoal : LockoutBingoSetup.itemGroupGoals) {
            if (item != Items.f_41829_ && groupGoal.containsKey(item)) {
                ((ArrayList)LockoutBingoSetup.itemGroupGoalTracker.get(j)).set((Integer)groupGoal.get(item), true);
            }

            ++j;
        }

    }

    @SubscribeEvent
    public static void checkStatistics(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof ServerPlayer) {
                ServerPlayer player = (ServerPlayer)event.getEntity();
                ServerStatsCounter stats = player.m_8951_();
                PlayerAdvancements advancements = player.m_8960_();
                if (LockoutBingoSetup.checkOpponentDies3 && stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12935_)) >= 3) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                        if (triggerClientBoardUpdate(LockoutBingoSetup.opponentDies3Index, (String)LockoutBingoSetup.team2.get(0))) {
                            LockoutBingoSetup.checkOpponentDies3 = false;
                        }
                    } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentDies3Index, (String)LockoutBingoSetup.team1.get(0))) {
                        LockoutBingoSetup.checkOpponentDies3 = false;
                    }
                }

                if (LockoutBingoSetup.checkOpponentTakes100 && (double)stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12931_)) / (double)10.0F >= (double)100.0F) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                        if (triggerClientBoardUpdate(LockoutBingoSetup.opponentTakes100Index, (String)LockoutBingoSetup.team2.get(0))) {
                            LockoutBingoSetup.checkOpponentTakes100 = false;
                        }
                    } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentTakes100Index, (String)LockoutBingoSetup.team1.get(0))) {
                        LockoutBingoSetup.checkOpponentTakes100 = false;
                    }
                }

                if (LockoutBingoSetup.checkTake200Damage && (double)stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12931_)) / (double)10.0F >= (double)200.0F && triggerClientBoardUpdate(LockoutBingoSetup.take200DamageIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkTake200Damage = false;
                }

                if (LockoutBingoSetup.checkDeal400Damage && (double)stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12928_)) / (double)10.0F >= (double)400.0F && triggerClientBoardUpdate(LockoutBingoSetup.deal400DamageIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkDeal400Damage = false;
                }

                if (LockoutBingoSetup.checkSprint1km && stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12996_)) >= 100000 && triggerClientBoardUpdate(LockoutBingoSetup.sprint1kmIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkSprint1km = false;
                }

                if (LockoutBingoSetup.checkKill100Mobs && stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12936_)) >= 100 && triggerClientBoardUpdate(LockoutBingoSetup.kill100MobsIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkKill100Mobs = false;
                }

                if (LockoutBingoSetup.checkUseCauldron && (stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12945_)) > 0 || stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12946_)) > 0 || stats.m_13015_(Stats.f_12988_.m_12902_(Stats.f_12947_)) > 0) && triggerClientBoardUpdate(LockoutBingoSetup.useCauldronIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkUseCauldron = false;
                }

                if (LockoutBingoSetup.checkKill7UniqueHostiles || LockoutBingoSetup.checkKill10UniqueHostiles || LockoutBingoSetup.checkKill15UniqueHostiles || LockoutBingoSetup.checkKill30UndeadMobs || LockoutBingoSetup.checkKill20Arthropods) {
                    int unique_entities_killed = 0;
                    int undead_killed = 0;
                    int anthropods_killed = 0;

                    for(Map.Entry<Advancement, AdvancementProgress> entry : advancements.f_135964_.entrySet()) {
                        String advancement_text = ((Advancement)entry.getKey()).m_138330_().getString().toLowerCase();
                        if (advancement_text.equals("[monsters hunted]")) {
                            unique_entities_killed = ((AdvancementProgress)entry.getValue()).m_8222_();
                        }
                    }

                    for(EntityType<?> entitytype : Registry.f_122826_) {
                        if (stats.m_13015_(Stats.f_12986_.m_12902_(entitytype)) > 0) {
                            if (entitytype.m_20674_().equals(MobCategory.MONSTER)) {
                            }

                            if (!entitytype.m_20674_().equals(MobCategory.CREATURE) && !entitytype.m_20674_().equals(MobCategory.UNDERGROUND_WATER_CREATURE) && !entitytype.m_20674_().equals(MobCategory.WATER_CREATURE) && entitytype.m_20674_().equals(MobCategory.AXOLOTLS)) {
                            }

                            for(String arthropod : arthropods) {
                                if (entitytype.m_20675_().contains(arthropod)) {
                                    anthropods_killed += stats.m_13015_(Stats.f_12986_.m_12902_(entitytype));
                                }
                            }

                            for(String undead_mob : undead) {
                                if (entitytype.m_20675_().contains(undead_mob)) {
                                    undead_killed += stats.m_13015_(Stats.f_12986_.m_12902_(entitytype));
                                }
                            }
                        }
                    }

                    if (LockoutBingoSetup.checkKill7UniqueHostiles && unique_entities_killed >= 7 && triggerClientBoardUpdate(LockoutBingoSetup.kill7UniqueHostilesIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkKill7UniqueHostiles = false;
                    }

                    if (LockoutBingoSetup.checkKill10UniqueHostiles && unique_entities_killed >= 10 && triggerClientBoardUpdate(LockoutBingoSetup.kill10UniqueHostilesIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkKill10UniqueHostiles = false;
                    }

                    if (LockoutBingoSetup.checkKill15UniqueHostiles && unique_entities_killed >= 15 && triggerClientBoardUpdate(LockoutBingoSetup.kill15UniqueHostilesIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkKill15UniqueHostiles = false;
                    }

                    if (LockoutBingoSetup.checkKill30UndeadMobs && undead_killed >= 30 && triggerClientBoardUpdate(LockoutBingoSetup.kill30UndeadMobsIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkKill30UndeadMobs = false;
                    }

                    if (LockoutBingoSetup.checkKill20Arthropods && anthropods_killed >= 20 && triggerClientBoardUpdate(LockoutBingoSetup.kill20ArthropodsIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkKill20Arthropods = false;
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public static void checkMiscTasks(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof Player) {
                ServerPlayer player = (ServerPlayer)event.getEntity();
                if (LockoutBingoSetup.checkOpponentCatchesFire && player.m_20291_(0)) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                        if (triggerClientBoardUpdate(LockoutBingoSetup.opponentCatchesFireIndex, (String)LockoutBingoSetup.team2.get(0))) {
                            LockoutBingoSetup.checkOpponentCatchesFire = false;
                        }
                    } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentCatchesFireIndex, (String)LockoutBingoSetup.team1.get(0))) {
                        LockoutBingoSetup.checkOpponentCatchesFire = false;
                    }
                }

                if (LockoutBingoSetup.checkBedrock) {
                    BlockPos down = player.m_20183_().m_7495_();
                    BlockState blockStateBelow = player.f_19853_.m_8055_(down);
                    Block blockBelow = blockStateBelow.m_60734_();
                    if (blockBelow.equals(Blocks.f_50752_) && triggerClientBoardUpdate(LockoutBingoSetup.bedrockIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkBedrock = false;
                    }
                }

                if (LockoutBingoSetup.checkOpponentTouchesWater && player.m_20069_()) {
                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                        if (triggerClientBoardUpdate(LockoutBingoSetup.opponentTouchesWaterIndex, (String)LockoutBingoSetup.team2.get(0))) {
                            LockoutBingoSetup.checkOpponentTouchesWater = false;
                        }
                    } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentTouchesWaterIndex, (String)LockoutBingoSetup.team1.get(0))) {
                        LockoutBingoSetup.checkOpponentTouchesWater = false;
                    }
                }

                if (LockoutBingoSetup.checkSkyLimit && player.m_20186_() >= (double)320.0F && triggerClientBoardUpdate(LockoutBingoSetup.skyLimitIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkSkyLimit = false;
                }

                if (LockoutBingoSetup.checkLevel15 && player.f_36078_ >= 15 && triggerClientBoardUpdate(LockoutBingoSetup.level15Index, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkLevel15 = false;
                }

                if (LockoutBingoSetup.checkLevel30 && player.f_36078_ >= 30 && triggerClientBoardUpdate(LockoutBingoSetup.level30Index, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkLevel30 = false;
                }

                if (LockoutBingoSetup.checkCarvedPumpkin) {
                    ItemStack itemstack = player.m_150109_().m_36052_(3);
                    if (pumpkinTimeRecord.containsKey(player.m_5446_().getString())) {
                        if (itemstack.m_150930_(Blocks.f_50143_.m_5456_())) {
                            int originalTime = (Integer)pumpkinTimeRecord.get(player.m_5446_().getString());
                            if (InGameTime >= originalTime + 300 && triggerClientBoardUpdate(LockoutBingoSetup.carvedPumpkinIndex, player.m_5446_().getString())) {
                                LockoutBingoSetup.checkCarvedPumpkin = false;
                            }
                        } else {
                            pumpkinTimeRecord.remove(player.m_5446_().getString());
                        }
                    } else if (itemstack.m_150930_(Blocks.f_50143_.m_5456_())) {
                        pumpkinTimeRecord.put(player.m_5446_().getString(), InGameTime);
                    }
                }

                if (LockoutBingoSetup.checkHunger && player.m_36324_().m_38702_() == 0 && triggerClientBoardUpdate(LockoutBingoSetup.hungerIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkHunger = false;
                }

            }
        }
    }

    @SubscribeEvent
    public static void checkStatusEffects(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof Player) {
                ServerPlayer player = (ServerPlayer)event.getEntity();

                for(MobEffectInstance effectinstance : player.m_21220_()) {
                    if (LockoutBingoSetup.effectGoals.containsKey(effectinstance.m_19544_()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.effectGoals.get(effectinstance.m_19544_()), player.m_5446_().getString())) {
                        LockoutBingoSetup.effectGoals.remove(effectinstance.m_19544_());
                    }
                }

                if (LockoutBingoSetup.checkGet3StatusEffects && player.m_21220_().size() >= 3 && triggerClientBoardUpdate(LockoutBingoSetup.get3StatusEffectsIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkGet3StatusEffects = false;
                }

                if (LockoutBingoSetup.checkGet6StatusEffects && player.m_21220_().size() >= 6 && triggerClientBoardUpdate(LockoutBingoSetup.get6StatusEffectsIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkGet6StatusEffects = false;
                }

            }
        }
    }

    @SubscribeEvent
    public static void checkRidingGoals(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof Player) {
                ServerPlayer player = (ServerPlayer)event.getEntity();
                ArrayList<String> toRidingRemove = new ArrayList();
                if (player.m_20202_() != null) {
                    for(String entity : LockoutBingoSetup.rideEntityGoals.keySet()) {
                        switch (entity) {
                            case "pig":
                                if (player.m_20202_() instanceof Pig) {
                                    Pig pig = (Pig)player.m_20202_();
                                    if (pig.m_6254_() && player.m_21205_().m_41720_().equals(Items.f_42684_) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.rideEntityGoals.get(entity), player.m_5446_().getString())) {
                                        toRidingRemove.add(entity);
                                    }
                                }
                                break;
                            case "horse":
                                if (player.m_20202_() instanceof Horse) {
                                    Horse horse = (Horse)player.m_20202_();
                                    if (horse.m_6254_() && triggerClientBoardUpdate((Integer)LockoutBingoSetup.rideEntityGoals.get(entity), player.m_5446_().getString())) {
                                        toRidingRemove.add(entity);
                                    }
                                }
                                break;
                            case "minecart":
                                if (player.m_20202_() instanceof Minecart && triggerClientBoardUpdate((Integer)LockoutBingoSetup.rideEntityGoals.get(entity), player.m_5446_().getString())) {
                                    toRidingRemove.add(entity);
                                }
                        }
                    }
                }

                for(String ridingRemove : toRidingRemove) {
                    LockoutBingoSetup.rideEntityGoals.remove(ridingRemove);
                }

            }
        }
    }

    private static String printBiome(Holder<Biome> p_205375_) {
        return (String)p_205375_.m_203439_().map((p_205377_) -> p_205377_.m_135782_().m_135815_(), (p_205367_) -> "[unregistered " + p_205367_ + "]");
    }

    @SubscribeEvent
    public static void checkBiomeGoals(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof Player) {
                ServerPlayer player = (ServerPlayer)event.getEntity();
                String playerBiome = printBiome(player.f_19853_.m_204166_(player.m_20183_()));
                if (LockoutBingoSetup.biomeGoals.containsKey(playerBiome) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.biomeGoals.get(playerBiome), player.m_5446_().getString())) {
                    if (playerBiome.equals("MUSHROOM_FIELDS".toLowerCase())) {
                        LockoutBingoSetup.biomeGoals.remove("MUSHROOM_FIELDS".toLowerCase());
                    } else if (!playerBiome.equals("ERODED_BADLANDS".toLowerCase()) && !playerBiome.equals("BADLANDS".toLowerCase()) && !playerBiome.equals("WOODED_BADLANDS".toLowerCase())) {
                        LockoutBingoSetup.biomeGoals.remove(playerBiome);
                    } else {
                        LockoutBingoSetup.biomeGoals.remove("ERODED_BADLANDS".toLowerCase());
                        LockoutBingoSetup.biomeGoals.remove("BADLANDS".toLowerCase());
                        LockoutBingoSetup.biomeGoals.remove("WOODED_BADLANDS".toLowerCase());
                    }
                }

            }
        }
    }

    @SubscribeEvent
    public static void checkInventory(LivingEvent.LivingTickEvent event) {
        if (started && !inStartPhase) {
            if (event.getEntity() instanceof Player) {
                ServerPlayer player = (ServerPlayer)event.getEntity();
                performTrackAction(player);
                ArrayList<Item> items = new ArrayList();
                boolean unique = true;
                int kelp_count = 0;
                int hopper_count = 0;
                int level_count = player.f_36078_;

                for(int i = 0; i < player.m_150109_().f_35974_.size(); ++i) {
                    if (!((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_().equals(Items.f_41852_) && !items.contains(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_())) {
                        items.add(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_());
                    } else {
                        unique = false;
                    }

                    if (((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_().equals(Items.f_42515_)) {
                        kelp_count += ((ItemStack)player.m_150109_().f_35974_.get(i)).m_41613_();
                    }

                    if (((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_().equals(Items.f_42155_)) {
                        hopper_count += ((ItemStack)player.m_150109_().f_35974_.get(i)).m_41613_();
                    }

                    itemGoalCheck(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_(), player);
                    if (((ItemStack)player.m_150109_().f_35974_.get(i)).m_41613_() == 64 && LockoutBingoSetup.item64Goals.containsKey(Items.f_41829_) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.item64Goals.get(Items.f_41829_), player.m_5446_().getString())) {
                        LockoutBingoSetup.itemGoals.remove(Items.f_41829_);
                    }

                    if (LockoutBingoSetup.item64Goals.containsKey(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_()) && ((ItemStack)player.m_150109_().f_35974_.get(i)).m_41613_() == 64 && triggerClientBoardUpdate((Integer)LockoutBingoSetup.item64Goals.get(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_()), player.m_5446_().getString())) {
                        LockoutBingoSetup.item64Goals.remove(((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_());
                    }
                }

                if (LockoutBingoSetup.checkFillInventory && unique && triggerClientBoardUpdate(LockoutBingoSetup.fillInventoryIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkFillInventory = false;
                }

                if (LockoutBingoSetup.checkObtainMoreKelp) {
                    if (realMostKelp < kelp_count) {
                        realMostKelp = kelp_count;
                        realMostKelpPlayer = player.m_5446_().getString();
                    }

                    if (Objects.equals(realMostKelpPlayer, player.m_5446_().getString()) && kelp_count < realMostKelp) {
                        realMostKelpPlayer = "";
                        realMostKelp = 0;
                    }
                }

                if (LockoutBingoSetup.checkObtainMoreHopper) {
                    if (realMostHoppers < hopper_count) {
                        realMostHoppers = hopper_count;
                        realMostHoppersPlayer = player.m_5446_().getString();
                    }

                    if (Objects.equals(realMostHoppersPlayer, player.m_5446_().getString()) && hopper_count < realMostHoppers) {
                        realMostHoppersPlayer = "";
                        realMostHoppers = 0;
                    }
                }

                if (LockoutBingoSetup.checkMoreLevels) {
                    if (realMostLevels < level_count) {
                        realMostLevels = level_count;
                        realMostLevelsPlayer = player.m_5446_().getString();
                    }

                    if (Objects.equals(realMostLevelsPlayer, player.m_5446_().getString()) && level_count < realMostLevels) {
                        realMostLevelsPlayer = "";
                        realMostLevels = 0;
                    }
                }

                itemGoalCheck(((ItemStack)player.m_150109_().f_35976_.get(0)).m_41720_(), player);
                int k = 0;
                ArrayList<Integer> indexesToRemove = new ArrayList();

                for(ArrayList<Boolean> groupTracker : LockoutBingoSetup.itemGroupGoalTracker) {
                    boolean passing = true;
                    int count = 0;

                    for(int l = 0; l < groupTracker.size(); ++l) {
                        if (!(Boolean)groupTracker.get(l)) {
                            passing = false;
                        } else {
                            ++count;
                        }

                        groupTracker.set(l, false);
                    }

                    if (passing && triggerClientBoardUpdate((Integer)((Hashtable)LockoutBingoSetup.itemGroupGoals.get(k)).get(Items.f_41829_), player.m_5446_().getString())) {
                        indexesToRemove.add(k);
                    }

                    if (((Hashtable)LockoutBingoSetup.itemGroupGoals.get(k)).containsKey(Items.f_41926_) && count >= (Integer)((Hashtable)LockoutBingoSetup.itemGroupGoals.get(k)).get(Items.f_41926_) && triggerClientBoardUpdate((Integer)((Hashtable)LockoutBingoSetup.itemGroupGoals.get(k)).get(Items.f_41829_), player.m_5446_().getString())) {
                        indexesToRemove.add(k);
                    }

                    ++k;
                }

                int adj = 0;

                for(int i : indexesToRemove) {
                    LockoutBingoSetup.itemGroupGoals.remove(i - adj);
                    LockoutBingoSetup.itemGroupGoalTracker.remove(i - adj);
                    ++adj;
                }

                for(int i = 0; i < player.m_150109_().f_35975_.size(); ++i) {
                    int j = 0;

                    for(Hashtable<Item, Integer> groupGoal : LockoutBingoSetup.armorGroupGoals) {
                        if (groupGoal.containsKey(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_())) {
                            ((ArrayList)LockoutBingoSetup.armorGroupGoalTracker.get(j)).set((Integer)groupGoal.get(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_()), true);
                            break;
                        }

                        ++j;
                    }

                    int z = 0;
                    indexesToRemove.clear();

                    for(Hashtable<Item, Integer> armorGoal : LockoutBingoSetup.armorGoals) {
                        if (armorGoal.containsKey(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_()) && triggerClientBoardUpdate((Integer)armorGoal.get(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_()), player.m_5446_().getString())) {
                            indexesToRemove.add(z);
                        }

                        ++z;
                    }

                    adj = 0;

                    for(int t : indexesToRemove) {
                        LockoutBingoSetup.armorGoals.remove(t - adj);
                        ++adj;
                    }

                    z = 0;
                    indexesToRemove.clear();

                    for(Hashtable<Item, Tuple<DyeItem, Integer>> dyedLeatherGoal : LockoutBingoSetup.dyedLeatherGoal) {
                        if (dyedLeatherGoal.containsKey(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_())) {
                            ItemStack tmp = new ItemStack(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_());
                            tmp = DyeableLeatherItem.m_41118_(tmp, new ArrayList(List.of((DyeItem)((Tuple)dyedLeatherGoal.get(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_())).m_14418_())));
                            int color = ((DyeableLeatherItem)tmp.m_41720_()).m_41121_(tmp);
                            if (color == ((DyeableLeatherItem)((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_()).m_41121_((ItemStack)player.m_150109_().f_35975_.get(i)) && triggerClientBoardUpdate((Integer)((Tuple)dyedLeatherGoal.get(((ItemStack)player.m_150109_().f_35975_.get(i)).m_41720_())).m_14419_(), player.m_5446_().getString())) {
                                indexesToRemove.add(z);
                            }
                        }

                        ++z;
                    }

                    adj = 0;

                    for(int t : indexesToRemove) {
                        LockoutBingoSetup.dyedLeatherGoal.remove(t - adj);
                        ++adj;
                    }
                }

                int n = 0;
                indexesToRemove.clear();

                for(ArrayList<Boolean> groupTracker : LockoutBingoSetup.armorGroupGoalTracker) {
                    boolean passing = true;

                    for(int l = 0; l < groupTracker.size(); ++l) {
                        if (!(Boolean)groupTracker.get(l)) {
                            passing = false;
                        }

                        groupTracker.set(l, false);
                    }

                    if (passing && triggerClientBoardUpdate((Integer)((Hashtable)LockoutBingoSetup.armorGroupGoals.get(n)).get(Items.f_41829_), player.m_5446_().getString())) {
                        indexesToRemove.add(n);
                    }

                    ++n;
                }

                adj = 0;

                for(int i : indexesToRemove) {
                    LockoutBingoSetup.armorGroupGoals.remove(i - adj);
                    LockoutBingoSetup.armorGroupGoalTracker.remove(i - adj);
                    ++adj;
                }

            }
        }
    }

    @SubscribeEvent
    public static void checkDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (LockoutBingoSetup.checkPlayerKillPlayer && event.getSource().m_7639_() instanceof Player) {
                Player opponent = (Player)event.getSource().m_7639_();
                if (!opponent.m_5446_().getString().equals(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.pkpGoalIndex, opponent.m_5446_().getString())) {
                    LockoutBingoSetup.checkPlayerKillPlayer = false;
                }
            }

            if (LockoutBingoSetup.checkOpponentDies) {
                if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                    if (triggerClientBoardUpdate(LockoutBingoSetup.opponentDiesIndex, (String)LockoutBingoSetup.team2.get(0))) {
                        LockoutBingoSetup.checkOpponentDies = false;
                    }
                } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentDiesIndex, (String)LockoutBingoSetup.team1.get(0))) {
                    LockoutBingoSetup.checkOpponentDies = false;
                }
            }

            if (LockoutBingoSetup.diedGoals.containsKey(event.getSource()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedGoals.get(event.getSource()), player.m_5446_().getString())) {
                LockoutBingoSetup.diedGoals.remove(event.getSource());
            }

            if (LockoutBingoSetup.checkMagid && event.getSource().m_19387_() && triggerClientBoardUpdate(LockoutBingoSetup.magicGoalIndex, player.m_5446_().getString())) {
                LockoutBingoSetup.checkMagid = false;
            }

            for(String entity : Set.copyOf(LockoutBingoSetup.diedEntityGoals.keySet())) {
                if (LockoutBingoSetup.diedEntityGoals.containsKey(entity)) {
                    switch (entity) {
                        case "llama":
                            if (event.getSource().m_7639_() instanceof Llama && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                            break;
                        case "bee":
                            if (event.getSource().m_7640_() instanceof Bee && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                            break;
                        case "firework":
                            if (event.getSource().m_7640_() instanceof FireworkRocketEntity && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                            break;
                        case "iron_golem":
                            if (event.getSource().m_7640_() instanceof IronGolem && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                            break;
                        case "tnt_minecart":
                            if (event.getSource().m_7639_() instanceof MinecartTNT && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                            break;
                        case "intentional_game_design":
                            if (event.getSource() instanceof BadRespawnPointDamage && triggerClientBoardUpdate((Integer)LockoutBingoSetup.diedEntityGoals.get(entity), player.m_5446_().getString())) {
                                LockoutBingoSetup.diedEntityGoals.remove(entity);
                                return;
                            }
                    }
                }
            }
        } else {
            for(String entity : Set.copyOf(LockoutBingoSetup.killEntityGoals.keySet())) {
                if (event.getSource().m_7639_() instanceof Player) {
                    Player opponent = (Player)event.getSource().m_7639_();
                    switch (entity) {
                        case "witch":
                            if (event.getEntity() instanceof Witch && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "zombie_villager":
                            if (event.getEntity() instanceof ZombieVillager && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "stray":
                            if (event.getEntity() instanceof Stray && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "zoglin":
                            if (event.getEntity() instanceof Zoglin && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "silverfish":
                            if (event.getEntity() instanceof Silverfish && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "guardian":
                            if (event.getEntity() instanceof Guardian && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "ghast":
                            if (event.getEntity() instanceof Ghast && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "snow_golem":
                            if (event.getEntity() instanceof SnowGolem && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "ender_dragon":
                            if (event.getEntity() instanceof EnderDragon && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "elder_guardian":
                            if (event.getEntity() instanceof ElderGuardian && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                            break;
                        case "endermite":
                            if (event.getEntity() instanceof Endermite && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                                LockoutBingoSetup.killEntityGoals.remove(entity);
                            }
                    }

                    if (entity.contains("Sheep") && event.getEntity() instanceof Sheep) {
                        int color_index = -1;

                        for(int i = 0; i < LockoutGoals.colors.length; ++i) {
                            if (entity.contains(LockoutGoals.colors[i])) {
                                if (color_index != -1) {
                                    if (LockoutGoals.colors[i].length() > LockoutGoals.colors[color_index].length()) {
                                        color_index = i;
                                    }
                                } else {
                                    color_index = i;
                                }
                            }
                        }

                        if (((Sheep)event.getEntity()).m_29874_().equals(LockoutGoals.color_map[color_index]) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.killEntityGoals.get(entity), opponent.m_5446_().getString())) {
                            LockoutBingoSetup.killEntityGoals.remove(entity);
                        }
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void playerBrewedPotion(PlayerBrewedPotionEvent event) {
        for(MobEffectInstance effectinstance : PotionUtils.m_43547_(event.getStack())) {
            if (LockoutBingoSetup.potionBrewGoals.containsKey(effectinstance.m_19544_()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.potionBrewGoals.get(effectinstance.m_19544_()), event.getEntity().m_5446_().getString())) {
                LockoutBingoSetup.potionBrewGoals.remove(effectinstance.m_19544_());
            }
        }

    }

    @SubscribeEvent
    public static void miningGoals(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        if (LockoutBingoSetup.miningGoals.containsKey(event.getState().m_60734_()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.miningGoals.get(event.getState().m_60734_()), player.m_5446_().getString())) {
            if (!event.getState().m_60734_().equals(Blocks.f_50264_) && !event.getState().m_60734_().equals(Blocks.f_152479_)) {
                if (!event.getState().m_60734_().equals(Blocks.f_50089_) && !event.getState().m_60734_().equals(Blocks.f_152474_)) {
                    LockoutBingoSetup.miningGoals.remove(event.getState().m_60734_());
                } else {
                    LockoutBingoSetup.miningGoals.remove(Blocks.f_50089_);
                    LockoutBingoSetup.miningGoals.remove(Blocks.f_152474_);
                }
            } else {
                LockoutBingoSetup.miningGoals.remove(Blocks.f_50264_);
                LockoutBingoSetup.miningGoals.remove(Blocks.f_152479_);
            }
        }

    }

    @SubscribeEvent
    public static void playerBreedAnimal(BabyEntitySpawnEvent event) {
        ServerPlayer playerEntity = (ServerPlayer)event.getCausedByPlayer();
        if (event.getParentA() instanceof Animal) {
            Animal animalEntity = (Animal)event.getParentA();
            String animalName = animalEntity.m_7755_().getString().toLowerCase();
            int k = 0;

            for(LockoutBingoSetup.BreedGoal breedGoal : LockoutBingoSetup.breedGoals) {
                if (animalName.equals(breedGoal.animal_name)) {
                    if (triggerClientBoardUpdate(breedGoal.goal_index, playerEntity.m_5446_().getString())) {
                        LockoutBingoSetup.breedGoals.remove(k);
                    }
                    break;
                }

                ++k;
            }
        }

    }

    @SubscribeEvent
    public static void eatingGoals(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof Player) {
            ServerPlayer player = (ServerPlayer)event.getEntity();
            Hashtable<String, HashSet<String>> unique_eating = null;
            if (event.getItem().m_41720_().m_41472_() && (LockoutBingoSetup.checkEat5 || LockoutBingoSetup.checkEat10 || LockoutBingoSetup.checkEat20)) {
                String food = event.getItem().m_41720_().m_5524_();

                try {
                    Path filename = Paths.get(player.m_20194_().m_6237_().toString(), "lockoutEatData.txt");

                    try {
                        FileInputStream file_in = new FileInputStream(filename.toString());
                        ObjectInputStream in = new ObjectInputStream(file_in);
                        unique_eating = (Hashtable)in.readObject();
                        in.close();
                        file_in.close();
                    } catch (FileNotFoundException var7) {
                        unique_eating = new Hashtable();
                    }

                    if (unique_eating.containsKey(player.m_5446_().getString())) {
                        ((HashSet)unique_eating.get(player.m_5446_().getString())).add(food);
                    } else {
                        unique_eating.put(player.m_5446_().getString(), new HashSet());
                        ((HashSet)unique_eating.get(player.m_5446_().getString())).add(food);
                    }

                    if (LockoutBingoSetup.checkEat5 && ((HashSet)unique_eating.get(player.m_5446_().getString())).size() >= 5 && triggerClientBoardUpdate(LockoutBingoSetup.eat5Index, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkEat5 = false;
                    }

                    if (LockoutBingoSetup.checkEat10 && ((HashSet)unique_eating.get(player.m_5446_().getString())).size() >= 10 && triggerClientBoardUpdate(LockoutBingoSetup.eat10Index, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkEat10 = false;
                    }

                    if (LockoutBingoSetup.checkEat20 && ((HashSet)unique_eating.get(player.m_5446_().getString())).size() >= 20 && triggerClientBoardUpdate(LockoutBingoSetup.eat20Index, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkEat20 = false;
                    }

                    FileOutputStream file_out = new FileOutputStream(filename.toString());
                    ObjectOutputStream out = new ObjectOutputStream(file_out);
                    out.writeObject(unique_eating);
                    out.close();
                    file_out.close();
                } catch (ClassNotFoundException | IOException var8) {
                    LockoutBingoMod.LOGGER.info("Can't save Eat Data");
                }
            }

            if (LockoutBingoSetup.eatingGoals.containsKey(event.getItem().m_41720_()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.eatingGoals.get(event.getItem().m_41720_()), player.m_5446_().getString())) {
                LockoutBingoSetup.eatingGoals.remove(event.getItem().m_41720_());
            }
        }

    }

    @SubscribeEvent
    public static void opponentTakesDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            if (LockoutBingoSetup.opponentDamageGoals.containsKey(event.getSource())) {
                if (LockoutBingoSetup.team1.contains(player.m_5446_().getString())) {
                    if (triggerClientBoardUpdate((Integer)LockoutBingoSetup.opponentDamageGoals.get(event.getSource()), (String)LockoutBingoSetup.team2.get(0))) {
                        LockoutBingoSetup.opponentDamageGoals.remove(event.getSource());
                    }
                } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && triggerClientBoardUpdate((Integer)LockoutBingoSetup.opponentDamageGoals.get(event.getSource()), (String)LockoutBingoSetup.team1.get(0))) {
                    LockoutBingoSetup.opponentDamageGoals.remove(event.getSource());
                }
            }

            if (LockoutBingoSetup.checkOpponentHitsYou && event.getSource().m_7639_() instanceof Player) {
                Player opp = (Player)event.getSource().m_7639_();
                if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) && LockoutBingoSetup.team2.contains(opp.m_5446_().getString())) {
                    if (triggerClientBoardUpdate(LockoutBingoSetup.opponentHitsYouIndex, player.m_5446_().getString())) {
                        LockoutBingoSetup.checkOpponentHitsYou = false;
                    }
                } else if (LockoutBingoSetup.team2.contains(player.m_5446_().getString()) && LockoutBingoSetup.team1.contains(opp.m_5446_().getString()) && triggerClientBoardUpdate(LockoutBingoSetup.opponentHitsYouIndex, player.m_5446_().getString())) {
                    LockoutBingoSetup.checkOpponentHitsYou = false;
                }
            }
        }

    }

    @SubscribeEvent
    public static void sleepAloneFinishes(SleepFinishedTimeEvent event) {
        if (LockoutBingoSetup.checkSleepAlone && event.getLevel() instanceof ServerLevel) {
            List<ServerPlayer> playerList = ((ServerLevel)event.getLevel()).m_7654_().m_6846_().m_11314_();
            ResourceKey<Level> dimensionKey = ((ServerLevel)event.getLevel()).m_46472_();
            boolean isAlone = true;
            String player_name = "";

            for(ServerPlayer playee : playerList) {
                if (player_name != "" && !playee.m_5446_().getString().equals(player_name) && playee.m_9236_().m_46472_().equals(dimensionKey)) {
                    isAlone = false;
                } else if (player_name == "" && playee.m_9236_().m_46472_().equals(dimensionKey)) {
                    player_name = playee.m_5446_().getString();
                }
            }

            if (isAlone && triggerClientBoardUpdate(LockoutBingoSetup.sleepAloneIndex, player_name)) {
                LockoutBingoSetup.checkSleepAlone = false;
            }
        }

    }

    @SubscribeEvent
    public static void eggProjectileThrown(ProjectileImpactEvent event) {
        if (LockoutBingoSetup.checkSnowballHit && event.getEntity() instanceof Snowball) {
            Snowball snowballEntity = (Snowball)event.getEntity();
            if (snowballEntity.m_37282_() instanceof Player) {
                Player playerEntity = (Player)((Snowball)event.getEntity()).m_37282_();
                if (event.getRayTraceResult().m_6662_() == Type.ENTITY) {
                    double x = event.getRayTraceResult().m_82450_().m_7096_();
                    double y = event.getRayTraceResult().m_82450_().m_7098_();
                    double z = event.getRayTraceResult().m_82450_().m_7094_();

                    for(Player opp : playerEntity.f_19853_.m_45976_(Player.class, new AABB(x - (double)1.0F, y - (double)1.0F, z - (double)1.0F, x + (double)1.0F, y + (double)1.0F, z + (double)1.0F))) {
                        if ((LockoutBingoSetup.team1.contains(opp.m_5446_().getString()) || LockoutBingoSetup.team2.contains(opp.m_5446_().getString())) && (!LockoutBingoSetup.team1.contains(opp.m_5446_().getString()) || !LockoutBingoSetup.team1.contains(playerEntity.m_5446_().getString())) && (!LockoutBingoSetup.team2.contains(opp.m_5446_().getString()) || !LockoutBingoSetup.team2.contains(playerEntity.m_5446_().getString())) && triggerClientBoardUpdate(LockoutBingoSetup.snowballHitIndex, playerEntity.m_5446_().getString())) {
                            LockoutBingoSetup.checkSnowballHit = false;
                        }
                    }
                }
            }
        }

    }

    @SubscribeEvent
    public static void chickenEggSpawn(EntityJoinLevelEvent event) {
        if (LockoutBingoSetup.checkChickenEggSpawn && event.getEntity() instanceof Chicken) {
            chickenSpawn.add(new Position(event.getEntity().m_20185_(), event.getEntity().m_20186_(), event.getEntity().m_20189_()));
        }

    }

    public static void resetClockInfo() {
        tick = 0;
        seconds = 0;
        inStartPhase = false;
        waitTime = 60;
        cycleTick = 0;
        started = false;
        GameTimer = -1;
        InGameTime = 0;
        winPoints = 13;
        realMostKelp = 0;
        realMostKelpPlayer = "";
        realMostHoppers = 0;
        realMostHoppersPlayer = "";
        realMostLevels = 0;
        realMostLevelsPlayer = "";
        goalMostKelp = 0;
        goalMostKelpPlayer = "";
        goalMostHoppers = 0;
        goalMostHoppersPlayer = "";
        goalMostLevels = 0;
        goalMostLevelsPlayer = "";
        pumpkinTimeRecord.clear();
    }

    @SubscribeEvent
    public static void clock(TickEvent.ServerTickEvent event) {
        if (server != null) {
            for(String player_name : LockoutBingoSetup.currentPlayers.keySet()) {
                ServerPlayer tmp = server.m_6846_().m_11255_(player_name);
                if (tmp != null) {
                    LockoutBingoSetup.currentPlayers.put(player_name, tmp);
                }
            }
        }

        if (inStartPhase) {
            started = true;
            if (tick != 40) {
                ++tick;
            } else {
                tick = 0;
                ++seconds;
                String msg = "";
                int temp = seconds - waitTime;
                switch (temp) {
                    case -3:
                        msg = "3";
                        break;
                    case -2:
                        msg = "2";
                        break;
                    case -1:
                        msg = "1";
                        break;
                    case 0:
                        msg = "GO!";
                        TimeCommand.m_139077_(LockoutBingoSetup.mcCommandSource, 1000);
                        tick = 0;
                        inStartPhase = false;
                        seconds = 0;
                        break;
                    default:
                        return;
                }

                if (!msg.equals("")) {
                    for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                        if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                            player.m_213846_(Component.m_237113_(msg));
                            if (msg.equals("GO!")) {
                                if (!LockoutBingoSetup.test_mode) {
                                    player.m_6915_();
                                }

                                player.m_36324_().m_38705_(20);
                                player.m_21153_(player.m_21233_());
                                player.m_21219_();
                            }
                        }
                    }
                }
            }
        }

        if (started) {
            if (!inStartPhase) {
                if (tick != 40) {
                    ++tick;
                } else {
                    tick = 0;
                    ++InGameTime;
                    if (LockoutBingoSetup.checkObtainMoreKelp) {
                        if (Objects.equals(goalMostKelpPlayer, "") && goalMostKelp == 0) {
                            if (realMostKelp != goalMostKelp) {
                                goalMostKelp = realMostKelp;
                                goalMostKelpPlayer = realMostKelpPlayer;
                                triggerClientBoardUpdate(LockoutBingoSetup.obtainMoreKelpIndex, goalMostKelpPlayer);
                            }
                        } else if (InGameTime % 30 == 0 && !goalMostKelpPlayer.equals(realMostKelpPlayer)) {
                            if (realMostKelp == 0) {
                                goalMostKelpPlayer = "";
                                goalMostKelp = 0;
                                ClearGoal(LockoutBingoSetup.obtainMoreKelpIndex, false);
                            } else {
                                goalMostKelp = realMostKelp;
                                goalMostKelpPlayer = realMostKelpPlayer;
                                ClearGoal(LockoutBingoSetup.obtainMoreKelpIndex, false);
                                triggerClientBoardUpdate(LockoutBingoSetup.obtainMoreKelpIndex, goalMostKelpPlayer);
                            }
                        }
                    }

                    if (LockoutBingoSetup.checkObtainMoreHopper) {
                        if (Objects.equals(goalMostHoppersPlayer, "") && goalMostHoppers == 0) {
                            if (realMostHoppers != goalMostHoppers) {
                                goalMostHoppers = realMostHoppers;
                                goalMostHoppersPlayer = realMostHoppersPlayer;
                                triggerClientBoardUpdate(LockoutBingoSetup.obtainMoreHopperIndex, goalMostHoppersPlayer);
                            }
                        } else if (InGameTime % 30 == 0 && !goalMostHoppersPlayer.equals(realMostHoppersPlayer)) {
                            if (realMostHoppers == 0) {
                                goalMostHoppersPlayer = "";
                                goalMostHoppers = 0;
                                ClearGoal(LockoutBingoSetup.obtainMoreHopperIndex, false);
                            } else {
                                goalMostHoppers = realMostHoppers;
                                goalMostHoppersPlayer = realMostHoppersPlayer;
                                ClearGoal(LockoutBingoSetup.obtainMoreHopperIndex, false);
                                triggerClientBoardUpdate(LockoutBingoSetup.obtainMoreHopperIndex, goalMostHoppersPlayer);
                            }
                        }
                    }

                    if (LockoutBingoSetup.checkMoreLevels) {
                        if (Objects.equals(goalMostLevelsPlayer, "") && goalMostLevels == 0) {
                            if (realMostLevels != goalMostLevels) {
                                goalMostLevels = realMostLevels;
                                goalMostLevelsPlayer = realMostLevelsPlayer;
                                triggerClientBoardUpdate(LockoutBingoSetup.moreLevelsIndex, goalMostLevelsPlayer);
                            }
                        } else if (InGameTime % 30 == 0 && !goalMostLevelsPlayer.equals(realMostLevelsPlayer)) {
                            if (realMostLevels == 0) {
                                goalMostLevelsPlayer = "";
                                goalMostLevels = 0;
                                ClearGoal(LockoutBingoSetup.moreLevelsIndex, false);
                            } else {
                                goalMostLevels = realMostLevels;
                                goalMostLevelsPlayer = realMostLevelsPlayer;
                                ClearGoal(LockoutBingoSetup.moreLevelsIndex, false);
                                triggerClientBoardUpdate(LockoutBingoSetup.moreLevelsIndex, goalMostLevelsPlayer);
                            }
                        }
                    }

                    if (GameTimer > 0) {
                        --GameTimer;

                        for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                            if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                                Networking.INSTANCE.sendTo(new PacketLockoutTimerUpdate(GameTimer), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                            }
                        }

                        if (GameTimer == 0) {
                            if (LockoutBingoSetup.team1Score > LockoutBingoSetup.team2Score) {
                                winPoints = LockoutBingoSetup.team1Score;
                                triggerClientBoardUpdate(-1, "");
                            } else if (LockoutBingoSetup.team2Score > LockoutBingoSetup.team1Score) {
                                winPoints = LockoutBingoSetup.team2Score;
                                triggerClientBoardUpdate(-1, "");
                            } else {
                                winPoints = LockoutBingoSetup.team1Score + 1;

                                for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                                    if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                                        Networking.INSTANCE.sendTo(new PacketPlaySoundEffect(SoundsEffect.match_point), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                                        player.m_213846_(Component.m_237115_("commands.matchpoint"));
                                    }
                                }
                            }
                        }
                    } else if (GameTimer == -1) {
                        for(ServerPlayer player : LockoutBingoSetup.currentPlayers.values()) {
                            if (LockoutBingoSetup.team1.contains(player.m_5446_().getString()) || LockoutBingoSetup.team2.contains(player.m_5446_().getString())) {
                                Networking.INSTANCE.sendTo(new PacketLockoutTimerUpdate(InGameTime), player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                            }
                        }
                    }
                }
            }

            if (cycleTick != 60) {
                ++cycleTick;
                return;
            }

            cycleTick = 0;
            LockoutBingoClientInfo.cycleBingoInventory();
        }

    }

    private static void performTrackAction(Player player) {
        String player_name = player.m_5446_().getString();
        ServerPlayer tracking = null;
        if (LockoutBingoSetup.team1.contains(player_name) || LockoutBingoSetup.team2.contains(player_name)) {
            if (!LockoutBingoSetup.trackingMap.containsKey(player_name)) {
                LockoutBingoSetup.reinitSinglePlayer(player_name);
            }

            String trackingName = (String)LockoutBingoSetup.trackingMap.get(player_name);
            tracking = (ServerPlayer)LockoutBingoSetup.currentPlayers.get(trackingName);
            if (tracking != null) {
                ItemStack compassItem = null;

                for(int i = 0; i < player.m_150109_().f_35974_.size(); ++i) {
                    if (((ItemStack)player.m_150109_().f_35974_.get(i)).m_41720_() instanceof CompassItem) {
                        compassItem = (ItemStack)player.m_150109_().f_35974_.get(i);
                        break;
                    }
                }

                if (compassItem == null && ((ItemStack)player.m_150109_().f_35976_.get(0)).m_41720_() instanceof CompassItem) {
                    compassItem = (ItemStack)player.m_150109_().f_35976_.get(0);
                }

                if (compassItem == null && player.f_36096_.m_142621_().m_41720_() instanceof CompassItem) {
                    compassItem = player.f_36096_.m_142621_();
                }

                if (compassItem == null) {
                    compassItem = new ItemStack(Items.f_42522_, 1);
                    if (!player.m_36356_(compassItem)) {
                        return;
                    }
                }

                CompoundTag p_234669_3_ = compassItem.m_41784_();
                BlockPos p_234669_2_ = tracking.m_20183_();
                ResourceKey<Level> p_234669_1_ = tracking.f_19853_.m_46472_();
                p_234669_3_.m_128359_("PlayerTracking", trackingName);
                p_234669_3_.m_128365_("LodestonePos", NbtUtils.m_129224_(p_234669_2_));
                DataResult var10000 = Level.f_46427_.encodeStart(NbtOps.f_128958_, p_234669_1_);
                Logger var10001 = LockoutBingoMod.LOGGER;
                Objects.requireNonNull(var10001);
                var10000.resultOrPartial(var10001::error).ifPresent((p_40731_) -> p_234669_3_.m_128365_("LodestoneDimension", p_40731_));
                if (!p_234669_3_.m_128471_("LodestoneTracked")) {
                    p_234669_3_.m_128379_("LodestoneTracked", true);
                }

                if (!compassItem.m_41786_().getString().equals("Now Tracking: " + tracking.m_5446_().getString())) {
                    compassItem.m_41714_(Component.m_237113_("Now Tracking: " + tracking.m_5446_().getString()));
                }

            }
        }
    }

    public static class Position {
        double x;
        double y;
        double z;

        public Position(double x, double y, double z) {
            this.x = Math.floor(x);
            this.y = Math.floor(y);
            this.z = Math.floor(z);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            } else if (o == null) {
                return false;
            } else if (this.getClass() != o.getClass()) {
                return false;
            } else {
                Position position = (Position)o;
                if (this.x != position.x && this.x != position.x - (double)1.0F && this.x != position.x + (double)1.0F || this.z != position.z && this.z != position.z - (double)1.0F && this.z != position.z + (double)1.0F || this.y != position.y && this.y != position.y - (double)1.0F && this.y != position.y + (double)1.0F) {
                    return Objects.equals(this.x, position.x) && Objects.equals(this.y, position.y) && Objects.equals(this.z, position.z);
                } else {
                    return true;
                }
            }
        }
    }
}
