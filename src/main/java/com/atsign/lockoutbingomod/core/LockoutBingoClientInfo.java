package com.atsign.lockoutbingomod.core;

import com.atsign.lockoutbingomod.LockoutBingoMod;
import com.atsign.lockoutbingomod.LockoutBingoInterface.BingoIngameGui;
import com.atsign.lockoutbingomod.LockoutBingoInterface.MainBingoTileEntity;
import com.atsign.lockoutbingomod.command.impl.LockoutBingoCommands;
import com.atsign.lockoutbingomod.core.init.ItemInit;
import com.atsign.lockoutbingomod.network.PacketLockoutBingoInfo;
import com.atsign.lockoutbingomod.network.PacketPlaySoundEffect;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;

public class LockoutBingoClientInfo {
    public static boolean isTestMode = true;
    public static ArrayList<String> team1;
    public static ArrayList<String> team2;
    public static int numOfGoals;
    public static ArrayList<PacketLockoutBingoInfo.GoalTuple> goals;
    public static LocalPlayer clientPlayerEntity;
    public static int clientPlayersTeamNum;
    public static MainBingoTileEntity bingoInventory;
    public static ArrayList<Integer> tileColoring;
    public static Hashtable<Integer, Integer> color_offset;
    public static Hashtable<String, Item> itemMap = null;
    public static Hashtable<String, List<Item>> itemCycleMap = null;
    public static Hashtable<String, Integer> itemCyclePosition = null;
    public static int location_id = 2;
    public static int teamScore = 0;
    public static int oppScore = 0;
    public static int timerLeft = -1;
    public static int team1ColorValue = -1;
    public static int team2ColorValue = -1;
    public static BingoIngameGui bingoGUI = null;
    public static boolean checkJump = false;

    public LockoutBingoClientInfo() {
    }

