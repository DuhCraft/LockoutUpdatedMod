package com.atsign.lockoutbingomod.core;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import com.atsign.lockoutbingomod.network.PacketLockoutBingoInfo;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagLocationArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;

public class LockoutBingoSetup {
    public static ArrayList<LockoutGoal> boardGoals = new ArrayList();
    public static ArrayList<PacketLockoutBingoInfo.GoalTuple> boardGoalsCompletion = new ArrayList();
    public static CommandSourceStack mcCommandSource;
    public static long seed;
    public static long searchSeed;
    public static ArrayList<AdvancementGoal> advancementGoals = new ArrayList();
    public static ArrayList<TameGoal> tameGoals = new ArrayList();
    public static Hashtable<Item, Integer> itemGoals = new Hashtable();
    public static Hashtable<Item, Integer> item64Goals = new Hashtable();
    public static Hashtable<DamageSource, Integer> diedGoals = new Hashtable();
    public static Hashtable<String, Integer> diedEntityGoals = new Hashtable();
    public static boolean checkMagid = false;
    public static int magicGoalIndex = -1;
    public static Hashtable<MobEffect, Integer> potionBrewGoals = new Hashtable();
    public static boolean checkLingering = false;
    public static int lingeringGoalIndex = -1;
    public static boolean checkPlayerKillPlayer = false;
    public static int pkpGoalIndex = -1;
    public static Hashtable<String, Integer> killEntityGoals = new Hashtable();
    public static ArrayList<BreedGoal> breedGoals = new ArrayList();
    public static Hashtable<MobEffect, Integer> effectGoals = new Hashtable();
    public static ArrayList<Hashtable<Item, Integer>> itemGroupGoals = new ArrayList();
    public static ArrayList<ArrayList<Boolean>> itemGroupGoalTracker = new ArrayList();
    public static ArrayList<Hashtable<Item, Integer>> armorGroupGoals = new ArrayList();
    public static ArrayList<ArrayList<Boolean>> armorGroupGoalTracker = new ArrayList();
    public static ArrayList<Hashtable<Item, Integer>> armorGoals = new ArrayList();
    public static ArrayList<Hashtable<Item, Tuple<DyeItem, Integer>>> dyedLeatherGoal = new ArrayList();
    public static Hashtable<Block, Integer> miningGoals = new Hashtable();
    public static Hashtable<String, Integer> rideEntityGoals = new Hashtable();
    public static Hashtable<Item, Integer> eatingGoals = new Hashtable();
    public static Hashtable<String, Integer> biomeGoals = new Hashtable();
    public static Hashtable<String, Integer> useSoundGoals = new Hashtable();
    public static ArrayList<String> discList = new ArrayList();
    public static boolean checkOpponentDies = false;
    public static int opponentDiesIndex = -1;
    public static Hashtable<DamageSource, Integer> opponentDamageGoals = new Hashtable();
    public static Hashtable<Item, Integer> opponentItemGoals = new Hashtable();
    public static ArrayList<Item> opponenetSeedList = new ArrayList();
    public static boolean checkOpponentJumps = false;
    public static int opponentJumpsIndex = -1;
    public static boolean checkSnowballHit = false;
    public static int snowballHitIndex = -1;
    public static boolean checkSleepAlone = false;
    public static int sleepAloneIndex = -1;
    public static boolean checkChickenEggSpawn = false;
    public static int chickenEggSpawnIndex = -1;
    public static boolean checkHunger = false;
    public static int hungerIndex = -1;
    public static boolean checkBedrock = false;
    public static int bedrockIndex = -1;
    public static boolean checkSkyLimit = false;
    public static int skyLimitIndex = -1;
    public static boolean checkLevel15 = false;
    public static int level15Index = -1;
    public static boolean checkGlowSign = false;
    public static int glowSignIndex = -1;
    public static boolean checkArmorStand = false;
    public static int armorStandIndex = -1;
    public static boolean checkCarvedPumpkin = false;
    public static int carvedPumpkinIndex = -1;
    public static boolean checkBreed4 = false;
    public static int breed4Index = -1;
    public static boolean checkBreed6 = false;
    public static int breed6Index = -1;
    public static boolean checkBreed8 = false;
    public static int breed8Index = -1;
    public static boolean checkKill7UniqueHostiles = false;
    public static int kill7UniqueHostilesIndex = -1;
    public static boolean checkKill10UniqueHostiles = false;
    public static int kill10UniqueHostilesIndex = -1;
    public static boolean checkKill15UniqueHostiles = false;
    public static int kill15UniqueHostilesIndex = -1;
    public static boolean checkKill30UndeadMobs = false;
    public static int kill30UndeadMobsIndex = -1;
    public static boolean checkKill20Arthropods = false;
    public static int kill20ArthropodsIndex = -1;
    public static boolean checkEat5 = false;
    public static int eat5Index = -1;
    public static boolean checkEat10 = false;
    public static int eat10Index = -1;
    public static boolean checkEat20 = false;
    public static int eat20Index = -1;
    public static boolean checkGet3StatusEffects = false;
    public static int get3StatusEffectsIndex = -1;
    public static boolean checkGet6StatusEffects = false;
    public static int get6StatusEffectsIndex = -1;
    public static boolean checkRemoveStatusEffect = false;
    public static int removeStatusEffectIndex = -1;
    public static boolean checkUseCauldron = false;
    public static int useCauldronIndex = -1;
    public static boolean checkOpponentDies3 = false;
    public static int opponentDies3Index = -1;
    public static boolean checkOpponentCatchesFire = false;
    public static int opponentCatchesFireIndex = -1;
    public static boolean checkOpponentTouchesWater = false;
    public static int opponentTouchesWaterIndex = -1;
    public static boolean checkOpponentHitsYou = false;
    public static int opponentHitsYouIndex = -1;
    public static boolean checkOpponentTakes100 = false;
    public static int opponentTakes100Index = -1;
    public static boolean checkLevel30 = false;
    public static int level30Index = -1;
    public static boolean checkDetonateTNT = false;
    public static int detonateTNTIndex = -1;
    public static boolean checkEnrageZombiePiglin = false;
    public static int enrageZombiePiglinIndex = -1;
    public static boolean checkTake200Damage = false;
    public static int take200DamageIndex = -1;
    public static boolean checkKill100Mobs = false;
    public static int kill100MobsIndex = -1;
    public static boolean checkDeal400Damage = false;
    public static int deal400DamageIndex = -1;
    public static boolean checkSprint1km = false;
    public static int sprint1kmIndex = -1;
    public static boolean checkGet15Adv = false;
    public static int get15AdvIndex = -1;
    public static boolean checkGet25Adv = false;
    public static int get25AdvIndex = -1;
    public static boolean checkGet35Adv = false;
    public static int get35AdvIndex = -1;
    public static boolean checkObtainMoreKelp = false;
    public static int obtainMoreKelpIndex = -1;
    public static boolean checkObtainMoreHopper = false;
    public static int obtainMoreHopperIndex = -1;
    public static boolean checkMoreLevels = false;
    public static int moreLevelsIndex = -1;
    public static boolean checkDieVines = false;
    public static int dieVinesIndex = -1;
    public static boolean checkFillInventory = false;
    public static int fillInventoryIndex = -1;
    public static ArrayList<String> team1 = new ArrayList();
    public static ArrayList<String> team2 = new ArrayList();
    public static int currentLinkedTeam = 0;
    public static Hashtable<String, String> trackingMap = new Hashtable();
    public static int team1Score = 0;
    public static int team2Score = 0;
    public static Hashtable<String, ServerPlayer> currentPlayers = new Hashtable();
    public static boolean test_mode = false;
    public static boolean blackout_mode = false;
    public static Hashtable<String, Boolean> biome_structure_cache = new Hashtable();
    public static boolean doBiomeStructureCheck = true;
    private static int num_of_diff_one = 0;
    public static int limit_num_of_diff_one = 10;
    private static int num_of_diff_two = 0;
    public static int limit_num_of_diff_two = 16;
    private static int num_of_diff_three = 0;
    public static int limit_num_of_diff_three = 7;
    private static int num_of_end = 0;
    private static final int limit_num_of_end = 2;
    private static int num_of_tool = 0;
    private static final int limit_num_of_tool = 2;
    private static int num_of_diamond = 0;
    private static final int limit_num_of_diamond = 1;
    private static int num_of_netherite = 0;
    private static final int limit_num_of_netherite = 1;
    private static int num_of_armor = 0;
    private static final int limit_num_of_armor = 1;
    private static int num_of_armor_special = 0;
    private static final int limit_num_of_armor_special = 1;
    private static int num_of_tame = 0;
    private static final int limit_num_of_tame = 2;
    private static int num_of_kill_1_mob = 0;
    private static final int limit_num_of_kill_1_mob = 3;
    private static int num_of_kill_x_hostile = 0;
    private static final int limit_num_of_kill_x_hostile = 2;
    private static int num_of_kill_type = 0;
    private static final int limit_num_of_kill_type = 1;
    private static int num_of_fortress = 0;
    private static final int limit_num_of_fortress = 3;
    private static int num_of_horse = 0;
    private static final int limit_num_of_horse = 1;
    private static int num_of_breed1 = 0;
    private static final int limit_num_of_breed1 = 1;
    private static int num_of_breed2 = 0;
    private static final int limit_num_of_breed2 = 2;
    private static int num_of_breedx = 0;
    private static final int limit_num_of_breedx = 1;
    private static int num_of_ride = 0;
    private static final int limit_num_of_ride = 2;
    private static int num_of_eat = 0;
    private static final int limit_num_of_eat = 3;
    private static int num_of_eatx = 0;
    private static final int limit_num_of_eatx = 2;
    private static int num_of_effect = 0;
    private static final int limit_num_of_effect = 4;
    private static int num_of_effect_x = 0;
    private static final int limit_num_of_effect_x = 1;
    private static int num_of_die = 0;
    private static final int limit_num_of_die = 3;
    private static int num_of_biome = 0;
    private static final int limit_num_of_biome = 1;
    private static int num_of_end_structure = 0;
    private static final int limit_num_of_end_structure = 1;
    private static int num_of_opponent_hard = 0;
    private static final int limit_num_of_opponent_hard = 1;
    private static int num_of_opponent_hurts = 0;
    private static final int limit_num_of_opponent_hurts = 1;
    private static int num_of_player_dies = 0;
    private static final int limit_num_of_player_dies = 1;
    private static int num_of_damage = 0;
    private static final int limit_num_of_damage = 1;
    private static int num_of_advancement = 0;
    private static final int limit_num_of_advancement = 1;
    private static int num_of_village = 0;
    private static final int limit_num_of_village = 3;
    private static int num_of_monument = 0;
    private static final int limit_num_of_monument = 1;
    private static int num_of_redstone = 0;
    private static final int limit_num_of_redstone = 2;
    private static int num_of_tnt = 0;
    private static final int limit_num_of_tnt = 1;
    private static int num_of_obtain_more = 0;
    private static final int limit_num_of_obtain_more = 1;
    private static int num_of_strider = 0;
    private static final int limit_num_of_strider = 1;
    private static int num_of_sheep = 0;
    private static final int limit_num_of_sheep = 1;
    private static int num_of_punch = 0;
    private static final int limit_num_of_punch = 1;
    private static int num_of_collect = 0;
    private static final int limit_num_of_collect = 2;
    private static int num_of_poison = 0;
    private static final int limit_num_of_poison = 1;
    private static int num_of_dragon = 0;
    private static final int limit_num_of_dragon = 1;
    private static int num_of_kill100 = 0;
    private static final int limit_num_of_kill100 = 1;
    private static int num_of_seed = 0;
    private static final int limit_num_of_seed = 1;
    public static final DynamicCommandExceptionType ERROR_INVALID_BIOME = new DynamicCommandExceptionType((p_137850_) -> Component.m_237110_("commands.locatebiome.invalid", new Object[]{p_137850_}));

