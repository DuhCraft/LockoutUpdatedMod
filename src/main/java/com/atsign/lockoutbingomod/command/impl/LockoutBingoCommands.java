package com.atsign.lockoutbingomod.command.impl;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import com.atsign.lockoutbingomod.core.LockoutBingoSaveData;
import com.atsign.lockoutbingomod.core.LockoutBingoSetup;
import com.atsign.lockoutbingomod.event.LinkedHeathServerEvents;
import com.atsign.lockoutbingomod.event.ModServerEvents;
import com.atsign.lockoutbingomod.network.Networking;
import com.atsign.lockoutbingomod.network.PacketBingoLocation;
import com.atsign.lockoutbingomod.network.PacketLockoutBingoStartClient;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.TeamArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkHooks;

public class LockoutBingoCommands {
    public static int blue_color = 42474;
    public static int orange_color = 16742656;
    public static boolean reset_player = false;
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_DIFFICULTY = (p_138424_, p_138425_) -> SharedSuggestionProvider.m_82967_(new String[]{"Easy", "Balanced", "Hard", "Random"}, p_138425_);
    private static final SuggestionProvider<CommandSourceStack> SUGGEST_LINK = (p_138424_, p_138425_) -> SharedSuggestionProvider.m_82967_(new String[]{"Team1", "Team2", "All"}, p_138425_);