    public static void buildItemMapping() {
        color_offset = new Hashtable();
        color_offset.put(LockoutBingoCommands.blue_color, 0);
        color_offset.put(LockoutBingoCommands.orange_color, 17);
        color_offset.put(170, 34);
        color_offset.put(43520, 51);
        color_offset.put(43690, 68);
        color_offset.put(11141120, 85);
        color_offset.put(11141290, 102);
        color_offset.put(16755200, 119);
        color_offset.put(11184810, 136);
        color_offset.put(5592405, 153);
        color_offset.put(5592575, 170);
        color_offset.put(5635925, 187);
        color_offset.put(5636095, 204);
        color_offset.put(16733525, 221);
        color_offset.put(16733695, 238);
        color_offset.put(16777045, 255);
        color_offset.put(16777215, 272);
        color_offset.put(0, 289);
        itemMap = new Hashtable();
        itemMap.put("Mine Diamond Ore", Items.f_42010_);
        itemMap.put("Mine Emerald Ore", Items.f_42107_);
        itemMap.put("Mine Mob Spawner", Items.f_42007_);
        itemMap.put("Mine Turtle Egg", Items.f_42279_);
        itemMap.put("Enter Nether", (Item)ItemInit.NETHER_PORTAL.get());
        itemMap.put("Enter End", Items.f_42101_);
        itemMap.put("Kill The Ender Dragon", Items.f_42683_);
        itemMap.put("Full Set of Wooden Tools", Items.f_42422_);
        itemMap.put("Full Set of Stone Tools", Items.f_42427_);
        itemMap.put("Full Set of Gold Tools", Items.f_42432_);
        itemMap.put("Full Set of Iron Tools", Items.f_42385_);
        itemMap.put("Full Set of Diamond Tools", Items.f_42390_);
        itemMap.put("Obtain Every Type of Shovel", Items.f_42421_);
        itemMap.put("Obtain Every Type of Hoe", Items.f_42424_);
        itemMap.put("Obtain Every Type of Axe", Items.f_42423_);
        itemMap.put("Obtain Every Type of Pickaxe", Items.f_42422_);
        itemMap.put("Obtain Every Type of Sword", Items.f_42420_);
        itemMap.put("Fill an Armor Stand with Armor", Items.f_42650_);
        itemMap.put("Wear Leather Armor Set", Items.f_42408_);
        itemMap.put("Wear Gold Armor Set", Items.f_42477_);
        itemMap.put("Wear Iron Armor Set", Items.f_42469_);
        itemMap.put("Wear Diamond Armor Set", Items.f_42473_);
        itemMap.put("Wear 1 Piece of Netherite Armor", Items.f_42480_);
        itemMap.put("Wear 1 Piece of Chain Armor", Items.f_42464_);
        itemMap.put("Wear a carved pumpkin for 5 minutes", Items.f_42047_);
        itemMap.put("Tame a Cat", (Item)ItemInit.TAME_CAT.get());
        itemMap.put("Tame a Horse", (Item)ItemInit.TAME_HORSE.get());
        itemMap.put("Tame a Parrot", (Item)ItemInit.TAME_PARROT.get());
        itemMap.put("Tame a Wolf", (Item)ItemInit.TAME_WOLF.get());
        itemMap.put("Breed Cow", (Item)ItemInit.BREED_COW.get());
        itemMap.put("Breed Sheep", (Item)ItemInit.BREED_SHEEP.get());
        itemMap.put("Breed Chicken", (Item)ItemInit.BREED_CHICKEN.get());
        itemMap.put("Breed Pig", (Item)ItemInit.BREED_PIG.get());
        itemMap.put("Breed Horse", (Item)ItemInit.BREED_HORSE.get());
        itemMap.put("Breed Hoglin", (Item)ItemInit.BREED_HOGLIN.get());
        itemMap.put("Breed Ocelot", (Item)ItemInit.BREED_OCELOT.get());
        itemMap.put("Breed Rabbit", (Item)ItemInit.BREED_RABBIT.get());
        itemMap.put("Breed Fox", (Item)ItemInit.BREED_FOX.get());
        itemMap.put("Breed Strider", (Item)ItemInit.BREED_STRIDER.get());
        itemMap.put("Breed Goat", (Item)ItemInit.BREED_GOAT.get());
        itemMap.put("Kill Witch", (Item)ItemInit.KILL_WITCH.get());
        itemMap.put("Kill Zombie Villager", (Item)ItemInit.KILL_ZOMBIE_VILLAGER.get());
        itemMap.put("Kill Stray", (Item)ItemInit.KILL_STRAY.get());
        itemMap.put("Kill Zoglin", (Item)ItemInit.KILL_ZOGLIN.get());
        itemMap.put("Kill Silverfish", (Item)ItemInit.KILL_SILVERFISH.get());
        itemMap.put("Kill Guardian", (Item)ItemInit.KILL_GUARDIAN.get());
        itemMap.put("Kill Ghast", (Item)ItemInit.KILL_GHAST.get());
        itemMap.put("Kill Snow Golem", (Item)ItemInit.KILL_SNOW_GOLEM.get());
        itemMap.put("Kill Elder Guardian", (Item)ItemInit.KILL_ELDER_GUARDIAN.get());
        itemMap.put("Kill Endermite", (Item)ItemInit.KILL_ENDERMITE.get());

        for(String color : LockoutGoals.colors) {
            Item sheep = null;
            Item terracotta = null;
            Item wool = null;
            Item concrete = null;
            switch (color) {
                case "White":
                    wool = Items.f_41870_;
                    concrete = Items.f_42246_;
                    terracotta = Items.f_42230_;
                    break;
                case "Orange":
                    wool = Items.f_41871_;
                    concrete = Items.f_42247_;
                    terracotta = Items.f_42231_;
                    sheep = (Item)ItemInit.KILL_ORANGE_SHEEP.get();
                    break;
                case "Magenta":
                    wool = Items.f_41872_;
                    concrete = Items.f_42248_;
                    terracotta = Items.f_42232_;
                    sheep = (Item)ItemInit.KILL_MAGENTA_SHEEP.get();
                    break;
                case "Light Blue":
                    wool = Items.f_41873_;
                    concrete = Items.f_42249_;
                    terracotta = Items.f_42233_;
                    sheep = (Item)ItemInit.KILL_LIGHT_BLUE_SHEEP.get();
                    break;
                case "Yellow":
                    wool = Items.f_41874_;
                    concrete = Items.f_42303_;
                    terracotta = Items.f_42234_;
                    sheep = (Item)ItemInit.KILL_YELLOW_SHEEP.get();
                    break;
                case "Lime":
                    wool = Items.f_41875_;
                    concrete = Items.f_42304_;
                    terracotta = Items.f_42235_;
                    sheep = (Item)ItemInit.KILL_LIME_SHEEP.get();
                    break;
                case "Pink":
                    wool = Items.f_41876_;
                    concrete = Items.f_42305_;
                    terracotta = Items.f_42236_;
                    sheep = (Item)ItemInit.KILL_PINK_SHEEP.get();
                    break;
                case "Gray":
                    wool = Items.f_41877_;
                    concrete = Items.f_42306_;
                    terracotta = Items.f_42237_;
                    break;
                case "Light Gray":
                    wool = Items.f_41878_;
                    concrete = Items.f_42307_;
                    terracotta = Items.f_42238_;
                    break;
                case "Cyan":
                    wool = Items.f_41932_;
                    concrete = Items.f_42308_;
                    terracotta = Items.f_42239_;
                    sheep = (Item)ItemInit.KILL_CYAN_SHEEP.get();
                    break;
                case "Purple":
                    wool = Items.f_41933_;
                    concrete = Items.f_42309_;
                    terracotta = Items.f_42240_;
                    sheep = (Item)ItemInit.KILL_PURPLE_SHEEP.get();
                    break;
                case "Blue":
                    wool = Items.f_41934_;
                    concrete = Items.f_42310_;
                    terracotta = Items.f_42241_;
                    sheep = (Item)ItemInit.KILL_BLUE_SHEEP.get();
                    break;
                case "Brown":
                    wool = Items.f_41935_;
                    concrete = Items.f_42311_;
                    terracotta = Items.f_42242_;
                    break;
                case "Green":
                    wool = Items.f_41936_;
                    concrete = Items.f_42312_;
                    terracotta = Items.f_42243_;
                    sheep = (Item)ItemInit.KILL_GREEN_SHEEP.get();
                    break;
                case "Red":
                    wool = Items.f_41937_;
                    concrete = Items.f_42313_;
                    terracotta = Items.f_42244_;
                    sheep = (Item)ItemInit.KILL_RED_SHEEP.get();
                    break;
                case "Black":
                    wool = Items.f_41938_;
                    concrete = Items.f_42314_;
                    terracotta = Items.f_42245_;
            }

            if (terracotta != null) {
                itemMap.put(String.format("Obtain %s Glazed Terracotta", color), terracotta);
            }

            if (sheep != null) {
                itemMap.put(String.format("Kill %s Sheep", color), sheep);
            }

            if (wool != null) {
                itemMap.put(String.format("Obtain 64 %s Wool", color), wool);
            }

            if (concrete != null) {
                itemMap.put(String.format("Obtain 64 %s Concrete", color), concrete);
            }
        }

        itemMap.put("Kill 30 Undead Mobs", (Item)ItemInit.KILL_ZOMBIE.get());
        itemMap.put("Kill 20 Arthropods", (Item)ItemInit.KILL_SPIDER_CYCLE.get());
        itemMap.put("Kill The Other Player", (Item)ItemInit.KILL_PLAYER.get());
        itemMap.put("Obtain Red Nether Brick Stairs", Items.f_42376_);
        itemMap.put("Obtain Bucket of Tropical Fish", Items.f_42459_);
        itemMap.put("Obtain Sea Lantern", Items.f_42251_);
        itemMap.put("Obtain Bookshelf", Items.f_41997_);
        itemMap.put("Obtain Mossy Stone Brick Wall", Items.f_42071_);
        itemMap.put("Obtain Flowering Azalea", Items.f_151013_);
        itemMap.put("Obtain Scaffolding", Items.f_42340_);
        itemMap.put("Obtain End Crystal", Items.f_42729_);
        itemMap.put("Obtain Bell", Items.f_42777_);
        itemMap.put("Obtain Bottle o' Enchanting", Items.f_42612_);
        itemMap.put("Obtain Slime Block", Items.f_42204_);
        itemMap.put("Obtain a powder snow bucket", Items.f_151055_);
        itemMap.put("Obtain Soul Lantern", Items.f_42779_);
        itemMap.put("Obtain Honey Bottle", Items.f_42787_);
        itemMap.put("Obtain Ancient Debris", Items.f_42792_);
        itemMap.put("Obtain Enchanted Golden Apples", Items.f_42437_);
        itemMap.put("Obtain Cake", Items.f_42502_);
        itemMap.put("Obtain Ender Chest", Items.f_42108_);
        itemMap.put("Obtain Heart of the Sea", Items.f_42716_);
        itemMap.put("Obtain Wither Skeleton Skull", Items.f_42679_);
        itemMap.put("Obtain Enchanting Table", Items.f_42100_);
        itemMap.put("Obtain Lodestone", Items.f_42790_);
        itemMap.put("Obtain End Rod", Items.f_42001_);
        itemMap.put("Obtain Sponge", Items.f_41902_);
        itemMap.put("Obtain Mushroom Stem", Items.f_42024_);
        itemMap.put("Obtain Dragon Egg", Items.f_42104_);
        itemMap.put("Obtain TNT", Items.f_41996_);
        itemMap.put("Obtain Cobweb", Items.f_41863_);
        itemMap.put("Obtain Goat Horn", Items.f_220219_);
        itemMap.put("Obtain Mud Brick Wall", Items.f_220190_);
        itemMap.put("Obtain Daylight Detector", Items.f_42152_);
        itemMap.put("Obtain Redstone Repeater", Items.f_42350_);
        itemMap.put("Obtain Redstone Comparator", Items.f_42351_);
        itemMap.put("Obtain Observer", Items.f_42264_);
        itemMap.put("Obtain Activator Rail", Items.f_42161_);
        itemMap.put("Obtain Detector Rail", Items.f_41861_);
        itemMap.put("Obtain Powered Rail", Items.f_41860_);
        itemMap.put("Obtain Dispenser", Items.f_41855_);
        itemMap.put("Obtain Piston", Items.f_41869_);
        itemMap.put("Obtain Every Type of Raw Ore Block", Items.f_150995_);
        itemMap.put("Obtain 5 Types of Saplings", Items.f_42799_);
        itemMap.put("Obtain every type of horse armor", Items.f_42653_);
        itemMap.put("Obtain Every Type of Seed", Items.f_42404_);
        itemMap.put("Obtain 6 Unique Flowers", Items.f_41948_);
        itemMap.put("Obtain 64 of Any One Item/Block", (Item)ItemInit.OF_ANYTHING_64.get());
        itemMap.put("Write a Book", Items.f_42614_);
        itemMap.put("Fill Inventory with Unique Items", Items.f_42009_);
        itemMap.put("Get \"This Boat Has Legs\" Advancement", Items.f_42685_);
        itemMap.put("Ride Pig with a Carrot on a Stick", Items.f_42684_);
        itemMap.put("Ride Horse with a Saddle", Items.f_42450_);
        itemMap.put("Ride Minecart", Items.f_42449_);
        itemMap.put("Use a Brewing Stand", Items.f_42543_);
        itemMap.put("Eat Pumpkin Pie", Items.f_42687_);
        itemMap.put("Eat a glow berry", Items.f_151079_);
        itemMap.put("Eat Rabbit Stew", Items.f_42699_);
        itemMap.put("Eat Suspicious Stew", Items.f_42718_);
        itemMap.put("Eat Cookie", Items.f_42572_);
        itemMap.put("Eat Chorus Fruit", Items.f_42730_);
        itemMap.put("Eat Poisonous Potato", Items.f_42675_);
        itemMap.put("Get Nausea", (Item)ItemInit.NAUSEA.get());
        itemMap.put("Get Jump Boost", (Item)ItemInit.JUMP_BOOST.get());
        itemMap.put("Get Absorption", (Item)ItemInit.ABSORPTION.get());
        itemMap.put("Get Levitation", (Item)ItemInit.LEVITATION.get());
        itemMap.put("Get Glowing", (Item)ItemInit.GLOWING.get());
        itemMap.put("Get Mining Fatigue", (Item)ItemInit.MINING_FATIGUE.get());
        itemMap.put("Get Bad Omen", (Item)ItemInit.BAD_OMEN.get());
        itemMap.put("Get Weakness", (Item)ItemInit.WEAKNESS.get());
        itemMap.put("Get Poisoned", (Item)ItemInit.POISON.get());
        itemMap.put("Get 3 Status Effects at Once", (Item)ItemInit.STATUS_EFFECTS_3.get());
        itemMap.put("Get 6 Status Effects at Once", (Item)ItemInit.STATUS_EFFECTS_6.get());
        itemMap.put("Remove a Status Effect with a Milk Bucket", Items.f_42455_);
        itemMap.put("Die by Bee Sting", (Item)ItemInit.DIE_TO_BEE.get());
        itemMap.put("Die by Llama", (Item)ItemInit.DIE_TO_LLAMA.get());
        itemMap.put("Die by Cactus", (Item)ItemInit.DIE_TO_CACTUS.get());
        itemMap.put("Die by Berry Bush", (Item)ItemInit.DIE_TO_SWEET_BERRIES.get());
        itemMap.put("Die by Anvil", (Item)ItemInit.DIE_TO_ANVIL.get());
        itemMap.put("Die by Firework Rocket", (Item)ItemInit.DIE_TO_FIREWORK.get());
        itemMap.put("Die to Falling Stalactite", (Item)ItemInit.Die_To_Falling_Stalactite.get());
        itemMap.put("Die by Magic", (Item)ItemInit.DIE_TO_MAGIC.get());
        itemMap.put("Die to Iron Golem", (Item)ItemInit.DIE_TO_GOLEM.get());
        itemMap.put("Die to [Intentional Game Design]", (Item)ItemInit.DIE_TO_BED.get());
        itemMap.put("Die by Falling Off Vines", (Item)ItemInit.DIE_TO_VINES.get());
        itemMap.put("Find an Ice Spike Biome", Items.f_42201_);
        itemMap.put("Find a Mushroom Biome", Items.f_42023_);
        itemMap.put("Find a Badlands Biome", Items.f_42199_);
        itemMap.put("Find a Fortress", Items.f_42095_);
        itemMap.put("Find a Bastion", Items.f_42763_);
        itemMap.put("Find a Stronghold", Items.f_42545_);
        itemMap.put("Find an End City", Items.f_42103_);
        itemMap.put("Use a Smithing Table", Items.f_42775_);
        itemMap.put("Use an Enchanting Table", Items.f_42100_);
        itemMap.put("Use an Anvil", Items.f_42146_);
        itemMap.put("Use a Composter", Items.f_42726_);
        itemMap.put("Use a Cauldron to wash something", (Item)ItemInit.USE_CAULDRON.get());
        itemMap.put("Use a Loom to Design a Banner", Items.f_42719_);
        itemMap.put("Use a Jukebox to play a Music Disc", Items.f_41984_);
        itemMap.put("An Opponent Dies", (Item)ItemInit.NO_DEATH.get());
        itemMap.put("An Opponent Dies 3 Times", (Item)ItemInit.NO_DEATH.get());
        itemMap.put("An Opponent Catches on Fire", (Item)ItemInit.NO_FIRE.get());
        itemMap.put("An Opponent Obtains a Crafting Table", (Item)ItemInit.NO_CRAFTING_TABLE.get());
        itemMap.put("An Opponent Obtains Obsidian", (Item)ItemInit.NO_OBSIDIAN.get());
        itemMap.put("An Opponent Obtains Seeds", (Item)ItemInit.NO_SEEDS.get());
        itemMap.put("An Opponent Jumps", (Item)ItemInit.NO_JUMP.get());
        itemMap.put("An Opponent Touches Water", (Item)ItemInit.NO_WATER.get());
        itemMap.put("An Opponent Takes Fall Damage", (Item)ItemInit.NO_FALL_DAMAGE.get());
        itemMap.put("An Opponent Takes 100 Total Damage", (Item)ItemInit.OPPONENT_TAKES_100_DAMAGE.get());
        itemMap.put("An Opponent is hit by a Snowball", Items.f_42452_);
        itemMap.put("An Opponent Hits You", (Item)ItemInit.OPPONENT_HITS_YOU.get());
        itemMap.put("Sleep Alone in the Overworld", Items.f_42570_);
        itemMap.put("Spawn a Chicken with an Egg", Items.f_42521_);
        itemMap.put("Reach Level 15", (Item)ItemInit.LEVEL_15.get());
        itemMap.put("Reach Level 30", (Item)ItemInit.LEVEL_30.get());
        itemMap.put("Use glow ink on a crimson sign", (Item)ItemInit.Glow_Crimson_Sign.get());
        itemMap.put("Empty Hunger Bar", (Item)ItemInit.STARVE.get());
        itemMap.put("Reach Bedrock", Items.f_41829_);
        itemMap.put("Reach Sky Limit", (Item)ItemInit.HEIGHT_LIMIT.get());
        itemMap.put("Die to TNT Minecart", Items.f_42693_);
        itemMap.put("Enrage a Zombie Piglin", (Item)ItemInit.ENRAGE_ZOMBIE_PIGLIN.get());
        itemMap.put("Take 200 Damage", (Item)ItemInit.TAKE_200_DAMAGE.get());
        itemMap.put("Kill 100 Mobs", (Item)ItemInit.KILL_100_MOBS.get());
        itemMap.put("Deal 400 Damage", (Item)ItemInit.DEAL_400_DAMAGE.get());
        itemMap.put("Sprint 1km", (Item)ItemInit.SPRINT_1KM.get());
        itemMap.put("Get any Spyglass Advancement", Items.f_151059_);
        itemMap.put("Trade with a Villager", Items.f_42616_);
        itemMap.put("Distract a Piglin with Gold", Items.f_42417_);
        itemMap.put("Visit All Nether Biomes", Items.f_42048_);
        itemMap.put("Get \"Sniper Duel\" Advancement", Items.f_42412_);
        itemMap.put("Get \"Bullseye\" Advancement", Items.f_42793_);
        itemMap.put("Charge a Respawn Anchor to Max", Items.f_42767_);
        itemMap.put("Get 15 Advancements", (Item)ItemInit.ADVANCEMENTS_15.get());
        itemMap.put("Get 25 Advancements", (Item)ItemInit.ADVANCEMENTS_25.get());
        itemMap.put("Get 35 Advancements", (Item)ItemInit.ADVANCEMENTS_35.get());
        itemMap.put("Obtain More Dried Kelp Blocks than the Opponent", (Item)ItemInit.MORE_KELP.get());
        itemMap.put("Obtain More Hoppers than the opponent", (Item)ItemInit.MORE_HOOPERS.get());
        itemMap.put("Have More Levels than the opponent", (Item)ItemInit.MORE_LEVEL.get());
        buildItemCycleMap();
    }