    public LockoutBingoSetup() {
    }

    public static void buildSpecialGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case enter_nether:
            case enter_end:
            case distract_piglin:
            case this_boat_has_legs_strider:
            case find_fortress:
            case find_bastion:
            case find_stronghold:
            case find_end_city:
            case trade_with_villager:
            case use_enchanting_table:
            case get_any_spyglass_advancement:
            case get_sniper_duel:
            case get_bullseye:
            case use_a_brewing_stand:
            case charge_respawn_anchor_to_max:
            case visit_all_nether_biomes:
                advancementGoals.add(new AdvancementGoal(task, goal_index));
                break;
            case tame_cat:
            case tame_horse:
            case tame_parrot:
            case tame_wolf:
                tameGoals.add(new TameGoal(task, goal_index));
                break;
            case breed_cow:
            case breed_sheep:
            case breed_chicken:
            case breed_pig:
            case breed_horse:
            case breed_hoglin:
            case breed_ocelot:
            case breed_rabbit:
            case breed_fox:
            case breed_strider:
            case breed_goat:
                breedGoals.add(new BreedGoal(task, goal_index));
        }

    }

    public static void buildItemGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case obtain_red_nether_brick_stairs:
                itemGoals.put(Items.f_42376_, goal_index);
                break;
            case obtain_sea_lantern:
                itemGoals.put(Items.f_42251_, goal_index);
                break;
            case obtain_bookshelf:
                itemGoals.put(Items.f_41997_, goal_index);
                break;
            case obtain_mossy_stone_brick_wall:
                itemGoals.put(Items.f_42071_, goal_index);
                break;
            case obtain_scaffolding:
                itemGoals.put(Items.f_42340_, goal_index);
                break;
            case obtain_end_crystal:
                itemGoals.put(Items.f_42729_, goal_index);
                break;
            case obtain_bell:
                itemGoals.put(Items.f_42777_, goal_index);
                break;
            case obtain_slime_block:
                itemGoals.put(Items.f_42204_, goal_index);
                break;
            case obtain_soul_lantern:
                itemGoals.put(Items.f_42779_, goal_index);
                break;
            case obtain_honey_bottle:
                itemGoals.put(Items.f_42787_, goal_index);
                break;
            case obtain_ancient_debris:
                itemGoals.put(Items.f_42792_, goal_index);
                break;
            case obtain_cake:
                itemGoals.put(Items.f_42502_, goal_index);
                break;
            case obtain_ender_chest:
                itemGoals.put(Items.f_42108_, goal_index);
                break;
            case obtain_heart_of_the_sea:
                itemGoals.put(Items.f_42716_, goal_index);
                break;
            case obtain_wither_skeleton_skull:
                itemGoals.put(Items.f_42679_, goal_index);
                break;
            case obtain_lodestone:
                itemGoals.put(Items.f_42790_, goal_index);
                break;
            case obtain_end_rod:
                itemGoals.put(Items.f_42001_, goal_index);
                break;
            case obtain_sponge:
                itemGoals.put(Items.f_41902_, goal_index);
                break;
            case obtain_mushroom_stem:
                itemGoals.put(Items.f_42024_, goal_index);
                break;
            case obtain_dragon_egg:
                itemGoals.put(Items.f_42104_, goal_index);
                break;
            case obtain_bottle_o_enchanting:
                itemGoals.put(Items.f_42612_, goal_index);
                break;
            case obtain_bucket_of_tropical_fish:
                itemGoals.put(Items.f_42459_, goal_index);
                break;
            case write_book:
                itemGoals.put(Items.f_42615_, goal_index);
                break;
            case brew_potion_of_lingering:
                itemGoals.put(Items.f_42739_, goal_index);
                break;
            case obtain_flowering_azalea:
                itemGoals.put(Items.f_151013_, goal_index);
                break;
            case obtain_a_powder_snow_bucket:
                itemGoals.put(Items.f_151055_, goal_index);
                break;
            case obtain_tnt:
                itemGoals.put(Items.f_41996_, goal_index);
                break;
            case obtain_cobweb:
                itemGoals.put(Items.f_41863_, goal_index);
                break;
            case obtain_goat_horn:
                itemGoals.put(Items.f_220219_, goal_index);
                break;
            case obtain_mud_brick_wall:
                itemGoals.put(Items.f_220190_, goal_index);
                break;
            case obtain_daylight_detector:
                itemGoals.put(Items.f_42152_, goal_index);
                break;
            case obtain_redstone_repeater:
                itemGoals.put(Items.f_42350_, goal_index);
                break;
            case obtain_redstone_comparator:
                itemGoals.put(Items.f_42351_, goal_index);
                break;
            case obtain_observer:
                itemGoals.put(Items.f_42264_, goal_index);
                break;
            case obtain_activator_rail:
                itemGoals.put(Items.f_42161_, goal_index);
                break;
            case obtain_detector_rail:
                itemGoals.put(Items.f_41861_, goal_index);
                break;
            case obtain_powered_rail:
                itemGoals.put(Items.f_41860_, goal_index);
                break;
            case obtain_dispenser:
                itemGoals.put(Items.f_41855_, goal_index);
                break;
            case obtain_piston:
                itemGoals.put(Items.f_41869_, goal_index);
                break;
            case obtain_glazed_terracotta:
                switch (((LockoutGoal)boardGoals.get(goal_index)).getTaskName()) {
                    case "Obtain White Glazed Terracotta":
                        itemGoals.put(Items.f_42230_, goal_index);
                        return;
                    case "Obtain Orange Glazed Terracotta":
                        itemGoals.put(Items.f_42231_, goal_index);
                        return;
                    case "Obtain Magenta Glazed Terracotta":
                        itemGoals.put(Items.f_42232_, goal_index);
                        return;
                    case "Obtain Light Blue Glazed Terracotta":
                        itemGoals.put(Items.f_42233_, goal_index);
                        return;
                    case "Obtain Yellow Glazed Terracotta":
                        itemGoals.put(Items.f_42234_, goal_index);
                        return;
                    case "Obtain Lime Glazed Terracotta":
                        itemGoals.put(Items.f_42235_, goal_index);
                        return;
                    case "Obtain Pink Glazed Terracotta":
                        itemGoals.put(Items.f_42236_, goal_index);
                        return;
                    case "Obtain Gray Glazed Terracotta":
                        itemGoals.put(Items.f_42237_, goal_index);
                        return;
                    case "Obtain Light Gray Glazed Terracotta":
                        itemGoals.put(Items.f_42238_, goal_index);
                        return;
                    case "Obtain Cyan Glazed Terracotta":
                        itemGoals.put(Items.f_42239_, goal_index);
                        return;
                    case "Obtain Purple Glazed Terracotta":
                        itemGoals.put(Items.f_42240_, goal_index);
                        return;
                    case "Obtain Blue Glazed Terracotta":
                        itemGoals.put(Items.f_42241_, goal_index);
                        return;
                    case "Obtain Brown Glazed Terracotta":
                        itemGoals.put(Items.f_42242_, goal_index);
                        return;
                    case "Obtain Green Glazed Terracotta":
                        itemGoals.put(Items.f_42243_, goal_index);
                        return;
                    case "Obtain Red Glazed Terracotta":
                        itemGoals.put(Items.f_42244_, goal_index);
                        return;
                    case "Obtain Black Glazed Terracotta":
                        itemGoals.put(Items.f_42245_, goal_index);
                        return;
                    default:
                        return;
                }
            case obtain_64_x_wool:
                int wool_color_index = -1;

                for(int i = 0; i < LockoutGoals.colors.length; ++i) {
                    if (((LockoutGoal)boardGoals.get(goal_index)).getTaskName().equals(String.format("Obtain 64 %s Wool", LockoutGoals.colors[i]))) {
                        wool_color_index = i;
                        break;
                    }
                }

                item64Goals.put(LockoutGoals.wool_map[wool_color_index], goal_index);
                break;
            case obtain_64_x_concrete:
                int concrete_color_index = -1;

                for(int i = 0; i < LockoutGoals.colors.length; ++i) {
                    if (((LockoutGoal)boardGoals.get(goal_index)).getTaskName().equals(String.format("Obtain 64 %s Concrete", LockoutGoals.colors[i]))) {
                        concrete_color_index = i;
                        break;
                    }
                }

                item64Goals.put(LockoutGoals.concrete_map[concrete_color_index], goal_index);
                break;
            case obtain_64_of_one_item_block:
                item64Goals.put(Items.f_41829_, goal_index);
        }

    }

    public static void buildDiedGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case die_by_cactus:
                diedGoals.put(DamageSource.f_19314_, goal_index);
                break;
            case die_by_berry_bush:
                diedGoals.put(DamageSource.f_19325_, goal_index);
                break;
            case die_by_anvil:
                diedGoals.put(DamageSource.f_19321_, goal_index);
                break;
            case die_to_falling_stalactite:
                diedGoals.put(DamageSource.f_146702_, goal_index);
                break;
            case die_to_intentional_game_design:
                diedEntityGoals.put("intentional_game_design", goal_index);
                break;
            case die_to_iron_golem:
                diedEntityGoals.put("iron_golem", goal_index);
                break;
            case detonate_tnt_minecart:
                diedEntityGoals.put("tnt_minecart", goal_index);
                break;
            case die_by_magic:
                checkMagid = true;
                magicGoalIndex = goal_index;
                break;
            case die_by_llama:
                diedEntityGoals.put("llama", goal_index);
                break;
            case die_by_bee_sting:
                diedEntityGoals.put("bee", goal_index);
                break;
            case die_by_firework_rocket:
                diedEntityGoals.put("firework", goal_index);
        }

    }

    public static void buildPotionBrewGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case brew_potion_of_healing -> potionBrewGoals.put(MobEffects.f_19601_, goal_index);
            case brew_potion_of_leaping -> potionBrewGoals.put(MobEffects.f_19603_, goal_index);
            case brew_potion_of_swiftness -> potionBrewGoals.put(MobEffects.f_19596_, goal_index);
            case brew_potion_of_invisibility -> potionBrewGoals.put(MobEffects.f_19609_, goal_index);
            case brew_potion_of_water_breathing -> potionBrewGoals.put(MobEffects.f_19608_, goal_index);
        }

    }

    public static void buildKillEntityGoals(LockoutGoal.Tasks task, String task_name, int goal_index) {
        switch (task) {
            case kill_witch:
                killEntityGoals.put("witch", goal_index);
                break;
            case kill_zombie_villager:
                killEntityGoals.put("zombie_villager", goal_index);
                break;
            case kill_stray:
                killEntityGoals.put("stray", goal_index);
                break;
            case kill_zoglin:
                killEntityGoals.put("zoglin", goal_index);
                break;
            case kill_silverfish:
                killEntityGoals.put("silverfish", goal_index);
                break;
            case kill_guardian:
                killEntityGoals.put("guardian", goal_index);
                break;
            case kill_ghast:
                killEntityGoals.put("ghast", goal_index);
                break;
            case kill_snow_golem:
                killEntityGoals.put("snow_golem", goal_index);
                break;
            case kill_ender_dragon:
                killEntityGoals.put("ender_dragon", goal_index);
                break;
            case kill_elder_guardian:
                killEntityGoals.put("elder_guardian", goal_index);
                break;
            case kill_endermite:
                killEntityGoals.put("endermite", goal_index);
                break;
            case kill_colored_sheep:
                killEntityGoals.put(task_name, goal_index);
                break;
            case kill_the_other_player:
                checkPlayerKillPlayer = true;
                pkpGoalIndex = goal_index;
        }

    }

    public static void buildEffectGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case get_nausea -> effectGoals.put(MobEffects.f_19604_, goal_index);
            case get_jump_boost -> effectGoals.put(MobEffects.f_19603_, goal_index);
            case get_absorption -> effectGoals.put(MobEffects.f_19617_, goal_index);
            case get_levitation -> effectGoals.put(MobEffects.f_19620_, goal_index);
            case get_glowing -> effectGoals.put(MobEffects.f_19619_, goal_index);
            case get_mining_fatigue -> effectGoals.put(MobEffects.f_19599_, goal_index);
            case get_bad_omen -> effectGoals.put(MobEffects.f_19594_, goal_index);
            case get_weakness -> effectGoals.put(MobEffects.f_19613_, goal_index);
            case get_poisoned -> effectGoals.put(MobEffects.f_19614_, goal_index);
        }

    }

    public static void buildItemGroupGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case full_set_wooden_tools:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42420_, 0);
                temp.put(Items.f_42423_, 1);
                temp.put(Items.f_42422_, 2);
                temp.put(Items.f_42424_, 3);
                temp.put(Items.f_42421_, 4);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case full_set_stone_tools:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42425_, 0);
                temp.put(Items.f_42428_, 1);
                temp.put(Items.f_42427_, 2);
                temp.put(Items.f_42429_, 3);
                temp.put(Items.f_42426_, 4);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case full_set_iron_tools:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42383_, 0);
                temp.put(Items.f_42386_, 1);
                temp.put(Items.f_42385_, 2);
                temp.put(Items.f_42387_, 3);
                temp.put(Items.f_42384_, 4);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case full_set_gold_tools:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42430_, 0);
                temp.put(Items.f_42433_, 1);
                temp.put(Items.f_42432_, 2);
                temp.put(Items.f_42434_, 3);
                temp.put(Items.f_42431_, 4);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case full_set_diamond_tools:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42388_, 0);
                temp.put(Items.f_42391_, 1);
                temp.put(Items.f_42390_, 2);
                temp.put(Items.f_42392_, 3);
                temp.put(Items.f_42389_, 4);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_shovel:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42421_, 0);
                temp.put(Items.f_42426_, 1);
                temp.put(Items.f_42384_, 2);
                temp.put(Items.f_42431_, 3);
                temp.put(Items.f_42394_, 4);
                temp.put(Items.f_42389_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_hoe:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42424_, 0);
                temp.put(Items.f_42429_, 1);
                temp.put(Items.f_42387_, 2);
                temp.put(Items.f_42434_, 3);
                temp.put(Items.f_42397_, 4);
                temp.put(Items.f_42392_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_axe:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42423_, 0);
                temp.put(Items.f_42428_, 1);
                temp.put(Items.f_42386_, 2);
                temp.put(Items.f_42396_, 3);
                temp.put(Items.f_42391_, 4);
                temp.put(Items.f_42433_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_pickaxe:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42422_, 0);
                temp.put(Items.f_42427_, 1);
                temp.put(Items.f_42385_, 2);
                temp.put(Items.f_42395_, 3);
                temp.put(Items.f_42390_, 4);
                temp.put(Items.f_42432_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_sword:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42420_, 0);
                temp.put(Items.f_42425_, 1);
                temp.put(Items.f_42383_, 2);
                temp.put(Items.f_42393_, 3);
                temp.put(Items.f_42388_, 4);
                temp.put(Items.f_42430_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_sapling:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42799_, 0);
                temp.put(Items.f_42800_, 1);
                temp.put(Items.f_42801_, 2);
                temp.put(Items.f_41826_, 3);
                temp.put(Items.f_41827_, 4);
                temp.put(Items.f_41828_, 5);
                temp.put(Items.f_220175_, 6);
                temp.put(Items.f_41926_, 5);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_6_unique_flowers:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_41948_, 0);
                temp.put(Items.f_41939_, 1);
                temp.put(Items.f_41940_, 2);
                temp.put(Items.f_41941_, 3);
                temp.put(Items.f_41942_, 4);
                temp.put(Items.f_41943_, 5);
                temp.put(Items.f_41944_, 6);
                temp.put(Items.f_41945_, 7);
                temp.put(Items.f_41946_, 8);
                temp.put(Items.f_41947_, 9);
                temp.put(Items.f_41949_, 10);
                temp.put(Items.f_41950_, 11);
                temp.put(Items.f_41951_, 12);
                temp.put(Items.f_42206_, 13);
                temp.put(Items.f_42207_, 14);
                temp.put(Items.f_42208_, 15);
                temp.put(Items.f_42209_, 16);
                temp.put(Items.f_41926_, 6);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_seed:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42404_, 0);
                temp.put(Items.f_42733_, 1);
                temp.put(Items.f_42578_, 2);
                temp.put(Items.f_42577_, 3);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_of_raw_ore:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_150995_, 0);
                temp.put(Items.f_150996_, 1);
                temp.put(Items.f_150997_, 2);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case obtain_every_type_horse_armor:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42654_, 0);
                temp.put(Items.f_42651_, 1);
                temp.put(Items.f_42652_, 2);
                temp.put(Items.f_42653_, 3);
                itemGroupGoals.add(temp);
                itemGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
        }

    }

    public static void buildArmorGroupGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case leather_armor_set:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42407_, 0);
                temp.put(Items.f_42408_, 1);
                temp.put(Items.f_42462_, 2);
                temp.put(Items.f_42463_, 3);
                armorGroupGoals.add(temp);
                armorGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case gold_armor_set:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42476_, 0);
                temp.put(Items.f_42477_, 1);
                temp.put(Items.f_42478_, 2);
                temp.put(Items.f_42479_, 3);
                armorGroupGoals.add(temp);
                armorGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case iron_armor_set:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42468_, 0);
                temp.put(Items.f_42469_, 1);
                temp.put(Items.f_42470_, 2);
                temp.put(Items.f_42471_, 3);
                armorGroupGoals.add(temp);
                armorGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
                break;
            case diamond_armor_set:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_41829_, goal_index);
                temp.put(Items.f_42472_, 0);
                temp.put(Items.f_42473_, 1);
                temp.put(Items.f_42474_, 2);
                temp.put(Items.f_42475_, 3);
                armorGroupGoals.add(temp);
                armorGroupGoalTracker.add(new ArrayList<Boolean>() {
                    {
                        this.add(false);
                        this.add(false);
                        this.add(false);
                        this.add(false);
                    }
                });
        }

    }

    public static void buildArmorGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case one_piece_netherite_armor:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_42480_, goal_index);
                temp.put(Items.f_42481_, goal_index);
                temp.put(Items.f_42482_, goal_index);
                temp.put(Items.f_42483_, goal_index);
                armorGoals.add(temp);
                break;
            case one_piece_chain_armor:
                Hashtable<Item, Integer> temp = new Hashtable();
                temp.put(Items.f_42464_, goal_index);
                temp.put(Items.f_42465_, goal_index);
                temp.put(Items.f_42466_, goal_index);
                temp.put(Items.f_42467_, goal_index);
                armorGoals.add(temp);
        }

    }

    public static void buildDyedGoals(LockoutGoal.Tasks task, String task_name, int goal_index) {
        switch (task) {
            case wear_colored_leather_armor:
                Hashtable<Item, Tuple<DyeItem, Integer>> temp = new Hashtable();
                int color_index = goal_index;
                int armor_index = goal_index;

                for(int i = 0; i < LockoutGoals.colors.length; ++i) {
                    if (task_name.contains(String.format("Wear %s Colored Leather", LockoutGoals.colors[i]))) {
                        color_index = i;
                    }
                }

                for(int i = 0; i < LockoutGoals.armor_pieces.length; ++i) {
                    if (task_name.contains(LockoutGoals.armor_pieces[i])) {
                        armor_index = i;
                    }
                }

                temp.put(LockoutGoals.armor_map[armor_index], new Tuple((DyeItem)LockoutGoals.dye_item_map[color_index], goal_index));
                dyedLeatherGoal.add(temp);
            default:
        }
    }

    public static void buildMiningGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case mine_diamond_ore:
                miningGoals.put(Blocks.f_50089_, goal_index);
                miningGoals.put(Blocks.f_152474_, goal_index);
                break;
            case mine_emerald_ore:
                miningGoals.put(Blocks.f_50264_, goal_index);
                miningGoals.put(Blocks.f_152479_, goal_index);
                break;
            case mine_mob_spawner:
                miningGoals.put(Blocks.f_50085_, goal_index);
                break;
            case mine_turtle_egg:
                miningGoals.put(Blocks.f_50578_, goal_index);
        }

    }

    public static void buildRideGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case ride_pig_with_carrot_on_stick -> rideEntityGoals.put("pig", goal_index);
            case ride_horse_with_saddle -> rideEntityGoals.put("horse", goal_index);
            case ride_minecart -> rideEntityGoals.put("minecart", goal_index);
        }

    }

    public static void buildEatingGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case eat_pumpkin_pie -> eatingGoals.put(Items.f_42687_, goal_index);
            case eat_rabbit_stew -> eatingGoals.put(Items.f_42699_, goal_index);
            case eat_suspicious_stew -> eatingGoals.put(Items.f_42718_, goal_index);
            case eat_cookie -> eatingGoals.put(Items.f_42572_, goal_index);
            case eat_chorus_fruit -> eatingGoals.put(Items.f_42730_, goal_index);
            case eat_poisonous_potato -> eatingGoals.put(Items.f_42675_, goal_index);
            case eat_a_glow_berry -> eatingGoals.put(Items.f_151079_, goal_index);
        }

    }

    public static void buildBiomeGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case find_ice_spike_biome:
                biomeGoals.put("ICE_SPIKES".toLowerCase(), goal_index);
                break;
            case find_mushroom_biome:
                biomeGoals.put("MUSHROOM_FIELDS".toLowerCase(), goal_index);
                break;
            case find_badlands_biome:
                biomeGoals.put("ERODED_BADLANDS".toLowerCase(), goal_index);
                biomeGoals.put("BADLANDS".toLowerCase(), goal_index);
                biomeGoals.put("WOODED_BADLANDS".toLowerCase(), goal_index);
        }

    }

    public static void buildUseSoundGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case use_smithing_table:
                useSoundGoals.put("BLOCK_SMITHING_TABLE_USE", goal_index);
                break;
            case use_anvil:
                useSoundGoals.put("BLOCK_ANVIL_USE", goal_index);
                break;
            case use_composter:
                useSoundGoals.put("BLOCK_COMPOSTER_EMPTY", goal_index);
                break;
            case use_jukebox_to_play_music:
                useSoundGoals.put("MUSIC_DISC_13", goal_index);
                useSoundGoals.put("MUSIC_DISC_CAT", goal_index);
                useSoundGoals.put("MUSIC_DISC_BLOCKS", goal_index);
                useSoundGoals.put("MUSIC_DISC_CHIRP", goal_index);
                useSoundGoals.put("MUSIC_DISC_FAR", goal_index);
                useSoundGoals.put("MUSIC_DISC_MALL", goal_index);
                useSoundGoals.put("MUSIC_DISC_MELLOHI", goal_index);
                useSoundGoals.put("MUSIC_DISC_STAL", goal_index);
                useSoundGoals.put("MUSIC_DISC_STRAD", goal_index);
                useSoundGoals.put("MUSIC_DISC_WARD", goal_index);
                useSoundGoals.put("MUSIC_DISC_11", goal_index);
                useSoundGoals.put("MUSIC_DISC_WAIT", goal_index);
                useSoundGoals.put("MUSIC_DISC_PIGSTEP", goal_index);
                useSoundGoals.put("MUSIC_DISC_OTHERSIDE", goal_index);
                discList.add("MUSIC_DISC_13");
                discList.add("MUSIC_DISC_CAT");
                discList.add("MUSIC_DISC_BLOCKS");
                discList.add("MUSIC_DISC_CHIRP");
                discList.add("MUSIC_DISC_FAR");
                discList.add("MUSIC_DISC_MALL");
                discList.add("MUSIC_DISC_MELLOHI");
                discList.add("MUSIC_DISC_STAL");
                discList.add("MUSIC_DISC_STRAD");
                discList.add("MUSIC_DISC_WARD");
                discList.add("MUSIC_DISC_11");
                discList.add("MUSIC_DISC_WAIT");
                discList.add("MUSIC_DISC_PIGSTEP");
                discList.add("MUSIC_DISC_OTHERSIDE");
                break;
            case use_loom_to_design_banner:
                useSoundGoals.put("UI_LOOM_TAKE_RESULT", goal_index);
        }

    }

    public static void buildOpponentDamageGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case opponent_dies:
                checkOpponentDies = true;
                opponentDiesIndex = goal_index;
                break;
            case opponent_takes_fall_damage:
                opponentDamageGoals.put(DamageSource.f_19315_, goal_index);
        }

    }

    public static void buildOpponentObtainsGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case opponent_obtains_crafting_table:
                opponentItemGoals.put(Items.f_41960_, goal_index);
                break;
            case oponnent_obtains_obsidian:
                opponentItemGoals.put(Items.f_41999_, goal_index);
                break;
            case oponnent_obtains_seeds:
                opponentItemGoals.put(Items.f_42404_, goal_index);
                opponentItemGoals.put(Items.f_42733_, goal_index);
                opponentItemGoals.put(Items.f_42578_, goal_index);
                opponentItemGoals.put(Items.f_42577_, goal_index);
                opponenetSeedList.add(Items.f_42404_);
                opponenetSeedList.add(Items.f_42733_);
                opponenetSeedList.add(Items.f_42578_);
                opponenetSeedList.add(Items.f_42577_);
                break;
            case opponent_jumps:
                checkOpponentJumps = true;
                opponentJumpsIndex = goal_index;
        }

    }

    public static void buildMiscGoals(LockoutGoal.Tasks task, int goal_index) {
        switch (task) {
            case sleep_alone_in_overworld:
                checkSleepAlone = true;
                sleepAloneIndex = goal_index;
                break;
            case spawn_a_chicken_with_an_egg:
                checkChickenEggSpawn = true;
                chickenEggSpawnIndex = goal_index;
                break;
            case empty_huger_bar:
                checkHunger = true;
                hungerIndex = goal_index;
                break;
            case reach_bedrock:
                checkBedrock = true;
                bedrockIndex = goal_index;
                break;
            case reach_sky_limit:
                checkSkyLimit = true;
                skyLimitIndex = goal_index;
                break;
            case reach_level_15:
                checkLevel15 = true;
                level15Index = goal_index;
                break;
            case use_glow_ink_on_crimson_sign:
                checkGlowSign = true;
                glowSignIndex = goal_index;
                break;
            case fill_armor_stand:
                checkArmorStand = true;
                armorStandIndex = goal_index;
                break;
            case wear_carved_pumpkin:
                checkCarvedPumpkin = true;
                carvedPumpkinIndex = goal_index;
                break;
            case breed_4_unique:
                checkBreed4 = true;
                breed4Index = goal_index;
                break;
            case breed_6_unique:
                checkBreed6 = true;
                breed6Index = goal_index;
                break;
            case breed_8_unique:
                checkBreed8 = true;
                breed8Index = goal_index;
                break;
            case opponent_is_hit_by_snowball:
                checkSnowballHit = true;
                snowballHitIndex = goal_index;
                break;
            case kill_7_unique_hostile:
                checkKill7UniqueHostiles = true;
                kill7UniqueHostilesIndex = goal_index;
                break;
            case kill_10_unique_hostile:
                checkKill10UniqueHostiles = true;
                kill10UniqueHostilesIndex = goal_index;
                break;
            case kill_15_unique_hostile:
                checkKill15UniqueHostiles = true;
                kill15UniqueHostilesIndex = goal_index;
                break;
            case kill_30_undead:
                checkKill30UndeadMobs = true;
                kill30UndeadMobsIndex = goal_index;
                break;
            case kill_20_arthropods:
                checkKill20Arthropods = true;
                kill20ArthropodsIndex = goal_index;
                break;
            case eat_5_unique_food:
                checkEat5 = true;
                eat5Index = goal_index;
                break;
            case eat_10_unique_food:
                checkEat10 = true;
                eat10Index = goal_index;
                break;
            case eat_20_unique_food:
                checkEat20 = true;
                eat20Index = goal_index;
                break;
            case get_3_status_effects_at_once:
                checkGet3StatusEffects = true;
                get3StatusEffectsIndex = goal_index;
                break;
            case get_6_status_effects_at_once:
                checkGet6StatusEffects = true;
                get6StatusEffectsIndex = goal_index;
                break;
            case remove_status_effect_with_milk:
                checkRemoveStatusEffect = true;
                removeStatusEffectIndex = goal_index;
                break;
            case use_cauldron_to_wash:
                checkUseCauldron = true;
                useCauldronIndex = goal_index;
                break;
            case opponent_dies_3_times:
                checkOpponentDies3 = true;
                opponentDies3Index = goal_index;
                break;
            case opponent_catches_fire:
                checkOpponentCatchesFire = true;
                opponentCatchesFireIndex = goal_index;
                break;
            case opponent_touches_water:
                checkOpponentTouchesWater = true;
                opponentTouchesWaterIndex = goal_index;
                break;
            case opponent_hits_you:
                checkOpponentHitsYou = true;
                opponentHitsYouIndex = goal_index;
                break;
            case opponent_takes_100_total_damage:
                checkOpponentTakes100 = true;
                opponentTakes100Index = goal_index;
                break;
            case reach_level_30:
                checkLevel30 = true;
                level30Index = goal_index;
                break;
            case enrage_zombie_piglin:
                checkEnrageZombiePiglin = true;
                enrageZombiePiglinIndex = goal_index;
                break;
            case take_200_damage:
                checkTake200Damage = true;
                take200DamageIndex = goal_index;
                break;
            case kill_100_mobs:
                checkKill100Mobs = true;
                kill100MobsIndex = goal_index;
                break;
            case deal_400_damage:
                checkDeal400Damage = true;
                deal400DamageIndex = goal_index;
                break;
            case sprint_1k:
                checkSprint1km = true;
                sprint1kmIndex = goal_index;
                break;
            case get_15_advancements:
                checkGet15Adv = true;
                get15AdvIndex = goal_index;
                break;
            case get_25_advancements:
                checkGet25Adv = true;
                get25AdvIndex = goal_index;
                break;
            case get_35_advancements:
                checkGet35Adv = true;
                get35AdvIndex = goal_index;
                break;
            case obtain_more_dried_kelp_than_opponent:
                checkObtainMoreKelp = true;
                obtainMoreKelpIndex = goal_index;
                break;
            case obtain_more_hoppers_than_opponent:
                checkObtainMoreHopper = true;
                obtainMoreHopperIndex = goal_index;
                break;
            case have_more_levels_than_opponent:
                checkMoreLevels = true;
                moreLevelsIndex = goal_index;
                break;
            case die_by_falling_off_vines:
                checkDieVines = true;
                dieVinesIndex = goal_index;
                break;
            case fill_inventory_with_unique_items:
                checkFillInventory = true;
                fillInventoryIndex = goal_index;
        }

    }

    public static void resetLimitCounts() {
        num_of_diff_one = 0;
        num_of_diff_two = 0;
        num_of_diff_three = 0;
        num_of_end = 0;
        num_of_tool = 0;
        num_of_diamond = 0;
        num_of_netherite = 0;
        num_of_armor = 0;
        num_of_armor_special = 0;
        num_of_tame = 0;
        num_of_kill_1_mob = 0;
        num_of_kill_x_hostile = 0;
        num_of_kill_type = 0;
        num_of_fortress = 0;
        num_of_horse = 0;
        num_of_breed1 = 0;
        num_of_breed2 = 0;
        num_of_breedx = 0;
        num_of_ride = 0;
        num_of_eat = 0;
        num_of_eatx = 0;
        num_of_effect = 0;
        num_of_effect_x = 0;
        num_of_die = 0;
        num_of_biome = 0;
        num_of_end_structure = 0;
        num_of_opponent_hard = 0;
        num_of_opponent_hurts = 0;
        num_of_player_dies = 0;
        num_of_damage = 0;
        num_of_advancement = 0;
        num_of_village = 0;
        num_of_monument = 0;
        num_of_redstone = 0;
        num_of_tnt = 0;
        num_of_obtain_more = 0;
        num_of_strider = 0;
        num_of_sheep = 0;
        num_of_punch = 0;
        num_of_collect = 0;
        num_of_poison = 0;
        num_of_dragon = 0;
        num_of_kill100 = 0;
        num_of_seed = 0;
        team1Score = 0;
        team2Score = 0;
    }

    public static void resetGoalChecks() {
        advancementGoals.clear();
        tameGoals.clear();
        itemGoals.clear();
        item64Goals.clear();
        diedGoals.clear();
        diedEntityGoals.clear();
        checkMagid = false;
        magicGoalIndex = -1;
        potionBrewGoals.clear();
        checkLingering = false;
        lingeringGoalIndex = -1;
        checkPlayerKillPlayer = false;
        pkpGoalIndex = -1;
        killEntityGoals.clear();
        breedGoals.clear();
        effectGoals.clear();
        itemGroupGoals.clear();
        itemGroupGoalTracker.clear();
        armorGroupGoals.clear();
        armorGroupGoalTracker.clear();
        armorGoals.clear();
        dyedLeatherGoal.clear();
        miningGoals.clear();
        rideEntityGoals.clear();
        eatingGoals.clear();
        biomeGoals.clear();
        useSoundGoals.clear();
        discList.clear();
        checkOpponentDies = false;
        opponentDiesIndex = -1;
        opponentDamageGoals.clear();
        opponentItemGoals.clear();
        opponenetSeedList.clear();
        checkOpponentJumps = false;
        opponentJumpsIndex = -1;
        checkSnowballHit = false;
        snowballHitIndex = -1;
        checkSleepAlone = false;
        sleepAloneIndex = -1;
        checkChickenEggSpawn = false;
        chickenEggSpawnIndex = -1;
        checkHunger = false;
        hungerIndex = -1;
        checkBedrock = false;
        bedrockIndex = -1;
        checkSkyLimit = false;
        skyLimitIndex = -1;
        checkLevel15 = false;
        level15Index = -1;
        checkGlowSign = false;
        glowSignIndex = -1;
        checkArmorStand = false;
        armorStandIndex = -1;
        checkCarvedPumpkin = false;
        carvedPumpkinIndex = -1;
        checkBreed4 = false;
        breed4Index = -1;
        checkBreed6 = false;
        breed6Index = -1;
        checkBreed8 = false;
        breed8Index = -1;
        checkKill7UniqueHostiles = false;
        kill7UniqueHostilesIndex = -1;
        checkKill10UniqueHostiles = false;
        kill10UniqueHostilesIndex = -1;
        checkKill15UniqueHostiles = false;
        kill15UniqueHostilesIndex = -1;
        checkKill30UndeadMobs = false;
        kill30UndeadMobsIndex = -1;
        checkKill20Arthropods = false;
        kill20ArthropodsIndex = -1;
        checkEat5 = false;
        eat5Index = -1;
        checkEat10 = false;
        eat10Index = -1;
        checkEat20 = false;
        eat20Index = -1;
        checkGet3StatusEffects = false;
        get3StatusEffectsIndex = -1;
        checkGet6StatusEffects = false;
        get6StatusEffectsIndex = -1;
        checkRemoveStatusEffect = false;
        removeStatusEffectIndex = -1;
        checkUseCauldron = false;
        useCauldronIndex = -1;
        checkOpponentDies3 = false;
        opponentDies3Index = -1;
        checkOpponentCatchesFire = false;
        opponentCatchesFireIndex = -1;
        checkOpponentTouchesWater = false;
        opponentTouchesWaterIndex = -1;
        checkOpponentHitsYou = false;
        opponentHitsYouIndex = -1;
        checkOpponentTakes100 = false;
        opponentTakes100Index = -1;
        checkLevel30 = false;
        level30Index = -1;
        checkDetonateTNT = false;
        detonateTNTIndex = -1;
        checkEnrageZombiePiglin = false;
        enrageZombiePiglinIndex = -1;
        checkTake200Damage = false;
        take200DamageIndex = -1;
        checkKill100Mobs = false;
        kill100MobsIndex = -1;
        checkDeal400Damage = false;
        deal400DamageIndex = -1;
        checkSprint1km = false;
        sprint1kmIndex = -1;
        checkGet15Adv = false;
        get15AdvIndex = -1;
        checkGet25Adv = false;
        get25AdvIndex = -1;
        checkGet35Adv = false;
        get35AdvIndex = -1;
        checkObtainMoreKelp = false;
        obtainMoreKelpIndex = -1;
        checkObtainMoreHopper = false;
        obtainMoreHopperIndex = -1;
        checkMoreLevels = false;
        moreLevelsIndex = -1;
        checkDieVines = false;
        dieVinesIndex = -1;
        checkFillInventory = false;
        fillInventoryIndex = -1;
    }

    public static boolean checkPlayerLimitations(LockoutGoal.Tasks task) {
        boolean pass = true;
        switch (task) {
            case sleep_alone_in_overworld:
                if (team1.size() > 1 || team2.size() > 1) {
                    pass = false;
                }
                break;
            case opponent_hits_you:
                if (ModServerEvents.GameTimer == -1) {
                    pass = false;
                }
        }

        return pass;
    }

    public static boolean checkBlackoutLimitations(LockoutGoal.Tasks task) {
        if (!blackout_mode) {
            return true;
        } else {
            boolean pass = true;
            switch (task) {
                case kill_the_other_player:
                case opponent_dies:
                case opponent_takes_fall_damage:
                case opponent_obtains_crafting_table:
                case oponnent_obtains_obsidian:
                case oponnent_obtains_seeds:
                case opponent_jumps:
                case sleep_alone_in_overworld:
                case opponent_is_hit_by_snowball:
                case opponent_dies_3_times:
                case opponent_catches_fire:
                case opponent_touches_water:
                case opponent_hits_you:
                case opponent_takes_100_total_damage:
                case obtain_more_dried_kelp_than_opponent:
                case obtain_more_hoppers_than_opponent:
                case have_more_levels_than_opponent:
                    pass = false;
                default:
                    return pass;
            }
        }
    }

    public static boolean checkBiomeStructure(LockoutGoal.Tasks task, String full_task_name) {
        if (!doBiomeStructureCheck) {
            return true;
        } else {
            if (seed != searchSeed) {
                searchSeed = seed;
                biome_structure_cache.clear();
            }

            boolean isNear = false;
            int force_check_amount = -1;
            ArrayList<String> structuresToCheck = new ArrayList();
            ArrayList<Tuple<Holder<Biome>, String>> biomesToCheck = new ArrayList();
            Registry<Biome> biomes = mcCommandSource.m_81377_().m_206579_().m_175515_(Registry.f_122885_);
            if (full_task_name.contains("Green")) {
                biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48203_).orElse((Object)null), Biomes.f_48203_.m_135782_().m_135815_()));
                biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186753_).orElse((Object)null), Biomes.f_186753_.m_135782_().m_135815_()));
                biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48159_).orElse((Object)null), Biomes.f_48159_.m_135782_().m_135815_()));
                biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48194_).orElse((Object)null), Biomes.f_48194_.m_135782_().m_135815_()));
            }

            if (full_task_name.contains("Brown")) {
                biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48222_).orElse((Object)null), Biomes.f_48222_.m_135782_().m_135815_()));
            }

            switch (task) {
                case tame_cat:
                    structuresToCheck.add("minecraft:swamp_hut");
                    structuresToCheck.add("minecraft:village_plains");
                    structuresToCheck.add("minecraft:village_desert");
                    structuresToCheck.add("minecraft:village_savanna");
                    structuresToCheck.add("minecraft:village_snowy");
                    structuresToCheck.add("minecraft:village_taiga");
                    break;
                case tame_horse:
                case breed_horse:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48157_).orElse((Object)null), Biomes.f_48157_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48158_).orElse((Object)null), Biomes.f_48158_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186768_).orElse((Object)null), Biomes.f_186768_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48202_).orElse((Object)null), Biomes.f_48202_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48176_).orElse((Object)null), Biomes.f_48176_.m_135782_().m_135815_()));
                    break;
                case tame_parrot:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48222_).orElse((Object)null), Biomes.f_48222_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48197_).orElse((Object)null), Biomes.f_48197_.m_135782_().m_135815_()));
                    break;
                case tame_wolf:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48205_).orElse((Object)null), Biomes.f_48205_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48206_).orElse((Object)null), Biomes.f_48206_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186755_).orElse((Object)null), Biomes.f_186755_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186763_).orElse((Object)null), Biomes.f_186763_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186764_).orElse((Object)null), Biomes.f_186764_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48152_).orElse((Object)null), Biomes.f_48152_.m_135782_().m_135815_()));
                    break;
                case breed_cow:
                case breed_sheep:
                case breed_chicken:
                case breed_pig:
                case breed_hoglin:
                case breed_strider:
                case obtain_red_nether_brick_stairs:
                case obtain_bookshelf:
                case obtain_mossy_stone_brick_wall:
                case obtain_end_crystal:
                case obtain_bell:
                case obtain_soul_lantern:
                case obtain_honey_bottle:
                case obtain_ancient_debris:
                case obtain_cake:
                case obtain_ender_chest:
                case obtain_wither_skeleton_skull:
                case obtain_lodestone:
                case obtain_end_rod:
                case obtain_mushroom_stem:
                case obtain_dragon_egg:
                case obtain_bottle_o_enchanting:
                case write_book:
                case brew_potion_of_lingering:
                case obtain_flowering_azalea:
                case obtain_tnt:
                case obtain_cobweb:
                case obtain_mud_brick_wall:
                case obtain_daylight_detector:
                case obtain_redstone_repeater:
                case obtain_redstone_comparator:
                case obtain_observer:
                case obtain_activator_rail:
                case obtain_detector_rail:
                case obtain_powered_rail:
                case obtain_dispenser:
                case obtain_piston:
                case obtain_glazed_terracotta:
                case obtain_64_x_wool:
                case obtain_64_x_concrete:
                case obtain_64_of_one_item_block:
                case die_by_anvil:
                case die_to_intentional_game_design:
                case die_to_iron_golem:
                case detonate_tnt_minecart:
                case die_by_magic:
                case die_by_bee_sting:
                case die_by_firework_rocket:
                case brew_potion_of_healing:
                case brew_potion_of_swiftness:
                case brew_potion_of_invisibility:
                case brew_potion_of_water_breathing:
                case kill_witch:
                case kill_zombie_villager:
                case kill_zoglin:
                case kill_silverfish:
                case kill_ghast:
                case kill_ender_dragon:
                case kill_endermite:
                case kill_colored_sheep:
                case kill_the_other_player:
                case get_jump_boost:
                case get_absorption:
                case get_levitation:
                case get_glowing:
                case get_weakness:
                case get_poisoned:
                case full_set_wooden_tools:
                case full_set_stone_tools:
                case full_set_iron_tools:
                case full_set_gold_tools:
                case full_set_diamond_tools:
                case obtain_every_type_of_shovel:
                case obtain_every_type_of_hoe:
                case obtain_every_type_of_axe:
                case obtain_every_type_of_pickaxe:
                case obtain_every_type_of_sword:
                case obtain_6_unique_flowers:
                case obtain_every_type_of_seed:
                case obtain_every_type_of_raw_ore:
                case obtain_every_type_horse_armor:
                case leather_armor_set:
                case gold_armor_set:
                case iron_armor_set:
                case diamond_armor_set:
                case one_piece_netherite_armor:
                case one_piece_chain_armor:
                case wear_colored_leather_armor:
                case mine_diamond_ore:
                case mine_mob_spawner:
                case ride_pig_with_carrot_on_stick:
                case ride_horse_with_saddle:
                case ride_minecart:
                case eat_pumpkin_pie:
                case eat_rabbit_stew:
                case eat_suspicious_stew:
                case eat_cookie:
                case eat_chorus_fruit:
                case eat_poisonous_potato:
                case eat_a_glow_berry:
                case use_smithing_table:
                case use_anvil:
                case use_composter:
                case use_jukebox_to_play_music:
                case use_loom_to_design_banner:
                case opponent_dies:
                case opponent_takes_fall_damage:
                case opponent_obtains_crafting_table:
                case oponnent_obtains_obsidian:
                case oponnent_obtains_seeds:
                case opponent_jumps:
                case sleep_alone_in_overworld:
                case spawn_a_chicken_with_an_egg:
                case empty_huger_bar:
                case reach_bedrock:
                case reach_sky_limit:
                case reach_level_15:
                case use_glow_ink_on_crimson_sign:
                case fill_armor_stand:
                case wear_carved_pumpkin:
                case breed_4_unique:
                case breed_6_unique:
                case breed_8_unique:
                default:
                    if (biomesToCheck.size() == 0 && structuresToCheck.size() == 0) {
                        return true;
                    }
                    break;
                case breed_ocelot:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48222_).orElse((Object)null), Biomes.f_48222_.m_135782_().m_135815_()));
                    break;
                case breed_rabbit:
                case brew_potion_of_leaping:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48203_).orElse((Object)null), Biomes.f_48203_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48152_).orElse((Object)null), Biomes.f_48152_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186761_).orElse((Object)null), Biomes.f_186761_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186755_).orElse((Object)null), Biomes.f_186755_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186756_).orElse((Object)null), Biomes.f_186756_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48206_).orElse((Object)null), Biomes.f_48206_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48179_).orElse((Object)null), Biomes.f_48179_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186763_).orElse((Object)null), Biomes.f_186763_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186764_).orElse((Object)null), Biomes.f_186764_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186754_).orElse((Object)null), Biomes.f_186754_.m_135782_().m_135815_()));
                    break;
                case breed_fox:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48152_).orElse((Object)null), Biomes.f_48152_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186763_).orElse((Object)null), Biomes.f_186763_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186764_).orElse((Object)null), Biomes.f_186764_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48206_).orElse((Object)null), Biomes.f_48206_.m_135782_().m_135815_()));
                    break;
                case breed_goat:
                case obtain_goat_horn:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186756_).orElse((Object)null), Biomes.f_186756_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186758_).orElse((Object)null), Biomes.f_186758_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186757_).orElse((Object)null), Biomes.f_186757_.m_135782_().m_135815_()));
                    break;
                case obtain_sea_lantern:
                case obtain_sponge:
                case kill_guardian:
                case kill_elder_guardian:
                case get_mining_fatigue:
                    structuresToCheck.add("minecraft:monument");
                    break;
                case obtain_scaffolding:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48197_).orElse((Object)null), Biomes.f_48197_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48222_).orElse((Object)null), Biomes.f_48222_.m_135782_().m_135815_()));
                    break;
                case obtain_slime_block:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48207_).orElse((Object)null), Biomes.f_48207_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_220595_).orElse((Object)null), Biomes.f_220595_.m_135782_().m_135815_()));
                    break;
                case obtain_heart_of_the_sea:
                    structuresToCheck.add("minecraft:buried_treasure");
                    break;
                case obtain_bucket_of_tropical_fish:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48167_).orElse((Object)null), Biomes.f_48167_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48166_).orElse((Object)null), Biomes.f_48166_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_151785_).orElse((Object)null), Biomes.f_151785_.m_135782_().m_135815_()));
                    break;
                case obtain_a_powder_snow_bucket:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186755_).orElse((Object)null), Biomes.f_186755_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186756_).orElse((Object)null), Biomes.f_186756_.m_135782_().m_135815_()));
                    break;
                case die_by_cactus:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48203_).orElse((Object)null), Biomes.f_48203_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186753_).orElse((Object)null), Biomes.f_186753_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48159_).orElse((Object)null), Biomes.f_48159_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48194_).orElse((Object)null), Biomes.f_48194_.m_135782_().m_135815_()));
                    break;
                case die_by_berry_bush:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48206_).orElse((Object)null), Biomes.f_48206_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48152_).orElse((Object)null), Biomes.f_48152_.m_135782_().m_135815_()));
                    break;
                case die_to_falling_stalactite:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_151784_).orElse((Object)null), Biomes.f_151784_.m_135782_().m_135815_()));
                    break;
                case die_by_llama:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48158_).orElse((Object)null), Biomes.f_48158_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186765_).orElse((Object)null), Biomes.f_186765_.m_135782_().m_135815_()));
                    break;
                case kill_stray:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186761_).orElse((Object)null), Biomes.f_186761_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48182_).orElse((Object)null), Biomes.f_48182_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48212_).orElse((Object)null), Biomes.f_48212_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48211_).orElse((Object)null), Biomes.f_48211_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48172_).orElse((Object)null), Biomes.f_48172_.m_135782_().m_135815_()));
                    break;
                case kill_snow_golem:
                case opponent_is_hit_by_snowball:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186761_).orElse((Object)null), Biomes.f_186761_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48182_).orElse((Object)null), Biomes.f_48182_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48212_).orElse((Object)null), Biomes.f_48212_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48211_).orElse((Object)null), Biomes.f_48211_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48152_).orElse((Object)null), Biomes.f_48152_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48148_).orElse((Object)null), Biomes.f_48148_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186755_).orElse((Object)null), Biomes.f_186755_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186756_).orElse((Object)null), Biomes.f_186756_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186758_).orElse((Object)null), Biomes.f_186758_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186757_).orElse((Object)null), Biomes.f_186757_.m_135782_().m_135815_()));
                    break;
                case get_nausea:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48166_).orElse((Object)null), Biomes.f_48166_.m_135782_().m_135815_()));
                    break;
                case get_bad_omen:
                    structuresToCheck.add("minecraft:pillager_outpost");
                    break;
                case obtain_every_type_of_sapling:
                    force_check_amount = 3;
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48157_).orElse((Object)null), Biomes.f_48157_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48151_).orElse((Object)null), Biomes.f_48151_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48206_).orElse((Object)null), Biomes.f_48206_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48222_).orElse((Object)null), Biomes.f_48222_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_220595_).orElse((Object)null), Biomes.f_220595_.m_135782_().m_135815_()));
                    break;
                case mine_emerald_ore:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186754_).orElse((Object)null), Biomes.f_186754_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186755_).orElse((Object)null), Biomes.f_186755_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186756_).orElse((Object)null), Biomes.f_186756_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186758_).orElse((Object)null), Biomes.f_186758_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186757_).orElse((Object)null), Biomes.f_186757_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186759_).orElse((Object)null), Biomes.f_186759_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186765_).orElse((Object)null), Biomes.f_186765_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186767_).orElse((Object)null), Biomes.f_186767_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186766_).orElse((Object)null), Biomes.f_186766_.m_135782_().m_135815_()));
                    break;
                case mine_turtle_egg:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48217_).orElse((Object)null), Biomes.f_48217_.m_135782_().m_135815_()));
                    break;
                case find_ice_spike_biome:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48182_).orElse((Object)null), Biomes.f_48182_.m_135782_().m_135815_()));
                    break;
                case find_mushroom_biome:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48215_).orElse((Object)null), Biomes.f_48215_.m_135782_().m_135815_()));
                    break;
                case find_badlands_biome:
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_186753_).orElse((Object)null), Biomes.f_186753_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48159_).orElse((Object)null), Biomes.f_48159_.m_135782_().m_135815_()));
                    biomesToCheck.add(new Tuple((Holder)biomes.m_203636_(Biomes.f_48194_).orElse((Object)null), Biomes.f_48194_.m_135782_().m_135815_()));
            }

            for(Tuple<Holder<Biome>, String> biomeToCheck : biomesToCheck) {
                if (biome_structure_cache.containsKey(biomeToCheck.m_14419_())) {
                    if (!Boolean.TRUE.equals(biome_structure_cache.get(biomeToCheck.m_14419_()))) {
                        continue;
                    }

                    --force_check_amount;
                    if (force_check_amount <= 0) {
                        isNear = true;
                        break;
                    }
                }

                if (biomeToCheck.m_14418_() == null) {
                    LockoutBingoMod.LOGGER.info("Issue with biome for task: " + task.name());
                }

                BlockPos blockpos = mcCommandSource.m_81372_().m_220360_();
                ServerLevel var10000 = mcCommandSource.m_81372_();
                HolderSet.Direct var10001 = HolderSet.m_205809_(new Holder[]{(Holder)biomeToCheck.m_14418_()});
                Objects.requireNonNull(var10001);
                Pair<BlockPos, Holder<Biome>> pair = var10000.m_215069_(var10001::m_203333_, blockpos, 1250, 32, 64);
                if (pair != null) {
                    BlockPos blockpos1 = (BlockPos)pair.getFirst();
                    if ((double)getDistance(blockpos.m_123341_(), blockpos.m_123343_(), blockpos1.m_123341_(), blockpos1.m_123343_()) <= (double)1000.0F) {
                        biome_structure_cache.put((String)biomeToCheck.m_14419_(), Boolean.TRUE);
                        --force_check_amount;
                        if (force_check_amount <= 0) {
                            isNear = true;
                            break;
                        }
                    } else {
                        biome_structure_cache.put((String)biomeToCheck.m_14419_(), Boolean.FALSE);
                    }
                } else {
                    biome_structure_cache.put((String)biomeToCheck.m_14419_(), Boolean.FALSE);
                }
            }

            if (isNear) {
                return true;
            } else {
                for(String structureToCheck : structuresToCheck) {
                    if (biome_structure_cache.containsKey(structureToCheck)) {
                        if (!Boolean.TRUE.equals(biome_structure_cache.get(structureToCheck))) {
                            continue;
                        }

                        --force_check_amount;
                        if (force_check_amount <= 0) {
                            isNear = true;
                            break;
                        }
                    }

                    BlockPos blockpos = mcCommandSource.m_81372_().m_220360_();
                    ServerLevel serverlevel = mcCommandSource.m_81372_();

                    try {
                        ResourceOrTagLocationArgument<Structure> arg = new ResourceOrTagLocationArgument(Registry.f_235725_);
                        ResourceOrTagLocationArgument.Result<?> result = arg.parse(new StringReader(structureToCheck));
                        Optional<ResourceOrTagLocationArgument.Result<Structure>> optional = result.m_207209_(Registry.f_235725_);
                        ResourceOrTagLocationArgument.Result<Structure> p_214473_ = (ResourceOrTagLocationArgument.Result)optional.orElseThrow(() -> LocateCommand.f_214452_.create(result));
                        Registry<Structure> registry = mcCommandSource.m_81372_().m_5962_().m_175515_(Registry.f_235725_);
                        HolderSet<Structure> holderset = (HolderSet)LocateCommand.m_214483_(p_214473_, registry).orElseThrow(() -> LocateCommand.f_214452_.create(p_214473_.m_207276_()));
                        Pair<BlockPos, Holder<Structure>> pair = serverlevel.m_7726_().m_8481_().m_223037_(serverlevel, holderset, blockpos, 100, false);
                        if (pair != null) {
                            BlockPos blockpos1 = (BlockPos)pair.getFirst();
                            if ((double)getDistance(blockpos.m_123341_(), blockpos.m_123343_(), blockpos1.m_123341_(), blockpos1.m_123343_()) <= (double)1000.0F) {
                                biome_structure_cache.put(structureToCheck, Boolean.TRUE);
                                --force_check_amount;
                                if (force_check_amount <= 0) {
                                    isNear = true;
                                    break;
                                }
                            } else {
                                biome_structure_cache.put(structureToCheck, Boolean.FALSE);
                            }
                        } else {
                            biome_structure_cache.put(structureToCheck, Boolean.FALSE);
                        }
                    } catch (CommandSyntaxException exception) {
                        exception.printStackTrace();
                    }
                }

                return isNear;
            }
        }
    }

    private static float getDistance(int x1, int z1, int x2, int z2) {
        int i = x2 - x1;
        int j = z2 - z1;
        return Mth.m_14116_((float)(i * i + j * j));
    }

    public static void SelectBoardGoals() {
        LockoutBingoMod.LOGGER.info("Entering Select Board Goals");
        test_mode = false;
        LockoutGoals.GenerateGoalList();
        boardGoals.clear();
        boardGoalsCompletion.clear();
        resetLimitCounts();
        resetGoalChecks();
        LockoutBingoMod.LOGGER.info("All Old Board Info Cleared");
        int num = LockoutGoals.full_goal_list.size();
        LockoutBingoMod.LOGGER.info(String.format("There are %d Goals to choose from", num));
        int[] selection = (new Random()).ints(0, num).distinct().limit((long)num).toArray();
        int i = 0;

        do {
            LockoutGoal temp = (LockoutGoal)LockoutGoals.full_goal_list.get(selection[i]);
            boolean canAdd = true;
            if (temp.getTaskName().contains("Unique Animals") || temp.getTaskName().contains("Unique Hostile Mobs") || temp.getTaskName().equals("Kill 30 Undead Mobs") || temp.getTaskName().equals("Kill 20 Arthropods") || temp.getTaskName().startsWith("Obtain 6") || temp.getTaskName().equals("An Opponent Dies 3 Times") || temp.getTaskName().equals("Deal 400 Damage") || temp.getTaskName().equals("Take 200 Damage") || temp.getTaskName().equals("An Opponent Takes 100 Total Damage")) {
                int index_to_add = boardGoals.size();
                if (index_to_add % 5 != 0 && (((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().contains("Unique Animals") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().contains("Unique Hostile Mobs") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("Kill 30 Undead Mobs") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("Kill 20 Arthropods") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().startsWith("Obtain 6") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("An Opponent Dies 3 Times") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("Deal 400 Damage") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("Take 200 Damage") || ((LockoutGoal)boardGoals.get(index_to_add - 1)).getTaskName().equals("An Opponent Takes 100 Total Damage"))) {
                    canAdd = false;
                }

                if (index_to_add - 5 >= 0 && (((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().contains("Unique Animals") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().contains("Unique Hostile Mobs") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("Kill 30 Undead Mobs") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("Kill 20 Arthropods") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().startsWith("Obtain 6") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("An Opponent Dies 3 Times") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("Deal 400 Damage") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("Take 200 Damage") || ((LockoutGoal)boardGoals.get(index_to_add - 5)).getTaskName().equals("An Opponent Takes 100 Total Damage"))) {
                    canAdd = false;
                }
            }

            switch (temp.getDifficulty()) {
                case 1:
                    if (num_of_diff_one >= limit_num_of_diff_one) {
                        canAdd = false;
                    }
                    break;
                case 2:
                    if (num_of_diff_two >= limit_num_of_diff_two) {
                        canAdd = false;
                    }
                    break;
                case 3:
                    if (num_of_diff_three >= limit_num_of_diff_three) {
                        canAdd = false;
                    }
            }

            canAdd = canAdd && (temp.getEnd() != 1 || num_of_end < 2);
            canAdd = canAdd && (temp.getTools() != 1 || num_of_tool < 2);
            canAdd = canAdd && (temp.getDiamond() != 1 || num_of_diamond < 1);
            canAdd = canAdd && (temp.getNetherite() != 1 || num_of_netherite < 1);
            canAdd = canAdd && (temp.getArmor() != 1 || num_of_armor < 1);
            canAdd = canAdd && (temp.getArmorSpecial() != 1 || num_of_armor_special < 1);
            canAdd = canAdd && (temp.getTame() != 1 || num_of_tame < 2);
            canAdd = canAdd && (temp.getKill_1_mob() != 1 || num_of_kill_1_mob < 3);
            canAdd = canAdd && (temp.getKill_x_hostile() != 1 || num_of_kill_x_hostile < 2);
            canAdd = canAdd && (temp.getKill_type() != 1 || num_of_kill_type < 1);
            canAdd = canAdd && (temp.getFortress() != 1 || num_of_fortress < 3);
            canAdd = canAdd && (temp.getHorse() != 1 || num_of_horse < 1);
            canAdd = canAdd && (temp.getBreed1() != 1 || num_of_breed1 < 1);
            canAdd = canAdd && (temp.getBreed2() != 1 || num_of_breed2 < 2);
            canAdd = canAdd && (temp.getBreedX() != 1 || num_of_breedx < 1);
            canAdd = canAdd && (temp.getRide() != 1 || num_of_ride < 2);
            canAdd = canAdd && (temp.getEat() != 1 || num_of_eat < 3);
            canAdd = canAdd && (temp.getEatX() != 1 || num_of_eatx < 2);
            canAdd = canAdd && (temp.getEffect() != 1 || num_of_effect < 4);
            canAdd = canAdd && (temp.getEffectX() != 1 || num_of_effect_x < 1);
            canAdd = canAdd && (temp.getDie() == 0 || num_of_die + temp.getDie() < 3);
            canAdd = canAdd && (temp.getBiome() != 1 || num_of_biome < 1);
            canAdd = canAdd && (temp.getEnd_structure() != 1 || num_of_end_structure < 1);
            canAdd = canAdd && (temp.getOpponent_hard() != 1 || num_of_opponent_hard < 1);
            canAdd = canAdd && (temp.getOpponenent_hurts() != 1 || num_of_opponent_hurts < 1);
            canAdd = canAdd && (temp.getPlayer_dies() != 1 || num_of_player_dies < 1);
            canAdd = canAdd && (temp.getDamage() != 1 || num_of_damage < 1);
            canAdd = canAdd && (temp.getAdvancement() != 1 || num_of_advancement < 1);
            canAdd = canAdd && (temp.getVillage() != 1 || num_of_village < 3);
            canAdd = canAdd && (temp.getMonument() != 1 || num_of_monument < 1);
            canAdd = canAdd && (temp.getRedstone() != 1 || num_of_redstone < 2);
            canAdd = canAdd && (temp.getTnt() != 1 || num_of_tnt < 1);
            canAdd = canAdd && (temp.getObtain_more() != 1 || num_of_obtain_more < 1);
            canAdd = canAdd && (temp.getStrider() != 1 || num_of_strider < 1);
            canAdd = canAdd && (temp.getSheep() != 1 || num_of_sheep < 1);
            canAdd = canAdd && (temp.getPunch() != 1 || num_of_punch < 1);
            canAdd = canAdd && (temp.getCollect() != 1 || num_of_collect < 2);
            canAdd = canAdd && (temp.getPoison() != 1 || num_of_poison < 1);
            canAdd = canAdd && (temp.getDragon() != 1 || num_of_dragon < 1);
            canAdd = canAdd && (temp.getKill100() != 1 || num_of_kill100 < 1);
            canAdd = canAdd && (temp.getSeed() != 1 || num_of_seed < 1);
            if (canAdd && checkBiomeStructure(temp.getTask(), temp.getTaskName()) && checkPlayerLimitations(temp.getTask()) && checkBlackoutLimitations(temp.getTask())) {
                switch (temp.getDifficulty()) {
                    case 1 -> ++num_of_diff_one;
                    case 2 -> ++num_of_diff_two;
                    case 3 -> ++num_of_diff_three;
                }

                num_of_end += temp.getEnd();
                num_of_tool += temp.getTools();
                num_of_diamond += temp.getDiamond();
                num_of_netherite += temp.getNetherite();
                num_of_armor += temp.getArmor();
                num_of_armor_special += temp.getArmorSpecial();
                num_of_tame += temp.getTame();
                num_of_kill_1_mob += temp.getKill_1_mob();
                num_of_kill_x_hostile += temp.getKill_x_hostile();
                num_of_kill_type += temp.getKill_type();
                num_of_fortress += temp.getFortress();
                num_of_horse += temp.getHorse();
                num_of_breed1 += temp.getBreed1();
                num_of_breed2 += temp.getBreed2();
                num_of_breedx += temp.getBreedX();
                num_of_ride += temp.getRide();
                num_of_eat += temp.getEat();
                num_of_eatx += temp.getEatX();
                num_of_effect += temp.getEffect();
                num_of_effect_x += temp.getEffectX();
                num_of_die += temp.getDie();
                num_of_biome += temp.getBiome();
                num_of_end_structure += temp.getEnd_structure();
                num_of_opponent_hard += temp.getOpponent_hard();
                num_of_opponent_hurts += temp.getOpponenent_hurts();
                num_of_player_dies += temp.getPlayer_dies();
                num_of_damage += temp.getDamage();
                num_of_advancement += temp.getAdvancement();
                num_of_village += temp.getVillage();
                num_of_monument += temp.getMonument();
                num_of_redstone += temp.getRedstone();
                num_of_tnt += temp.getTnt();
                num_of_obtain_more += temp.getObtain_more();
                num_of_strider += temp.getStrider();
                num_of_sheep += temp.getSheep();
                num_of_punch += temp.getPunch();
                num_of_collect += temp.getCollect();
                num_of_poison += temp.getPoison();
                num_of_dragon += temp.getDragon();
                num_of_kill100 += temp.getKill100();
                num_of_seed += temp.getSeed();
                boardGoals.add(temp);
            }

            ++i;
        } while(boardGoals.size() != 25 && i < selection.length);

        if (boardGoals.size() != 25) {
            LockoutBingoMod.LOGGER.info("Ran Out of Goals... Need to Restart???");
            SelectBoardGoals();
        }

        int k = 0;

        for(LockoutGoal goal : boardGoals) {
            boardGoalsCompletion.add(new PacketLockoutBingoInfo.GoalTuple(goal.getTaskName(), "", k));
            ++k;
        }

        LockoutBingoMod.LOGGER.info("All Goal Packets Made");
        setupUpGoalSearches();
        LockoutBingoMod.LOGGER.info("All Goal Searches Setup");
    }

    public static void SelectTestAllBoardGoals() {
        test_mode = true;
        LockoutGoals.GenerateFullGoalList();
        boardGoals.clear();
        boardGoalsCompletion.clear();
        resetLimitCounts();
        resetGoalChecks();
        int num = LockoutGoals.full_goal_list.size();
        int[] selection = (new Random()).ints(0, num).distinct().limit((long)num).toArray();

        for(int i : selection) {
            LockoutGoal temp = (LockoutGoal)LockoutGoals.full_goal_list.get(selection[i]);
            boardGoals.add(temp);
        }

        int k = 0;

        for(LockoutGoal goal : boardGoals) {
            boardGoalsCompletion.add(new PacketLockoutBingoInfo.GoalTuple(goal.getTaskName(), "", k));
            ++k;
        }

        setupUpGoalSearches();
    }

    public static void ResumeLockoutSetup() {
        LockoutGoals.GenerateFullGoalList();
        boardGoals.clear();
        resetGoalChecks();
        resetLimitCounts();

        for(int i = 0; i < boardGoalsCompletion.size(); ++i) {
            if (!((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).player_complete.equals("")) {
                if (team1.contains(((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).player_complete)) {
                    ++team1Score;
                } else if (team2.contains(((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).player_complete)) {
                    ++team2Score;
                }
            }

            for(int k = 0; k < LockoutGoals.full_goal_list.size(); ++k) {
                if (((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).goal.equals(((LockoutGoal)LockoutGoals.full_goal_list.get(k)).getTaskName())) {
                    boardGoals.add((LockoutGoal)LockoutGoals.full_goal_list.get(k));
                    LockoutGoals.full_goal_list.remove(k);
                    break;
                }
            }
        }

        setupUpGoalSearches();
    }

    public static void handleTeam1PlayerInit(String player) {
        if (team2.size() != 0) {
            trackingMap.put(player, (String)team2.get(0));
        } else if (team1.size() > 1) {
            if (((String)team1.get(0)).equals(player)) {
                trackingMap.put(player, (String)team1.get(1));
            } else {
                trackingMap.put(player, (String)team1.get(0));
            }
        } else {
            trackingMap.put(player, (String)team1.get(0));
        }

    }

    public static void initializeTrackingTable() {
        trackingMap.clear();

        for(String player : team1) {
            handleTeam1PlayerInit(player);
        }

        for(String player : team2) {
            trackingMap.put(player, (String)team1.get(0));
        }

    }

    public static void reinitSinglePlayer(String player) {
        if (team1.contains(player)) {
            handleTeam1PlayerInit(player);
        } else if (team2.contains(player)) {
            trackingMap.put(player, (String)team1.get(0));
        }

    }

    public static void rotateTrackingTable(String player) {
        if (team1.contains(player)) {
            if (!trackingMap.containsKey(player)) {
                handleTeam1PlayerInit(player);
                return;
            }

            String currentTrack = (String)trackingMap.get(player);
            if (team2.contains(currentTrack)) {
                int i = team2.indexOf(currentTrack) + 1;
                if (i < team2.size()) {
                    trackingMap.put(player, (String)team2.get(i));
                    return;
                }

                i = 0;
                if (((String)team1.get(i)).equals(player)) {
                    ++i;
                }

                if (i < team1.size()) {
                    trackingMap.put(player, (String)team1.get(i));
                } else {
                    trackingMap.put(player, (String)team2.get(0));
                }
            } else if (team1.contains(currentTrack)) {
                int i = team1.indexOf(currentTrack) + 1;
                if (i < team1.size()) {
                    if (((String)team1.get(i)).equals(player)) {
                        ++i;
                    }

                    if (i < team1.size()) {
                        trackingMap.put(player, (String)team1.get(i));
                        return;
                    }
                }

                handleTeam1PlayerInit(player);
            } else {
                handleTeam1PlayerInit(player);
            }
        } else if (team2.contains(player)) {
            if (!trackingMap.containsKey(player)) {
                trackingMap.put(player, (String)team1.get(0));
                return;
            }

            String currentTrack = (String)trackingMap.get(player);
            if (team1.contains(currentTrack)) {
                int i = team1.indexOf(currentTrack) + 1;
                if (i < team1.size()) {
                    trackingMap.put(player, (String)team1.get(i));
                    return;
                }

                i = 0;
                if (((String)team2.get(i)).equals(player)) {
                    ++i;
                }

                if (i < team2.size()) {
                    trackingMap.put(player, (String)team2.get(i));
                } else {
                    trackingMap.put(player, (String)team1.get(0));
                }
            } else if (team2.contains(currentTrack)) {
                int i = team2.indexOf(currentTrack) + 1;
                if (i < team2.size()) {
                    if (((String)team2.get(i)).equals(player)) {
                        ++i;
                    }

                    if (i < team2.size()) {
                        trackingMap.put(player, (String)team2.get(i));
                        return;
                    }
                }

                trackingMap.put(player, (String)team1.get(0));
            } else {
                trackingMap.put(player, (String)team1.get(0));
            }
        }

    }

    public static void setupUpGoalSearches() {
        initializeTrackingTable();

        for(int i = 0; i < boardGoals.size(); ++i) {
            if (((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).player_complete.equals("")) {
                buildSpecialGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildItemGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildDiedGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildPotionBrewGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildKillEntityGoals(((LockoutGoal)boardGoals.get(i)).getTask(), ((LockoutGoal)boardGoals.get(i)).getTaskName(), i);
                buildEffectGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildItemGroupGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildArmorGroupGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildArmorGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildDyedGoals(((LockoutGoal)boardGoals.get(i)).getTask(), ((LockoutGoal)boardGoals.get(i)).getTaskName(), i);
                buildMiningGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildRideGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildEatingGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildBiomeGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildUseSoundGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildOpponentDamageGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildOpponentObtainsGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildMiscGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
            }
        }

    }

    public static void resetGoalSearches() {
        resetGoalChecks();

        for(int i = 0; i < boardGoals.size(); ++i) {
            if (((PacketLockoutBingoInfo.GoalTuple)boardGoalsCompletion.get(i)).player_complete.equals("")) {
                buildSpecialGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildItemGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildDiedGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildPotionBrewGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildKillEntityGoals(((LockoutGoal)boardGoals.get(i)).getTask(), ((LockoutGoal)boardGoals.get(i)).getTaskName(), i);
                buildEffectGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildItemGroupGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildArmorGroupGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildArmorGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildDyedGoals(((LockoutGoal)boardGoals.get(i)).getTask(), ((LockoutGoal)boardGoals.get(i)).getTaskName(), i);
                buildMiningGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildRideGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildEatingGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildBiomeGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildUseSoundGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildOpponentDamageGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildOpponentObtainsGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
                buildMiscGoals(((LockoutGoal)boardGoals.get(i)).getTask(), i);
            }
        }

    }

    public static class AdvancementGoal {
        public LockoutGoal.Tasks task;
        public List<String> advancement_text;
        public int goal_index;

        public AdvancementGoal(LockoutGoal.Tasks task, int goal_index) {
            this.task = task;
            this.goal_index = goal_index;
            this.advancement_text = new ArrayList();
            this.map_task_to_advancement();
        }

        private void map_task_to_advancement() {
            this.advancement_text.clear();
            switch (this.task) {
                case enter_nether:
                    this.advancement_text.add("We Need to Go Deeper");
                    break;
                case enter_end:
                    this.advancement_text.add("The End?");
                    break;
                case distract_piglin:
                    this.advancement_text.add("Oh Shiny");
                    break;
                case this_boat_has_legs_strider:
                    this.advancement_text.add("This Boat Has Legs");
                    break;
                case find_fortress:
                    this.advancement_text.add("A Terrible Fortress");
                    break;
                case find_bastion:
                    this.advancement_text.add("Those Were the Days");
                    break;
                case find_stronghold:
                    this.advancement_text.add("Eye Spy");
                    break;
                case find_end_city:
                    this.advancement_text.add("The City at the End of the Game");
                    break;
                case trade_with_villager:
                    this.advancement_text.add("What a Deal!");
                    break;
                case use_enchanting_table:
                    this.advancement_text.add("Enchanter");
                    break;
                case get_any_spyglass_advancement:
                    this.advancement_text.add("Is It a Bird?");
                    this.advancement_text.add("Is It a Balloon?");
                    this.advancement_text.add("Is It a Plane?");
                    break;
                case get_sniper_duel:
                    this.advancement_text.add("Sniper Duel");
                    break;
                case get_bullseye:
                    this.advancement_text.add("Bullseye");
                    break;
                case use_a_brewing_stand:
                    this.advancement_text.add("Local Brewery");
                    break;
                case charge_respawn_anchor_to_max:
                    this.advancement_text.add("Not Quite \"Nine\" Lives");
                    break;
                case visit_all_nether_biomes:
                    this.advancement_text.add("Hot Tourist Destinations");
            }

        }
    }

    public static class TameGoal {
        public LockoutGoal.Tasks task;
        public String animal_name;
        public int goal_index;

        public TameGoal(LockoutGoal.Tasks task, int goal_index) {
            this.task = task;
            this.goal_index = goal_index;
            this.map_task_to_animal();
        }

        private void map_task_to_animal() {
            switch (this.task) {
                case tame_cat -> this.animal_name = "cat";
                case tame_horse -> this.animal_name = "horse";
                case tame_parrot -> this.animal_name = "parrot";
                case tame_wolf -> this.animal_name = "wolf";
            }

        }
    }

    public static class BreedGoal {
        public LockoutGoal.Tasks task;
        public String animal_name;
        public int goal_index;

        public BreedGoal(LockoutGoal.Tasks task, int goal_index) {
            this.task = task;
            this.goal_index = goal_index;
            this.map_task_to_animal();
        }

        private void map_task_to_animal() {
            switch (this.task) {
                case breed_cow -> this.animal_name = "cow";
                case breed_sheep -> this.animal_name = "sheep";
                case breed_chicken -> this.animal_name = "chicken";
                case breed_pig -> this.animal_name = "pig";
                case breed_horse -> this.animal_name = "horse";
                case breed_hoglin -> this.animal_name = "hoglin";
                case breed_ocelot -> this.animal_name = "ocelot";
                case breed_rabbit -> this.animal_name = "rabbit";
                case breed_fox -> this.animal_name = "fox";
                case breed_strider -> this.animal_name = "strider";
                case breed_goat -> this.animal_name = "goat";
            }

        }
    }
}
