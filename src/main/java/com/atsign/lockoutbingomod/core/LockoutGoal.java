package com.atsign.lockoutbingomod.core;

public class LockoutGoal {
    private final String task_name;
    private final Tasks task;
    private final String description;
    private final int difficulty;
    private final int end;
    private final int tools;
    private final int diamond;
    private final int netherite;
    private final int armor;
    private final int armor_special;
    private final int tame;
    private final int kill_1_mob;
    private final int kill_x_hostile;
    private final int kill_type;
    private final int fortress;
    private final int horse;
    private final int breed1;
    private final int breed2;
    private final int breedX;
    private final int ride;
    private final int eat;
    private final int eatX;
    private final int effect;
    private final int effectX;
    private final int die;
    private final int biome;
    private final int end_structure;
    private final int opponent_hard;
    private final int opponenent_hurts;
    private final int player_dies;
    private final int damage;
    private final int advancement;
    private final int village;
    private final int monument;
    private final int redstone;
    private final int tnt;
    private final int obtain_more;
    private final int strider;
    private final int sheep;
    private final int punch;
    private final int collect;
    private final int poison;
    private final int dragon;
    private final int kill100;
    private final int seed;

    private LockoutGoal(LockoutGoalBuilder builder) {
        this.task_name = builder.task_name;
        this.task = builder.task;
        this.description = builder.description;
        this.difficulty = builder.difficulty;
        this.end = builder.end;
        this.tools = builder.tools;
        this.diamond = builder.diamond;
        this.netherite = builder.netherite;
        this.armor = builder.armor;
        this.armor_special = builder.armor_special;
        this.tame = builder.tame;
        this.kill_1_mob = builder.kill_1_mob;
        this.kill_x_hostile = builder.kill_x_hostile;
        this.kill_type = builder.kill_type;
        this.fortress = builder.fortress;
        this.horse = builder.horse;
        this.breed1 = builder.breed1;
        this.breed2 = builder.breed2;
        this.breedX = builder.breedX;
        this.ride = builder.ride;
        this.eat = builder.eat;
        this.eatX = builder.eatX;
        this.effect = builder.effect;
        this.effectX = builder.effectX;
        this.die = builder.die;
        this.biome = builder.biome;
        this.end_structure = builder.end_structure;
        this.opponent_hard = builder.opponent_hard;
        this.opponenent_hurts = builder.opponenent_hurts;
        this.player_dies = builder.player_dies;
        this.damage = builder.damage;
        this.advancement = builder.advancement;
        this.village = builder.village;
        this.monument = builder.monument;
        this.redstone = builder.redstone;
        this.tnt = builder.tnt;
        this.obtain_more = builder.obtain_more;
        this.strider = builder.strider;
        this.sheep = builder.sheep;
        this.punch = builder.punch;
        this.collect = builder.collect;
        this.poison = builder.poison;
        this.dragon = builder.dragon;
        this.kill100 = builder.kill100;
        this.seed = builder.seed;
    }

    public String getTaskName() {
        return this.task_name;
    }