    public LockoutBingoCommands() {
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("Lockout").then(Commands.m_82129_("player1", EntityArgument.m_91466_()).then(Commands.m_82129_("player2", EntityArgument.m_91466_()).executes((source) -> StartBingo((CommandSourceStack)source.getSource(), EntityArgument.m_91474_(source, "player1"), EntityArgument.m_91474_(source, "player2")))))).then(Commands.m_82127_("teams").then(Commands.m_82129_("team1", TeamArgument.m_112088_()).then(Commands.m_82129_("team2", TeamArgument.m_112088_()).executes((source) -> StartBingoMulti((CommandSourceStack)source.getSource(), TeamArgument.m_112091_(source, "team1"), TeamArgument.m_112091_(source, "team2")))))));
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("Blackout").then(Commands.m_82129_("player1", EntityArgument.m_91466_()).executes((source) -> StartBlackout((CommandSourceStack)source.getSource(), EntityArgument.m_91474_(source, "player1"))))).then(Commands.m_82127_("team").then(Commands.m_82129_("team1", TeamArgument.m_112088_()).executes((source) -> StartBlackout((CommandSourceStack)source.getSource(), TeamArgument.m_112091_(source, "team1"))))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("startTestBingo").then(Commands.m_82129_("player1", EntityArgument.m_91466_()).then(Commands.m_82129_("player2", EntityArgument.m_91466_()).executes((source) -> StartTestBingo((CommandSourceStack)source.getSource(), EntityArgument.m_91474_(source, "player1"), EntityArgument.m_91474_(source, "player2"))))));
        dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.m_82127_("LockoutResume").then(Commands.m_82129_("player1", EntityArgument.m_91466_()).then(Commands.m_82129_("player2", EntityArgument.m_91466_()).executes((source) -> ResumeBingo((CommandSourceStack)source.getSource(), EntityArgument.m_91474_(source, "player1"), EntityArgument.m_91474_(source, "player2")))))).then(Commands.m_82127_("teams").then(Commands.m_82129_("team1", TeamArgument.m_112088_()).then(Commands.m_82129_("team2", TeamArgument.m_112088_()).executes((source) -> ResumeMultiBingo((CommandSourceStack)source.getSource(), TeamArgument.m_112091_(source, "team1"), TeamArgument.m_112091_(source, "team2")))))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutSetStartTime").then(Commands.m_82129_("startTime", IntegerArgumentType.integer(1)).executes((source) -> SetBingoStartTime((CommandSourceStack)source.getSource(), IntegerArgumentType.getInteger(source, "startTime")))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutTimer").then(Commands.m_82129_("timer_in_minutes", IntegerArgumentType.integer(1)).executes((source) -> SetBingoTimer((CommandSourceStack)source.getSource(), IntegerArgumentType.getInteger(source, "timer_in_minutes")))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutLocation").then(Commands.m_82129_("location_id", IntegerArgumentType.integer(1, 4)).executes((source) -> ChangeBingoLocation((CommandSourceStack)source.getSource(), ((CommandSourceStack)source.getSource()).m_81375_(), IntegerArgumentType.getInteger(source, "location_id")))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutLinkHealth").then(Commands.m_82129_("health", StringArgumentType.greedyString()).suggests(SUGGEST_LINK).executes((source) -> {
            String health = StringArgumentType.getString(source, "health");
            if (health.equals("Team1")) {
                return StartTeamHealthLink((CommandSourceStack)source.getSource(), 1);
            } else if (health.equals("Team2")) {
                return StartTeamHealthLink((CommandSourceStack)source.getSource(), 2);
            } else {
                return health.equals("All") ? StartTeamHealthLink((CommandSourceStack)source.getSource(), -1) : 1;
            }
        })));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutDifficulty").then(Commands.m_82129_("difficulty", StringArgumentType.greedyString()).suggests(SUGGEST_DIFFICULTY).executes((source) -> SetLockoutDifficulty((CommandSourceStack)source.getSource(), StringArgumentType.getString(source, "difficulty")))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("LockoutBiomeStructureCheck").then(Commands.m_82129_("doBiomeStructureCheck", BoolArgumentType.bool()).executes((source) -> SetBiomeStructureCheck((CommandSourceStack)source.getSource(), BoolArgumentType.getBool(source, "doBiomeStructureCheck")))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("GiveGoal").then(Commands.m_82129_("player", EntityArgument.m_91466_()).then(Commands.m_82129_("goal_index", IntegerArgumentType.integer(1, 25)).executes((source) -> GiveBingoGoal((CommandSourceStack)source.getSource(), EntityArgument.m_91474_(source, "player"), IntegerArgumentType.getInteger(source, "goal_index"))))));
        dispatcher.register((LiteralArgumentBuilder)Commands.m_82127_("RemoveGoal").then(Commands.m_82129_("goal_index", IntegerArgumentType.integer(1, 25)).executes((source) -> ClearBingoGoal((CommandSourceStack)source.getSource(), IntegerArgumentType.getInteger(source, "goal_index")))));
    }

    private static int SetBingoStartTime(CommandSourceStack source, int startTime) {
        ModServerEvents.waitTime = startTime;
        source.m_81354_(Component.m_237115_("commands.setStartTime"), true);
        return 1;
    }

    private static int StartTeamHealthLink(CommandSourceStack source, int team_num) {
        LockoutBingoSetup.currentLinkedTeam = team_num;
        source.m_81354_(Component.m_237115_("commands.initLinkedHealth"), true);
        return 1;
    }

    private static int SetBingoTimer(CommandSourceStack source, int timer) {
        ModServerEvents.GameTimer = timer * 60;
        source.m_81354_(Component.m_237115_("commands.setGameLength"), true);
        return 1;
    }

    private static int SetBiomeStructureCheck(CommandSourceStack source, Boolean doCheck) {
        LockoutBingoSetup.doBiomeStructureCheck = doCheck;
        source.m_81354_(Component.m_237115_("commands.setBiomeStructureCheck"), true);
        return 1;
    }

    private static int SetLockoutDifficulty(CommandSourceStack source, String difficulty) {
        if (difficulty.equals("Easy")) {
            LockoutBingoSetup.limit_num_of_diff_one = 20;
            LockoutBingoSetup.limit_num_of_diff_two = 15;
            LockoutBingoSetup.limit_num_of_diff_three = 2;
        } else if (difficulty.equals("Balanced")) {
            LockoutBingoSetup.limit_num_of_diff_one = 10;
            LockoutBingoSetup.limit_num_of_diff_two = 16;
            LockoutBingoSetup.limit_num_of_diff_three = 7;
        } else if (difficulty.equals("Hard")) {
            LockoutBingoSetup.limit_num_of_diff_one = 5;
            LockoutBingoSetup.limit_num_of_diff_two = 20;
            LockoutBingoSetup.limit_num_of_diff_three = 15;
        } else {
            if (!difficulty.equals("Random")) {
                source.m_81352_(Component.m_237115_("commands.setDifficultyFailed"));
                return 1;
            }

            LockoutBingoSetup.limit_num_of_diff_one = 1000;
            LockoutBingoSetup.limit_num_of_diff_two = 1000;
            LockoutBingoSetup.limit_num_of_diff_three = 1000;
        }

        source.m_81354_(Component.m_237115_("commands.setDifficulty"), true);
        return 1;
    }

    private static void resetPlayer(ServerPlayer player) {
        player.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
        player.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player.m_36324_().m_38705_(20);
        player.m_21153_(player.m_21233_());
        player.m_150109_().m_6211_();
        player.f_36078_ = 0;
        player.f_36079_ = 0;
        player.f_36080_ = 0.0F;
        reset_player = true;
        player.f_8909_ = player.m_20194_().m_6846_().m_11296_(player);
        player.f_8910_ = player.m_20194_().m_6846_().m_11239_(player);
        reset_player = false;
    }

    private static void clearLockout(String path) {
        LockoutBingoSetup.team1.clear();
        LockoutBingoSetup.team2.clear();
        Path filenameEat = Paths.get(path, "lockoutEatData.txt");
        Path filenameBreed = Paths.get(path, "lockoutBreedData.txt");
        Path filenameAdvancement = Paths.get(path, "lockoutAdvancementData.txt");

        try {
            Files.deleteIfExists(filenameEat);
        } catch (IOException var7) {
        }

        try {
            Files.deleteIfExists(filenameBreed);
        } catch (IOException var6) {
        }

        try {
            Files.deleteIfExists(filenameAdvancement);
        } catch (IOException var5) {
        }

        ModServerEvents.resetClockInfo();
    }

    private static int StartBingo(CommandSourceStack source, ServerPlayer player1, ServerPlayer player2) {
        clearLockout(player1.m_20194_().m_6237_().toString());
        LockoutBingoSetup.team1.add(player1.m_5446_().getString());
        LockoutBingoSetup.team2.add(player2.m_5446_().getString());
        LockoutBingoSetup.mcCommandSource = source;
        ModServerEvents.server = player1.f_8924_;
        LockoutBingoSetup.seed = source.m_81372_().m_7328_();
        resetPlayer(player1);
        resetPlayer(player2);
        player1.m_213846_(Component.m_237113_("Generating Board..."));
        player2.m_213846_(Component.m_237113_("Generating Board..."));
        LockoutBingoSetup.SelectBoardGoals();
        PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(false, ModServerEvents.GameTimer, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        resetPlayer(player1);
        Networking.INSTANCE.sendTo(packet, player1.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        resetPlayer(player2);
        Networking.INSTANCE.sendTo(packet, player2.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
        LockoutBingoClientInfo.setupFakeClientBingo(false, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
        NetworkHooks.openScreen(player1, LockoutBingoClientInfo.bingoInventory);
        NetworkHooks.openScreen(player2, LockoutBingoClientInfo.bingoInventory);
        ModServerEvents.inStartPhase = true;
        player1.m_8951_().m_12850_();
        player1.m_8951_().m_12819_(player1);
        player2.m_8951_().m_12850_();
        player2.m_8951_().m_12819_(player2);
        source.m_81354_(Component.m_237115_("commands.startBingo"), true);
        return 1;
    }

    private static int StartBingoMulti(CommandSourceStack source, PlayerTeam team1, PlayerTeam team2) {
        ModServerEvents.server = source.m_230896_().m_20194_();
        clearLockout(ModServerEvents.server.m_6237_().toString());
        ArrayList<ServerPlayer> team1List = new ArrayList();
        ArrayList<ServerPlayer> team2List = new ArrayList();
        ArrayList<String> team1ListNames = new ArrayList(team1.m_6809_());
        ArrayList<String> team2ListNames = new ArrayList(team2.m_6809_());
        LockoutBingoSetup.seed = source.m_81372_().m_7328_();
        int team1Color = team1.m_7414_() != ChatFormatting.RESET ? team1.m_7414_().m_126665_() : blue_color;
        int team2Color = team2.m_7414_() != ChatFormatting.RESET ? team2.m_7414_().m_126665_() : orange_color;
        if (team1ListNames.size() != 0 && team2ListNames.size() != 0) {
            for(int i = 0; i < team1.m_6809_().size(); ++i) {
                LockoutBingoSetup.team1.add((String)team1ListNames.get(i));
                team1List.add(ModServerEvents.server.m_6846_().m_11255_((String)team1ListNames.get(i)));
                resetPlayer((ServerPlayer)team1List.get(i));
                ((ServerPlayer)team1List.get(i)).m_213846_(Component.m_237113_("Generating Board..."));
            }

            for(int i = 0; i < team2.m_6809_().size(); ++i) {
                LockoutBingoSetup.team2.add((String)team2ListNames.get(i));
                team2List.add(ModServerEvents.server.m_6846_().m_11255_((String)team2ListNames.get(i)));
                resetPlayer((ServerPlayer)team2List.get(i));
                ((ServerPlayer)team2List.get(i)).m_213846_(Component.m_237113_("Generating Board..."));
            }

            LockoutBingoSetup.mcCommandSource = source;
            LockoutBingoSetup.SelectBoardGoals();
            PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(false, ModServerEvents.GameTimer, team1Color, team2Color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

            for(int i = 0; i < team1List.size(); ++i) {
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                resetPlayer((ServerPlayer)team1List.get(i));
                Networking.INSTANCE.sendTo(packet, ((ServerPlayer)team1List.get(i)).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
            }

            for(int i = 0; i < team2List.size(); ++i) {
                ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
                ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                resetPlayer((ServerPlayer)team2List.get(i));
                Networking.INSTANCE.sendTo(packet, ((ServerPlayer)team2List.get(i)).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
            }

            LockoutBingoClientInfo.setupFakeClientBingo(false, team1Color, team2Color, LockoutBingoSetup.team1.size(), LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

            for(int i = 0; i < team1List.size(); ++i) {
                NetworkHooks.openScreen((ServerPlayer)team1List.get(i), LockoutBingoClientInfo.bingoInventory);
                ((ServerPlayer)team1List.get(i)).m_8951_().m_12850_();
                ((ServerPlayer)team1List.get(i)).m_8951_().m_12819_((ServerPlayer)team1List.get(i));
            }

            for(int i = 0; i < team2List.size(); ++i) {
                NetworkHooks.openScreen((ServerPlayer)team2List.get(i), LockoutBingoClientInfo.bingoInventory);
                ((ServerPlayer)team2List.get(i)).m_8951_().m_12850_();
                ((ServerPlayer)team2List.get(i)).m_8951_().m_12819_((ServerPlayer)team2List.get(i));
            }

            ModServerEvents.inStartPhase = true;
            switch (LockoutBingoSetup.currentLinkedTeam) {
                case -1:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam1();
                    LinkedHeathServerEvents.startLinkTeam2();
                case 0:
                default:
                    break;
                case 1:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam1();
                    break;
                case 2:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam2();
            }

            source.m_81354_(Component.m_237115_("commands.startBingo"), true);
            return 1;
        } else {
            source.m_81352_(Component.m_237115_("commands.startBingoFailed"));
            return 1;
        }
    }

    private static int StartBlackout(CommandSourceStack source, Collection<String> players, int team1Color) {
        ModServerEvents.server = source.m_230896_().m_20194_();
        clearLockout(ModServerEvents.server.m_6237_().toString());
        ArrayList<ServerPlayer> team1List = new ArrayList();
        ArrayList<String> team1ListNames = new ArrayList(players);
        LockoutBingoSetup.blackout_mode = true;
        ModServerEvents.winPoints = 25;
        LockoutBingoSetup.seed = source.m_81372_().m_7328_();
        int team2Color = orange_color;
        if (team1ListNames.size() == 0) {
            source.m_81352_(Component.m_237115_("commands.startBlackoutFailed"));
            return 1;
        } else {
            for(int i = 0; i < players.size(); ++i) {
                LockoutBingoSetup.team1.add((String)team1ListNames.get(i));
                team1List.add(ModServerEvents.server.m_6846_().m_11255_((String)team1ListNames.get(i)));
                resetPlayer((ServerPlayer)team1List.get(i));
                ((ServerPlayer)team1List.get(i)).m_213846_(Component.m_237113_("Generating Board..."));
            }

            LockoutBingoSetup.mcCommandSource = source;
            LockoutBingoSetup.SelectBoardGoals();
            PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(false, ModServerEvents.GameTimer, team1Color, team2Color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

            for(int i = 0; i < team1List.size(); ++i) {
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
                ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                resetPlayer((ServerPlayer)team1List.get(i));
                Networking.INSTANCE.sendTo(packet, ((ServerPlayer)team1List.get(i)).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
            }

            LockoutBingoClientInfo.setupFakeClientBingo(false, team1Color, team2Color, LockoutBingoSetup.team1.size(), LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

            for(int i = 0; i < team1List.size(); ++i) {
                NetworkHooks.openScreen((ServerPlayer)team1List.get(i), LockoutBingoClientInfo.bingoInventory);
                ((ServerPlayer)team1List.get(i)).m_8951_().m_12850_();
                ((ServerPlayer)team1List.get(i)).m_8951_().m_12819_((ServerPlayer)team1List.get(i));
            }

            ModServerEvents.inStartPhase = true;
            switch (LockoutBingoSetup.currentLinkedTeam) {
                case -1:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam1();
                    LinkedHeathServerEvents.startLinkTeam2();
                case 0:
                default:
                    break;
                case 1:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam1();
                    break;
                case 2:
                    LinkedHeathServerEvents.server = source.m_81377_();
                    LinkedHeathServerEvents.startLinkTeam2();
            }

            source.m_81354_(Component.m_237113_("Blackout Bingo has begun"), true);
            return 1;
        }
    }

    private static int StartBlackout(CommandSourceStack source, ServerPlayer player1) {
        return StartBlackout(source, new ArrayList(Arrays.asList(player1.m_5446_().getString())), blue_color);
    }

    private static int StartBlackout(CommandSourceStack source, PlayerTeam team1) {
        return StartBlackout(source, team1.m_6809_(), team1.m_7414_() != ChatFormatting.RESET ? team1.m_7414_().m_126665_() : blue_color);
    }

    private static int StartTestBingo(CommandSourceStack source, ServerPlayer player1, ServerPlayer player2) {
        LockoutBingoSetup.test_mode = true;
        LockoutBingoSetup.team1.add(player1.m_5446_().getString());
        LockoutBingoSetup.team2.add(player2.m_5446_().getString());
        LockoutBingoSetup.mcCommandSource = source;
        ModServerEvents.server = player1.f_8924_;
        ModServerEvents.waitTime = 4;
        Path filenameEat = Paths.get(player1.m_20194_().m_6237_().toString(), "lockoutEatData.txt");
        Path filenameBreed = Paths.get(player1.m_20194_().m_6237_().toString(), "lockoutBreedData.txt");
        Path filenameAdvancement = Paths.get(player1.m_20194_().m_6237_().toString(), "lockoutAdvancementData.txt");

        try {
            Files.deleteIfExists(filenameEat);
            Files.deleteIfExists(filenameBreed);
            Files.deleteIfExists(filenameAdvancement);
        } catch (IOException var7) {
        }

        resetPlayer(player1);
        resetPlayer(player2);
        player1.m_213846_(Component.m_237113_("Generating Board..."));
        player2.m_213846_(Component.m_237113_("Generating Board..."));
        LockoutBingoSetup.SelectTestAllBoardGoals();
        PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(true, ModServerEvents.GameTimer, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
        player1.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        resetPlayer(player1);
        Networking.INSTANCE.sendTo(packet, player1.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
        player2.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
        resetPlayer(player2);
        Networking.INSTANCE.sendTo(packet, player2.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
        LockoutBingoClientInfo.setupFakeClientBingo(true, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
        ModServerEvents.inStartPhase = true;
        player1.m_8951_().m_12850_();
        player1.m_8951_().m_12819_(player1);
        player2.m_8951_().m_12850_();
        player2.m_8951_().m_12819_(player2);
        source.m_81354_(Component.m_237115_("commands.startTestBingo"), true);
        return 1;
    }

    private static int ResumeBingo(CommandSourceStack source, ServerPlayer player1, ServerPlayer player2) {
        LockoutBingoSaveData saveData = null;

        try {
            Path filename = Paths.get(player1.m_20194_().m_6237_().toString(), "lockoutSaveData.txt");
            FileInputStream file = new FileInputStream(filename.toString());
            ObjectInputStream in = new ObjectInputStream(file);
            saveData = (LockoutBingoSaveData)in.readObject();
            in.close();
            file.close();
            LockoutBingoSetup.test_mode = saveData.isTestMode;
            LockoutBingoSetup.team1 = new ArrayList(saveData.team1);
            LockoutBingoSetup.team2 = new ArrayList(saveData.team2);
            LockoutBingoSetup.boardGoalsCompletion = new ArrayList(saveData.goals);
            LockoutBingoSetup.mcCommandSource = source;
            ModServerEvents.server = player1.f_8924_;
            LockoutBingoSetup.ResumeLockoutSetup();
            PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(false, ModServerEvents.GameTimer, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
            Networking.INSTANCE.sendTo(packet, player1.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
            player1.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
            player1.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
            player1.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
            Networking.INSTANCE.sendTo(packet, player2.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
            player2.m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
            player2.m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
            player2.m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
            LockoutBingoClientInfo.setupFakeClientBingo(LockoutBingoSetup.test_mode, blue_color, orange_color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);
            NetworkHooks.openScreen(player1, LockoutBingoClientInfo.bingoInventory);
            NetworkHooks.openScreen(player2, LockoutBingoClientInfo.bingoInventory);
            player1.m_8951_().m_12850_();
            player1.m_8951_().m_12819_(player1);
            player2.m_8951_().m_12850_();
            player2.m_8951_().m_12819_(player2);
            ModServerEvents.inStartPhase = true;
            source.m_81354_(Component.m_237115_("commands.resumeBingoSuccess"), true);
        } catch (ClassNotFoundException | IOException var8) {
            source.m_81352_(Component.m_237115_("commands.resumeBingoFailed"));
        }

        return 1;
    }

    private static int ResumeMultiBingo(CommandSourceStack source, PlayerTeam team1, PlayerTeam team2) {
        LockoutBingoSaveData saveData = null;
        ArrayList<ServerPlayer> team1List = new ArrayList();
        ArrayList<ServerPlayer> team2List = new ArrayList();
        ArrayList<String> team1ListNames = new ArrayList(team1.m_6809_());
        ArrayList<String> team2ListNames = new ArrayList(team2.m_6809_());
        ModServerEvents.server = source.m_230896_().m_20194_();
        int team1Color = team1.m_7414_() != ChatFormatting.RESET ? team1.m_7414_().m_126665_() : blue_color;
        int team2Color = team2.m_7414_() != ChatFormatting.RESET ? team2.m_7414_().m_126665_() : orange_color;
        if (team1ListNames.size() != 0 && team2ListNames.size() != 0) {
            for(int i = 0; i < team1.m_6809_().size(); ++i) {
                team1List.add(ModServerEvents.server.m_6846_().m_11255_((String)team1ListNames.get(i)));
            }

            for(int i = 0; i < team2.m_6809_().size(); ++i) {
                team2List.add(ModServerEvents.server.m_6846_().m_11255_((String)team2ListNames.get(i)));
            }

            try {
                for(int i = 0; i < team1List.size(); ++i) {
                    LockoutBingoSetup.team1.add(((ServerPlayer)team1List.get(i)).m_5446_().getString());
                    ModServerEvents.server = ((ServerPlayer)team1List.get(i)).f_8924_;
                }

                for(int i = 0; i < team2List.size(); ++i) {
                    LockoutBingoSetup.team2.add(((ServerPlayer)team2List.get(i)).m_5446_().getString());
                }

                Path filename = Paths.get(((ServerPlayer)team1List.get(0)).m_20194_().m_6237_().toString(), "lockoutSaveData.txt");
                FileInputStream file = new FileInputStream(filename.toString());
                ObjectInputStream in = new ObjectInputStream(file);
                saveData = (LockoutBingoSaveData)in.readObject();
                in.close();
                file.close();
                LockoutBingoSetup.test_mode = saveData.isTestMode;
                LockoutBingoSetup.team1 = new ArrayList(saveData.team1);
                LockoutBingoSetup.team2 = new ArrayList(saveData.team2);
                LockoutBingoSetup.boardGoalsCompletion = new ArrayList(saveData.goals);
                LockoutBingoSetup.mcCommandSource = source;
                LockoutBingoSetup.ResumeLockoutSetup();
                PacketLockoutBingoStartClient packet = new PacketLockoutBingoStartClient(false, ModServerEvents.GameTimer, team1Color, team2Color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

                for(int i = 0; i < team1List.size(); ++i) {
                    Networking.INSTANCE.sendTo(packet, ((ServerPlayer)team1List.get(i)).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                    ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                    ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
                    ((ServerPlayer)team1List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                }

                for(int i = 0; i < team2List.size(); ++i) {
                    Networking.INSTANCE.sendTo(packet, ((ServerPlayer)team2List.get(i)).f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
                    ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19597_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                    ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19603_, (ModServerEvents.waitTime + 10) * 20, 249, false, false));
                    ((ServerPlayer)team2List.get(i)).m_7292_(new MobEffectInstance(MobEffects.f_19599_, (ModServerEvents.waitTime + 10) * 20, 98, false, false));
                }

                LockoutBingoClientInfo.setupFakeClientBingo(LockoutBingoSetup.test_mode, team1Color, team2Color, 1, LockoutBingoSetup.team1, LockoutBingoSetup.team2, LockoutBingoSetup.boardGoalsCompletion.size(), LockoutBingoSetup.boardGoalsCompletion);

                for(int i = 0; i < team1List.size(); ++i) {
                    NetworkHooks.openScreen((ServerPlayer)team1List.get(i), LockoutBingoClientInfo.bingoInventory);
                    ((ServerPlayer)team1List.get(i)).m_8951_().m_12850_();
                    ((ServerPlayer)team1List.get(i)).m_8951_().m_12819_((ServerPlayer)team1List.get(i));
                }

                for(int i = 0; i < team2List.size(); ++i) {
                    NetworkHooks.openScreen((ServerPlayer)team2List.get(i), LockoutBingoClientInfo.bingoInventory);
                    ((ServerPlayer)team2List.get(i)).m_8951_().m_12850_();
                    ((ServerPlayer)team2List.get(i)).m_8951_().m_12819_((ServerPlayer)team2List.get(i));
                }

                ModServerEvents.inStartPhase = true;
                switch (LockoutBingoSetup.currentLinkedTeam) {
                    case -1:
                        LinkedHeathServerEvents.server = source.m_81377_();
                        LinkedHeathServerEvents.startLinkTeam1();
                        LinkedHeathServerEvents.startLinkTeam2();
                    case 0:
                    default:
                        break;
                    case 1:
                        LinkedHeathServerEvents.server = source.m_81377_();
                        LinkedHeathServerEvents.startLinkTeam1();
                        break;
                    case 2:
                        LinkedHeathServerEvents.server = source.m_81377_();
                        LinkedHeathServerEvents.startLinkTeam2();
                }

                source.m_81354_(Component.m_237115_("commands.resumeBingoSuccess"), true);
            } catch (ClassNotFoundException | IOException var15) {
                source.m_81352_(Component.m_237115_("commands.resumeBingoFailed"));
            }

            return 1;
        } else {
            source.m_81352_(Component.m_237115_("commands.resumeBingoFailed"));
            return 1;
        }
    }

    private static int ChangeBingoLocation(CommandSourceStack source, ServerPlayer playerEntity, Integer pos) {
        Networking.INSTANCE.sendTo(new PacketBingoLocation(pos), playerEntity.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
        return 1;
    }

    private static int GiveBingoGoal(CommandSourceStack source, ServerPlayer playerEntity, Integer goal) {
        ModServerEvents.triggerClientBoardUpdate(goal - 1, playerEntity.m_5446_().getString());
        LockoutBingoSetup.resetGoalSearches();
        return 1;
    }

    private static int ClearBingoGoal(CommandSourceStack source, Integer goal) {
        ModServerEvents.ClearGoal(goal - 1);
        return 1;
    }
}
