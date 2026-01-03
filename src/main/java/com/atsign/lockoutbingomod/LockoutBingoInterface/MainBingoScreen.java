package com.atsign.lockoutbingomod.LockoutBingoInterface;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import com.atsign.lockoutbingomod.core.init.Keybinds;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MainBingoScreen extends AbstractContainerScreen<ContainerMainBingoBoard> {
    private ResourceLocation GUI = new ResourceLocation("lockoutbingomod", "textures/guis/inventory_thing.png");
    private ResourceLocation COLOR = new ResourceLocation("lockoutbingomod", "textures/guis/color_fix_2.png");

    public MainBingoScreen(ContainerMainBingoBoard container, Inventory inv, Component name) {
        super(container, inv, name);
        this.f_97726_ = 130;
        this.f_97727_ = 94;
        this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
        this.f_97736_ = (this.f_96544_ - this.f_97727_) / 2;
    }

    protected void m_7856_() {
        super.m_7856_();
        this.f_97735_ = (this.f_96543_ - this.f_97726_) / 2;
        this.f_97736_ = (this.f_96544_ - this.f_97727_) / 2;
    }

    protected void m_7286_(PoseStack p_97787_, float partialTicks, int x, int y) {
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.m_157456_(0, this.GUI);
        int relX = (this.f_96543_ - this.f_97726_) / 2;
        int relY = (this.f_96544_ - this.f_97727_) / 2;
        m_93133_(p_97787_, relX, relY, 0.0F, 0.0F, 104, 114, 139, 114);
    }

    @ParametersAreNonnullByDefault
    protected void m_7027_(PoseStack p_97808_, int x, int y) {
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69482_();
        RenderSystem.m_157427_(GameRenderer::m_172817_);
        RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.m_157456_(0, this.COLOR);
        int index = 0;
        int relX = (this.f_96543_ - this.f_97726_) / 2;
        int relY = (this.f_96544_ - this.f_97727_) / 2;

        for(int y1 = 0; y1 < 5; ++y1) {
            for(int x1 = 0; x1 < 5; ++x1) {
                if ((Integer)LockoutBingoClientInfo.tileColoring.get(index) == 1) {
                    m_93133_(p_97808_, 8 + x1 * 18, 18 + y1 * 18, (float)(Integer)LockoutBingoClientInfo.color_offset.get(LockoutBingoClientInfo.team1ColorValue), 0.0F, 16, 16, 306, 16);
                } else if ((Integer)LockoutBingoClientInfo.tileColoring.get(index) == 2) {
                    m_93133_(p_97808_, 8 + x1 * 18, 18 + y1 * 18, (float)(Integer)LockoutBingoClientInfo.color_offset.get(LockoutBingoClientInfo.team2ColorValue), 0.0F, 16, 16, 306, 16);
                }

                ++index;
            }
        }

        RenderSystem.m_69461_();
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        int offset = 0;
        if (LockoutBingoClientInfo.timerLeft != -1) {
            offset = 19;
            int sec = LockoutBingoClientInfo.timerLeft % 60;
            int min = LockoutBingoClientInfo.timerLeft / 60 % 60;
            int hours = LockoutBingoClientInfo.timerLeft / 60 / 60;
            String strSec = sec < 10 ? "0" + Integer.toString(sec) : Integer.toString(sec);
            String strmin = min < 10 ? "0" + Integer.toString(min) : Integer.toString(min);
            String strHours = hours < 10 ? "0" + Integer.toString(hours) : Integer.toString(hours);
            String timer = strHours + ":" + strmin + ":" + strSec;
            Minecraft.m_91087_().f_91062_.m_92883_(p_97808_, timer, (float)(52 - offset - Minecraft.m_91087_().f_91062_.m_92895_(timer) / 2), 6.0F, 2697513);
        }

        Minecraft.m_91087_().f_91062_.m_92883_(p_97808_, "-", (float)(52 + offset - Minecraft.m_91087_().f_91062_.m_92895_("-") / 2), 6.0F, 2697513);
        int greenColor = 42474;
        String team = Integer.toString(LockoutBingoClientInfo.teamScore);
        Minecraft.m_91087_().f_91062_.m_92883_(p_97808_, team, (float)(43 + offset - Minecraft.m_91087_().f_91062_.m_92895_(team) / 2), 6.0F, LockoutBingoClientInfo.clientPlayersTeamNum == 1 ? LockoutBingoClientInfo.team1ColorValue : LockoutBingoClientInfo.team2ColorValue);
        int redColor = 16742656;
        String opp = Integer.toString(LockoutBingoClientInfo.oppScore);
        Minecraft.m_91087_().f_91062_.m_92883_(p_97808_, opp, (float)(61 + offset - Minecraft.m_91087_().f_91062_.m_92895_(opp) / 2), 6.0F, LockoutBingoClientInfo.clientPlayersTeamNum == 1 ? LockoutBingoClientInfo.team2ColorValue : LockoutBingoClientInfo.team1ColorValue);
    }

    @ParametersAreNonnullByDefault
    public void m_6305_(PoseStack p_97795_, int mouseX, int mouseY, float partialTicks) {
        this.m_7333_(p_97795_);
        super.m_6305_(p_97795_, mouseX, mouseY, partialTicks);
        this.m_7025_(p_97795_, mouseX, mouseY);
    }

    public void m_97799_(PoseStack p_97800_, Slot p_97801_) {
        int i = p_97801_.f_40220_;
        int j = p_97801_.f_40221_;
        ItemStack itemstack = p_97801_.m_7993_();
        boolean flag = false;
        boolean flag1 = false;
        this.m_93250_(100);
        this.f_96542_.f_115093_ = 100.0F;
        if (itemstack.m_41619_() && p_97801_.m_6659_()) {
            Pair<ResourceLocation, ResourceLocation> pair = p_97801_.m_7543_();
            if (pair != null) {
                TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.f_96541_.m_91258_((ResourceLocation)pair.getFirst()).apply((ResourceLocation)pair.getSecond());
                RenderSystem.m_157456_(0, textureatlassprite.m_118414_().m_118330_());
                m_93200_(p_97800_, i, j, this.m_93252_(), 16, 16, textureatlassprite);
                flag1 = true;
            }
        }

        if (!flag1) {
            if (flag) {
                m_93172_(p_97800_, i, j, i + 16, j + 16, -2130706433);
            }

            RenderSystem.m_69482_();
            this.f_96542_.m_174229_(this.f_96541_.f_91074_, itemstack, i, j, p_97801_.f_40220_ + p_97801_.f_40221_ * this.f_97726_);
            int count = itemstack.m_41613_();
            if (count > 0) {
                String item_description = itemstack.m_41786_().getString();
                if (item_description.contains("Sprint 1km")) {
                    count = 1000;
                } else if (item_description.contains("Take 200 Damage")) {
                    count = 200;
                } else if (item_description.contains("Deal 400 Damage")) {
                    count = 400;
                }
            }

            if (count != 1) {
                String s = String.valueOf(count);
                if (count >= 1000) {
                    String var10000 = String.valueOf((double)count / (double)1000.0F);
                    s = var10000.replaceFirst(".0", "") + "km";
                }

                PoseStack posestack_count = new PoseStack();
                posestack_count.m_85837_((double)0.0F, (double)0.0F, (double)(this.f_96542_.f_115093_ + 200.0F));
                MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
                Minecraft.m_91087_().f_91062_.m_92811_(s, (float)(i + 19 - 2 - Minecraft.m_91087_().f_91062_.m_92895_(s)), (float)(j + 6 + 3), 16777215, true, posestack_count.m_85850_().m_85861_(), multibuffersource$buffersource, false, 0, 15728880);
                multibuffersource$buffersource.m_109911_();
            }

            RenderSystem.m_69465_();
        }

        this.f_96542_.f_115093_ = 0.0F;
        this.m_93250_(0);
    }

    @Nonnull
    public List<Component> m_96555_(ItemStack itemStack) {
        List<Component> list1 = Lists.newArrayList();
        list1.add(itemStack.m_41786_());
        return list1;
    }

    public boolean m_6913_() {
        return true;
    }

    public boolean m_7043_() {
        return false;
    }

    public boolean m_7933_(int keyCode, int scanCode, int modifiers) {
        InputConstants.Key mouseKey = InputConstants.m_84827_(keyCode, scanCode);
        if (super.m_7933_(keyCode, scanCode, modifiers)) {
            return true;
        } else if (Keybinds.bingo.isActiveAndMatches(mouseKey)) {
            this.m_7379_();
            return true;
        } else {
            return true;
        }
    }
}