    public Tasks getTask() {
        return this.task;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDifficulty() {
        return this.difficulty;
    }

    public int getEnd() {
        return this.end;
    }

    public int getTools() {
        return this.tools;
    }

    public int getDiamond() {
        return this.diamond;
    }

    public int getNetherite() {
        return this.netherite;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getArmorSpecial() {
        return this.armor_special;
    }

    public int getTame() {
        return this.tame;
    }

    public int getKill_1_mob() {
        return this.kill_1_mob;
    }

    public int getKill_x_hostile() {
        return this.kill_x_hostile;
    }

    public int getKill_type() {
        return this.kill_type;
    }

    public int getFortress() {
        return this.fortress;
    }

    public int getHorse() {
        return this.horse;
    }

    public int getBreed1() {
        return this.breed1;
    }

    public int getBreed2() {
        return this.breed2;
    }

    public int getBreedX() {
        return this.breedX;
    }

    public int getRide() {
        return this.ride;
    }

    public int getEat() {
        return this.eat;
    }

    public int getEatX() {
        return this.eatX;
    }

    public int getEffect() {
        return this.effect;
    }

    public int getEffectX() {
        return this.effectX;
    }

    public int getDie() {
        return this.die;
    }

    public int getBiome() {
        return this.biome;
    }

    public int getEnd_structure() {
        return this.end_structure;
    }

    public int getOpponent_hard() {
        return this.opponent_hard;
    }

    public int getOpponenent_hurts() {
        return this.opponenent_hurts;
    }

    public int getPlayer_dies() {
        return this.player_dies;
    }

    public int getDamage() {
        return this.damage;
    }

    public int getAdvancement() {
        return this.advancement;
    }

    public int getVillage() {
        return this.village;
    }

    public int getMonument() {
        return this.monument;
    }

    public int getRedstone() {
        return this.redstone;
    }

    public int getTnt() {
        return this.tnt;
    }

    public int getObtain_more() {
        return this.obtain_more;
    }

    public int getStrider() {
        return this.strider;
    }

    public int getSheep() {
        return this.sheep;
    }

    public int getPunch() {
        return this.punch;
    }

    public int getCollect() {
        return this.collect;
    }

    public int getPoison() {
        return this.poison;
    }

    public int getDragon() {
        return this.dragon;
    }

    public int getKill100() {
        return this.kill100;
    }

    public int getSeed() {
        return this.seed;
    }

    public static enum Tasks {
        mine_diamond_ore,
        mine_emerald_ore,
        mine_mob_spawner,
        mine_turtle_egg,
        enter_nether,
        enter_end,
        kill_ender_dragon,
        full_set_wooden_tools,
        full_set_stone_tools,
        full_set_gold_tools,
        full_set_iron_tools,
        full_set_diamond_tools,
        obtain_every_type_of_shovel,
        obtain_every_type_of_hoe,
        obtain_every_type_of_axe,
        obtain_every_type_of_pickaxe,
        obtain_every_type_of_sword,
        fill_armor_stand,
        leather_armor_set,
        gold_armor_set,
        iron_armor_set,
        diamond_armor_set,
        one_piece_netherite_armor,
        one_piece_chain_armor,
        wear_carved_pumpkin,
        wear_colored_leather_armor,
        tame_cat,
        tame_horse,
        tame_parrot,
        tame_wolf,
        breed_cow,
        breed_sheep,
        breed_chicken,
        breed_pig,
        breed_horse,
        breed_hoglin,
        breed_ocelot,
        breed_rabbit,
        breed_fox,
        breed_strider,
        breed_goat,
        breed_4_unique,
        breed_6_unique,
        breed_8_unique,
        kill_witch,
        kill_zombie_villager,
        kill_stray,
        kill_zoglin,
        kill_silverfish,
        kill_guardian,
        kill_ghast,
        kill_snow_golem,
        kill_elder_guardian,
        kill_endermite,
        kill_colored_sheep,
        kill_7_unique_hostile,
        kill_10_unique_hostile,
        kill_15_unique_hostile,
        kill_30_undead,
        kill_20_arthropods,
        kill_the_other_player,
        obtain_red_nether_brick_stairs,
        obtain_bucket_of_tropical_fish,
        obtain_sea_lantern,
        obtain_bookshelf,
        obtain_mossy_stone_brick_wall,
        obtain_flowering_azalea,
        obtain_scaffolding,
        obtain_end_crystal,
        obtain_bell,
        obtain_bottle_o_enchanting,
        obtain_slime_block,
        obtain_a_powder_snow_bucket,
        obtain_soul_lantern,
        obtain_honey_bottle,
        obtain_ancient_debris,
        obtain_cake,
        obtain_ender_chest,
        obtain_heart_of_the_sea,
        obtain_wither_skeleton_skull,
        obtain_lodestone,
        obtain_end_rod,
        obtain_sponge,
        obtain_mushroom_stem,
        obtain_dragon_egg,
        obtain_tnt,
        obtain_cobweb,
        obtain_goat_horn,
        obtain_mud_brick_wall,
        obtain_daylight_detector,
        obtain_redstone_repeater,
        obtain_redstone_comparator,
        obtain_observer,
        obtain_activator_rail,
        obtain_detector_rail,
        obtain_powered_rail,
        obtain_dispenser,
        obtain_piston,
        obtain_every_type_of_raw_ore,
        obtain_every_type_of_sapling,
        obtain_every_type_horse_armor,
        obtain_every_type_of_seed,
        obtain_6_unique_flowers,
        obtain_glazed_terracotta,
        obtain_64_of_one_item_block,
        obtain_64_x_wool,
        obtain_64_x_concrete,
        write_book,
        fill_inventory_with_unique_items,
        this_boat_has_legs_strider,
        ride_pig_with_carrot_on_stick,
        ride_horse_with_saddle,
        ride_minecart,
        use_a_brewing_stand,
        brew_potion_of_healing,
        brew_potion_of_leaping,
        brew_potion_of_swiftness,
        brew_potion_of_invisibility,
        brew_potion_of_water_breathing,
        brew_potion_of_lingering,
        eat_pumpkin_pie,
        eat_a_glow_berry,
        eat_rabbit_stew,
        eat_suspicious_stew,
        eat_cookie,
        eat_chorus_fruit,
        eat_poisonous_potato,
        eat_5_unique_food,
        eat_10_unique_food,
        eat_20_unique_food,
        get_nausea,
        get_jump_boost,
        get_absorption,
        get_levitation,
        get_glowing,
        get_mining_fatigue,
        get_bad_omen,
        get_weakness,
        get_poisoned,
        get_3_status_effects_at_once,
        get_6_status_effects_at_once,
        remove_status_effect_with_milk,
        die_by_bee_sting,
        die_by_llama,
        die_by_cactus,
        die_by_berry_bush,
        die_by_anvil,
        die_by_firework_rocket,
        die_to_falling_stalactite,
        die_by_magic,
        die_to_iron_golem,
        die_to_intentional_game_design,
        die_by_falling_off_vines,
        find_ice_spike_biome,
        find_mushroom_biome,
        find_badlands_biome,
        find_fortress,
        find_bastion,
        find_stronghold,
        find_end_city,
        use_smithing_table,
        use_enchanting_table,
        use_anvil,
        use_composter,
        use_cauldron_to_wash,
        use_loom_to_design_banner,
        use_jukebox_to_play_music,
        opponent_dies,
        opponent_dies_3_times,
        opponent_catches_fire,
        opponent_obtains_crafting_table,
        oponnent_obtains_obsidian,
        oponnent_obtains_seeds,
        opponent_jumps,
        opponent_touches_water,
        opponent_takes_fall_damage,
        opponent_takes_100_total_damage,
        opponent_is_hit_by_snowball,
        opponent_hits_you,
        sleep_alone_in_overworld,
        spawn_a_chicken_with_an_egg,
        reach_level_15,
        reach_level_30,
        use_glow_ink_on_crimson_sign,
        empty_huger_bar,
        reach_bedrock,
        reach_sky_limit,
        detonate_tnt_minecart,
        enrage_zombie_piglin,
        take_200_damage,
        kill_100_mobs,
        deal_400_damage,
        sprint_1k,
        get_any_spyglass_advancement,
        trade_with_villager,
        distract_piglin,
        visit_all_nether_biomes,
        get_sniper_duel,
        get_bullseye,
        charge_respawn_anchor_to_max,
        get_15_advancements,
        get_25_advancements,
        get_35_advancements,
        obtain_more_dried_kelp_than_opponent,
        obtain_more_hoppers_than_opponent,
        have_more_levels_than_opponent;

        private Tasks() {
        }
    }

    public static class LockoutGoalBuilder {
        private static final int ZERO = 0;
        private final String task_name;
        private final Tasks task;
        private final String description;
        private final int difficulty;
        private int end = 0;
        private int tools = 0;
        private int diamond = 0;
        private int netherite = 0;
        private int armor = 0;
        private int armor_special = 0;
        private int tame = 0;
        private int kill_1_mob = 0;
        private int kill_x_hostile = 0;
        private int kill_type = 0;
        private int fortress = 0;
        private int horse = 0;
        private int breed1 = 0;
        private int breed2 = 0;
        private int breedX = 0;
        private int ride = 0;
        private int eat = 0;
        private int eatX = 0;
        private int effect = 0;
        private int effectX = 0;
        private int die = 0;
        private int biome = 0;
        private int end_structure = 0;
        private int opponent_hard = 0;
        private int opponenent_hurts = 0;
        private int player_dies = 0;
        private int damage = 0;
        private int advancement = 0;
        private int village = 0;
        private int monument = 0;
        private int redstone = 0;
        private int tnt = 0;
        private int obtain_more = 0;
        private int strider = 0;
        private int sheep = 0;
        private int punch = 0;
        private int collect = 0;
        private int poison = 0;
        private int dragon = 0;
        private int kill100 = 0;
        private int seed = 0;

        public LockoutGoalBuilder(String task_name, Tasks task, String description, int difficulty) {
            this.task_name = task_name;
            this.task = task;
            this.description = description;
            this.difficulty = difficulty;
        }

        public LockoutGoalBuilder end(int end) {
            this.end = end;
            return this;
        }

        public LockoutGoalBuilder tools(int tools) {
            this.tools = tools;
            return this;
        }

        public LockoutGoalBuilder diamond(int diamond) {
            this.diamond = diamond;
            return this;
        }

        public LockoutGoalBuilder netherite(int netherite) {
            this.netherite = netherite;
            return this;
        }

        public LockoutGoalBuilder armor(int armor) {
            this.armor = armor;
            return this;
        }

        public LockoutGoalBuilder specialarmor(int armor_special) {
            this.armor_special = armor_special;
            return this;
        }

        public LockoutGoalBuilder tame(int tame) {
            this.tame = tame;
            return this;
        }

        public LockoutGoalBuilder kill_1_mob(int kill_1_mob) {
            this.kill_1_mob = kill_1_mob;
            return this;
        }

        public LockoutGoalBuilder kill_x_hostile(int kill_x_hostile) {
            this.kill_x_hostile = kill_x_hostile;
            return this;
        }

        public LockoutGoalBuilder kill_type(int kill_type) {
            this.kill_type = kill_type;
            return this;
        }

        public LockoutGoalBuilder fortress(int fortress) {
            this.fortress = fortress;
            return this;
        }

        public LockoutGoalBuilder horse(int horse) {
            this.horse = horse;
            return this;
        }

        public LockoutGoalBuilder breed1(int breed1) {
            this.breed1 = breed1;
            return this;
        }

        public LockoutGoalBuilder breed2(int breed2) {
            this.breed2 = breed2;
            return this;
        }

        public LockoutGoalBuilder breedX(int breedX) {
            this.breedX = breedX;
            return this;
        }

        public LockoutGoalBuilder ride(int ride) {
            this.ride = ride;
            return this;
        }

        public LockoutGoalBuilder eat(int eat) {
            this.eat = eat;
            return this;
        }

        public LockoutGoalBuilder eatX(int eatX) {
            this.eatX = eatX;
            return this;
        }

        public LockoutGoalBuilder effect(int effect) {
            this.effect = effect;
            return this;
        }

        public LockoutGoalBuilder effectX(int effectX) {
            this.effectX = effectX;
            return this;
        }

        public LockoutGoalBuilder die(int die) {
            this.die = die;
            return this;
        }

        public LockoutGoalBuilder biome(int biome) {
            this.biome = biome;
            return this;
        }

        public LockoutGoalBuilder end_structure(int end_structure) {
            this.end_structure = end_structure;
            return this;
        }

        public LockoutGoalBuilder opponent_hard(int opponent_hard) {
            this.opponent_hard = opponent_hard;
            return this;
        }

        public LockoutGoalBuilder opponenent_hurts(int opponenent_hurts) {
            this.opponenent_hurts = opponenent_hurts;
            return this;
        }

        public LockoutGoalBuilder player_dies(int player_dies) {
            this.player_dies = player_dies;
            return this;
        }

        public LockoutGoalBuilder damage(int damage) {
            this.damage = damage;
            return this;
        }

        public LockoutGoalBuilder advancement(int advancement) {
            this.advancement = advancement;
            return this;
        }

        public LockoutGoalBuilder village(int village) {
            this.village = village;
            return this;
        }

        public LockoutGoalBuilder monument(int monument) {
            this.monument = monument;
            return this;
        }

        public LockoutGoalBuilder redstone(int redstone) {
            this.redstone = redstone;
            return this;
        }

        public LockoutGoalBuilder tnt(int tnt) {
            this.tnt = tnt;
            return this;
        }

        public LockoutGoalBuilder obtain_more(int obtain_more) {
            this.obtain_more = obtain_more;
            return this;
        }

        public LockoutGoalBuilder strider(int strider) {
            this.strider = strider;
            return this;
        }

        public LockoutGoalBuilder sheep(int sheep) {
            this.sheep = sheep;
            return this;
        }

        public LockoutGoalBuilder punch(int punch) {
            this.punch = punch;
            return this;
        }

        public LockoutGoalBuilder collect(int collect) {
            this.collect = collect;
            return this;
        }

        public LockoutGoalBuilder poison(int poison) {
            this.poison = poison;
            return this;
        }

        public LockoutGoalBuilder dragon(int dragon) {
            this.dragon = dragon;
            return this;
        }

        public LockoutGoalBuilder kill100(int kill100) {
            this.kill100 = kill100;
            return this;
        }

        public LockoutGoalBuilder seed(int seed) {
            this.seed = seed;
            return this;
        }

        public LockoutGoal build() {
            return new LockoutGoal(this);
        }
    }
}