    public static void buildItemCycleMap() {
        itemCycleMap = new Hashtable();
        itemCyclePosition = new Hashtable();
        itemCycleMap.put("Full Set of Wooden Tools", new ArrayList(Arrays.asList(Items.f_42422_, Items.f_42423_, Items.f_42424_, Items.f_42421_, Items.f_42420_)));
        itemCyclePosition.put("Full Set of Wooden Tools", 0);
        itemCycleMap.put("Full Set of Stone Tools", new ArrayList(Arrays.asList(Items.f_42427_, Items.f_42428_, Items.f_42429_, Items.f_42426_, Items.f_42425_)));
        itemCyclePosition.put("Full Set of Stone Tools", 0);
        itemCycleMap.put("Full Set of Gold Tools", new ArrayList(Arrays.asList(Items.f_42432_, Items.f_42433_, Items.f_42434_, Items.f_42431_, Items.f_42430_)));
        itemCyclePosition.put("Full Set of Gold Tools", 0);
        itemCycleMap.put("Full Set of Iron Tools", new ArrayList(Arrays.asList(Items.f_42385_, Items.f_42386_, Items.f_42387_, Items.f_42384_, Items.f_42383_)));
        itemCyclePosition.put("Full Set of Iron Tools", 0);
        itemCycleMap.put("Full Set of Diamond Tools", new ArrayList(Arrays.asList(Items.f_42390_, Items.f_42391_, Items.f_42392_, Items.f_42389_, Items.f_42388_)));
        itemCyclePosition.put("Full Set of Diamond Tools", 0);
        itemMap.put("Obtain Every Type of Shovel", Items.f_42421_);
        itemMap.put("Obtain Every Type of Hoe", Items.f_42424_);
        itemMap.put("Obtain Every Type of Axe", Items.f_42423_);
        itemMap.put("Obtain Every Type of Pickaxe", Items.f_42422_);
        itemMap.put("Obtain Every Type of Sword", Items.f_42420_);
        itemCycleMap.put("Obtain Every Type of Shovel", new ArrayList(Arrays.asList(Items.f_42421_, Items.f_42426_, Items.f_42384_, Items.f_42431_, Items.f_42389_, Items.f_42394_)));
        itemCyclePosition.put("Obtain Every Type of Shovel", 0);
        itemCycleMap.put("Obtain Every Type of Hoe", new ArrayList(Arrays.asList(Items.f_42424_, Items.f_42429_, Items.f_42387_, Items.f_42434_, Items.f_42392_, Items.f_42397_)));
        itemCyclePosition.put("Obtain Every Type of Hoe", 0);
        itemCycleMap.put("Obtain Every Type of Axe", new ArrayList(Arrays.asList(Items.f_42423_, Items.f_42428_, Items.f_42386_, Items.f_42433_, Items.f_42391_, Items.f_42396_)));
        itemCyclePosition.put("Obtain Every Type of Axe", 0);
        itemCycleMap.put("Obtain Every Type of Pickaxe", new ArrayList(Arrays.asList(Items.f_42422_, Items.f_42427_, Items.f_42385_, Items.f_42432_, Items.f_42390_, Items.f_42395_)));
        itemCyclePosition.put("Obtain Every Type of Pickaxe", 0);
        itemCycleMap.put("Obtain Every Type of Sword", new ArrayList(Arrays.asList(Items.f_42420_, Items.f_42425_, Items.f_42383_, Items.f_42430_, Items.f_42388_, Items.f_42393_)));
        itemCyclePosition.put("Obtain Every Type of Sword", 0);
        itemCycleMap.put("Wear Leather Armor Set", new ArrayList(Arrays.asList(Items.f_42408_, Items.f_42463_, Items.f_42462_, Items.f_42407_)));
        itemCyclePosition.put("Wear Leather Armor Set", 0);
        itemCycleMap.put("Wear Gold Armor Set", new ArrayList(Arrays.asList(Items.f_42477_, Items.f_42479_, Items.f_42478_, Items.f_42476_)));
        itemCyclePosition.put("Wear Gold Armor Set", 0);
        itemCycleMap.put("Wear Iron Armor Set", new ArrayList(Arrays.asList(Items.f_42469_, Items.f_42471_, Items.f_42470_, Items.f_42468_)));
        itemCyclePosition.put("Wear Iron Armor Set", 0);
        itemCycleMap.put("Wear Diamond Armor Set", new ArrayList(Arrays.asList(Items.f_42473_, Items.f_42475_, Items.f_42474_, Items.f_42472_)));
        itemCyclePosition.put("Wear Diamond Armor Set", 0);
        itemCycleMap.put("Kill 30 Undead Mobs", new ArrayList(Arrays.asList((Item)ItemInit.KILL_ZOMBIE.get(), (Item)ItemInit.KILL_WITHER_SKELETON.get(), (Item)ItemInit.KILL_STRAY_CYCLE.get(), (Item)ItemInit.KILL_HUSK.get(), (Item)ItemInit.KILL_DROWNED.get())));
        itemCyclePosition.put("Kill 30 Undead Mobs", 0);
        itemCycleMap.put("Kill 20 Arthropods", new ArrayList(Arrays.asList((Item)ItemInit.KILL_SPIDER_CYCLE.get(), (Item)ItemInit.KILL_SILVERFISH_CYCLE.get(), (Item)ItemInit.KILL_ENDERMITE_CYCLE.get(), (Item)ItemInit.KILL_CAVE_SPIDER_CYCLE.get(), (Item)ItemInit.KILL_BEE_CYCLE.get())));
        itemCyclePosition.put("Kill 20 Arthropods", 0);
        itemCycleMap.put("Obtain 5 Types of Saplings", new ArrayList(Arrays.asList(Items.f_42799_, Items.f_42800_, Items.f_41827_, Items.f_42801_, Items.f_41828_, Items.f_41826_, Items.f_220175_)));
        itemCyclePosition.put("Obtain 5 Types of Saplings", 0);
        itemCycleMap.put("Obtain every type of horse armor", new ArrayList(Arrays.asList(Items.f_42653_, Items.f_42654_, Items.f_42652_, Items.f_42651_)));
        itemCyclePosition.put("Obtain every type of horse armor", 0);
        itemCycleMap.put("Obtain Every Type of Raw Ore Block", new ArrayList(Arrays.asList(Items.f_150995_, Items.f_150997_, Items.f_150996_)));
        itemCyclePosition.put("Obtain Every Type of Raw Ore Block", 0);
        itemCycleMap.put("Obtain Every Type of Seed", new ArrayList(Arrays.asList(Items.f_42404_, Items.f_42733_, Items.f_42578_, Items.f_42577_)));
        itemCyclePosition.put("Obtain Every Type of Seed", 0);
        itemCycleMap.put("Obtain 6 Unique Flowers", new ArrayList(Arrays.asList(Items.f_41948_, Items.f_41939_, Items.f_41940_, Items.f_41941_, Items.f_41942_, Items.f_41943_, Items.f_41944_, Items.f_41945_, Items.f_41946_, Items.f_41947_, Items.f_41949_, Items.f_41950_, Items.f_41951_, Items.f_42206_, Items.f_42207_, Items.f_42208_, Items.f_42209_)));
        itemCyclePosition.put("Obtain 6 Unique Flowers", 0);
        itemCycleMap.put("Die to [Intentional Game Design]", new ArrayList(Arrays.asList((Item)ItemInit.DIE_TO_BED.get(), (Item)ItemInit.DIE_TO_ANCHOR.get())));
        itemCyclePosition.put("Die to [Intentional Game Design]", 0);
        itemCycleMap.put("Visit All Nether Biomes", new ArrayList(Arrays.asList(Items.f_42048_, Items.f_42049_, Items.f_42541_, Items.f_42488_)));
        itemCyclePosition.put("Visit All Nether Biomes", 0);
    }

    public static void cycleBingoInventory() {
        for(int goal_index = 0; goal_index < goals.size(); ++goal_index) {
            if (itemCycleMap.containsKey(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)) {
                int pos = (Integer)itemCyclePosition.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal);
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Kill 30 Undead Mobs")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).get(pos), 30));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Kill 20 Arthropods")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).get(pos), 20));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Obtain 6 Unique Flowers")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).get(pos), 6));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Obtain 5 Types of Saplings")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).get(pos), 5));
                } else {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).get(pos)));
                }

                pos = pos + 1 >= ((List)itemCycleMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)).size() ? 0 : pos + 1;
                itemCyclePosition.put(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal, pos);
                bingoInventory.m_8020_(goal_index).m_41714_(Component.m_237113_(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal));
            }
        }

    }

    public static void buildBingoInventory() {
        tileColoring = new ArrayList();
        teamScore = 0;
        oppScore = 0;
        if (itemMap == null) {
            buildItemMapping();
        }

        for(int goal_index = 0; goal_index < goals.size(); ++goal_index) {
            String goal_name = ((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal;
            if (goal_name.equals("An Opponent Jumps")) {
                checkJump = ((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).player_complete.equals("");
            }

            String player = ((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).player_complete;
            int team;
            if (team1.contains(player)) {
                team = 1;
            } else {
                team = 2;
            }

            int tileColor;
            if (player.equals("")) {
                tileColor = 0;
            } else {
                tileColor = team;
                if (clientPlayersTeamNum == team) {
                    ++teamScore;
                } else {
                    ++oppScore;
                }
            }

            tileColoring.add(tileColor);
            if (itemMap.containsKey(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)) {
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Kill 30 Undead Mobs")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 30));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Kill 20 Arthropods")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 20));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Obtain 6 Unique Flowers")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 6));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Obtain 5 Types of Saplings")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 5));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.startsWith("Obtain 64") && !((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Any One Item/Block")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 64));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("An Opponent Takes 100 Total Damage")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 100));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("An Opponent Dies 3 Times")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 3));
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.equals("Kill 100 Mobs")) {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal), 100));
                } else {
                    bingoInventory.m_6836_(goal_index, new ItemStack((ItemLike)itemMap.get(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal)));
                }
            } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Brew Potion")) {
                ItemStack itemStack = new ItemStack(Items.f_42589_);
                List<Component> list1 = Lists.newArrayList();
                switch (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal) {
                    case "Brew Potion of Healing":
                        PotionUtils.m_43549_(itemStack, Potions.f_43623_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Leaping":
                        PotionUtils.m_43549_(itemStack, Potions.f_43607_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Swiftness":
                        PotionUtils.m_43549_(itemStack, Potions.f_43612_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Invisibility":
                        PotionUtils.m_43549_(itemStack, Potions.f_43605_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Water Breathing":
                        PotionUtils.m_43549_(itemStack, Potions.f_43621_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Slow Falling":
                        PotionUtils.m_43549_(itemStack, Potions.f_43596_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                        break;
                    case "Brew Potion of Lingering":
                        itemStack = new ItemStack(Items.f_42739_);
                        bingoInventory.m_6836_(goal_index, itemStack);
                }
            } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Colored Leather")) {
                ItemStack itemStack;
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Helmet")) {
                    itemStack = new ItemStack(Items.f_42407_);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Chestplate")) {
                    itemStack = new ItemStack(Items.f_42408_);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Leggings")) {
                    itemStack = new ItemStack(Items.f_42462_);
                } else {
                    if (!((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Boots")) {
                        continue;
                    }

                    itemStack = new ItemStack(Items.f_42463_);
                }

                for(int color_index = 0; color_index < LockoutGoals.colors.length; ++color_index) {
                    String var10001 = LockoutGoals.colors[color_index];
                    if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains(var10001)) {
                        itemStack = DyeableLeatherItem.m_41118_(itemStack, new ArrayList(List.of((DyeItem)LockoutGoals.dye_item_map[color_index])));
                        break;
                    }
                }

                bingoInventory.m_6836_(goal_index, itemStack);
            } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Unique Animals")) {
                ItemStack itemStack = new ItemStack((ItemLike)ItemInit.BREED_X.get());
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("4")) {
                    itemStack.m_41764_(4);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("6")) {
                    itemStack.m_41764_(6);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("8")) {
                    itemStack.m_41764_(8);
                }

                bingoInventory.m_6836_(goal_index, itemStack);
            } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Unique Hostile Mobs")) {
                ItemStack itemStack = new ItemStack((ItemLike)ItemInit.KILL_X_UNIQUE.get());
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("7")) {
                    itemStack.m_41764_(7);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("10")) {
                    itemStack.m_41764_(10);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("15")) {
                    itemStack.m_41764_(15);
                }

                bingoInventory.m_6836_(goal_index, itemStack);
            } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("Unique Foods")) {
                ItemStack itemStack = new ItemStack((ItemLike)ItemInit.EAT_X_UNIQUE.get());
                if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("5")) {
                    itemStack.m_41764_(5);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("10")) {
                    itemStack.m_41764_(10);
                } else if (((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal.contains("20")) {
                    itemStack.m_41764_(20);
                }

                bingoInventory.m_6836_(goal_index, itemStack);
            }

            bingoInventory.m_8020_(goal_index).m_41714_(Component.m_237113_(((PacketLockoutBingoInfo.GoalTuple)goals.get(goal_index)).goal));
        }

    }

    public static void startClientBingo(boolean isTestMode, int timer, int team1Color, int team2Color, int numPlayersPerTeam, ArrayList<String> team1, ArrayList<String> team2, int numOfGoals, ArrayList<PacketLockoutBingoInfo.GoalTuple> goals) {
        Minecraft.m_91087_().f_91074_.m_108739_("Client Setup");
        clientPlayerEntity = Minecraft.m_91087_().f_91074_;
        clientPlayerEntity.f_108591_ = new StatsCounter();
        LockoutBingoClientInfo.isTestMode = isTestMode;
        timerLeft = timer;
        team1ColorValue = team1Color;
        team2ColorValue = team2Color;
        checkJump = false;
        LockoutBingoClientInfo.team1 = new ArrayList(team1);
        LockoutBingoClientInfo.team2 = new ArrayList(team2);
        if (team1.contains(clientPlayerEntity.m_5446_().getString())) {
            clientPlayersTeamNum = 1;
        } else {
            clientPlayersTeamNum = 2;
        }

        LockoutBingoClientInfo.numOfGoals = numOfGoals;
        LockoutBingoClientInfo.goals = new ArrayList(goals);
        bingoInventory = new MainBingoTileEntity(BlockPos.f_121853_, (BlockState)null, LockoutBingoClientInfo.numOfGoals);
        buildBingoInventory();
        if (bingoGUI == null) {
            bingoGUI = new BingoIngameGui(Minecraft.m_91087_());
        }

    }

    public static void setupFakeClientBingo(boolean isTestMode, int team1Color, int team2Color, int numPlayersPerTeam, ArrayList<String> team1, ArrayList<String> team2, int numOfGoals, ArrayList<PacketLockoutBingoInfo.GoalTuple> goals) {
        LockoutBingoClientInfo.isTestMode = isTestMode;
        checkJump = false;
        LockoutBingoClientInfo.team1 = new ArrayList(team1);
        LockoutBingoClientInfo.team2 = new ArrayList(team2);
        clientPlayersTeamNum = 1;
        team1ColorValue = team1Color;
        team2ColorValue = team2Color;
        LockoutBingoClientInfo.numOfGoals = numOfGoals;
        LockoutBingoClientInfo.goals = new ArrayList(goals);
        bingoInventory = new MainBingoTileEntity(BlockPos.f_121853_, (BlockState)null, LockoutBingoClientInfo.numOfGoals);
        buildBingoInventory();
    }

    public static void updateClientBingoInfo(int numOfGoals, ArrayList<PacketLockoutBingoInfo.GoalTuple> goals) {
        LockoutBingoClientInfo.numOfGoals = numOfGoals;
        LockoutBingoClientInfo.goals = new ArrayList(goals);
        bingoInventory = new MainBingoTileEntity(BlockPos.f_121853_, (BlockState)null, numOfGoals);
        buildBingoInventory();
    }

    public static void playSoundEffect(PacketPlaySoundEffect.SoundsEffect soundsEffect) {
        LockoutBingoMod.LOGGER.info("Received Request to play: " + soundsEffect.name());
        Minecraft.m_91087_().f_91074_.m_20225_(false);
        switch (soundsEffect) {
            case win -> Minecraft.m_91087_().f_91074_.m_5496_(SoundEvents.f_12497_, 1.0F, 1.0F);
            case lose -> Minecraft.m_91087_().f_91074_.m_5496_(SoundEvents.f_12308_, 1.0F, 1.0F);
            case team_complete -> Minecraft.m_91087_().f_91074_.m_5496_(SoundEvents.f_12275_, 1.0F, 1.0F);
            case opp_complete -> Minecraft.m_91087_().f_91074_.m_5496_(SoundEvents.f_12002_, 1.0F, 1.0F);
            case match_point -> Minecraft.m_91087_().f_91074_.m_5496_(SoundEvents.f_215699_, 1.0F, 1.0F);
        }

    }
}
