package com.atsign.lockoutbingomod.LockoutBingoInterface;

import com.atsign.lockoutbingomod.core.LockoutBingoClientInfo;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class BingoIngameGui extends GuiComponent {
    private ResourceLocation GUI;
    private ResourceLocation GUI_TOP_RIGHT = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_top_right.png");
    private ResourceLocation GUI_TOP_LEFT = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_top_left.png");
    private ResourceLocation GUI_BOTTOM_LEFT = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_bottom_left.png");
    private ResourceLocation GUI_BOTTOM_RIGHT = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_bottom_right.png");
    private ResourceLocation GUI_BOTTOM = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_bottom.png");
    private ResourceLocation GUI_TOP = new ResourceLocation("lockoutbingomod", "textures/guis/hotbar_top.png");
    private ResourceLocation COLOR = new ResourceLocation("lockoutbingomod", "textures/guis/color_fix_2.png");
    public final Minecraft mc;
    protected final ItemRenderer itemRenderer;
    protected int scaledWidth;
    protected int scaledHeight;
    int relX;
    int relY;
    int fixY;
    int scoreX;
    int scoreY;

    public BingoIngameGui(Minecraft mcIn) {
        this.mc = mcIn;
        this.itemRenderer = mcIn.m_91291_();
    }

    public void renderIngameGui(PoseStack p_93011_, float partialTicks) {
        this.scaledWidth = this.mc.m_91268_().m_85445_();
        this.scaledHeight = this.mc.m_91268_().m_85446_();
        switch (LockoutBingoClientInfo.location_id) {
            case 1:
                this.GUI = this.GUI_TOP;
                this.relX = 0;
                this.relY = this.fixY = 0;
                if (LockoutBingoClientInfo.timerLeft != -1) {
                    this.scoreX = 75;
                } else {
                    this.scoreX = 47;
                }

                this.scoreY = 94;
                break;
            case 2:
                this.GUI = this.GUI_TOP;
                this.relX = this.scaledWidth - 94;
                this.relY = this.fixY = 0;
                if (LockoutBingoClientInfo.timerLeft != -1) {
                    this.scoreX = this.scaledWidth - 19;
                } else {
                    this.scoreX = this.scaledWidth - 47;
                }

                this.scoreY = 94;
                break;
            case 3:
            default:
                this.GUI = this.GUI_BOTTOM;
                this.relX = 0;
                this.relY = this.scaledHeight - 105;
                this.fixY = this.relY + 11;
                if (LockoutBingoClientInfo.timerLeft != -1) {
                    this.scoreX = 74;
                } else {
                    this.scoreX = 47;
                }

                this.scoreY = this.relY + 4;
                break;
            case 4:
                this.GUI = this.GUI_BOTTOM;
                this.relX = this.scaledWidth - 94;
                this.relY = this.scaledHeight - 105;
                this.fixY = this.relY + 11;
                if (LockoutBingoClientInfo.timerLeft != -1) {
                    this.scoreX = this.scaledWidth - 20;
                } else {
                    this.scoreX = this.scaledWidth - 47;
                }

                this.scoreY = this.relY + 4;
        }

        if (!LockoutBingoClientInfo.isTestMode) {
            this.renderHotbar(partialTicks, p_93011_);
        }

    }

    private Player getRenderViewPlayer() {
        return !(this.mc.m_91288_() instanceof Player) ? null : (Player)this.mc.m_91288_();
    }

    protected void renderHotbar(float partialTicks, PoseStack p_93011_) {
        Player playerentity = this.getRenderViewPlayer();
        RenderSystem.m_157456_(0, this.GUI);
        m_93133_(p_93011_, this.relX, this.relY, 0.0F, 0.0F, 94, 105, 94, 105);
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        int index = 0;
        int i1 = 1;

        for(int y = 0; y < 5; ++y) {
            for(int x = 0; x < 5; ++x) {
                this.renderSlot(this.relX + 3 + x * 18, this.fixY + 3 + y * 18, partialTicks, playerentity, LockoutBingoClientInfo.bingoInventory.m_8020_(index), i1++);
                ++index;
            }
        }

        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        RenderSystem.m_69482_();
        RenderSystem.m_157456_(0, this.COLOR);
        index = 0;

        for(int y = 0; y < 5; ++y) {
            for(int x = 0; x < 5; ++x) {
                if ((Integer)LockoutBingoClientInfo.tileColoring.get(index) == 1) {
                    m_93133_(p_93011_, this.relX + 3 + x * 18, this.fixY + 3 + y * 18, (float)(Integer)LockoutBingoClientInfo.color_offset.get(LockoutBingoClientInfo.team1ColorValue), 0.0F, 16, 16, 306, 16);
                } else if ((Integer)LockoutBingoClientInfo.tileColoring.get(index) == 2) {
                    m_93133_(p_93011_, this.relX + 3 + x * 18, this.fixY + 3 + y * 18, (float)(Integer)LockoutBingoClientInfo.color_offset.get(LockoutBingoClientInfo.team2ColorValue), 0.0F, 16, 16, 306, 16);
                }

                ++index;
            }
        }

        RenderSystem.m_69461_();
        RenderSystem.m_69465_();
        RenderSystem.m_69478_();
        RenderSystem.m_69453_();
        if (LockoutBingoClientInfo.timerLeft != -1) {
            int sec = LockoutBingoClientInfo.timerLeft % 60;
            int min = LockoutBingoClientInfo.timerLeft / 60 % 60;
            int hours = LockoutBingoClientInfo.timerLeft / 60 / 60;
            String strSec = sec < 10 ? "0" + Integer.toString(sec) : Integer.toString(sec);
            String strmin = min < 10 ? "0" + Integer.toString(min) : Integer.toString(min);
            String strHours = hours < 10 ? "0" + Integer.toString(hours) : Integer.toString(hours);
            String timer = strHours + ":" + strmin + ":" + strSec;
            Minecraft.m_91087_().f_91062_.m_92750_(p_93011_, timer, (float)(this.scoreX - 46 - Minecraft.m_91087_().f_91062_.m_92895_(timer) / 2), (float)this.scoreY, 16777215);
        }

        Minecraft.m_91087_().f_91062_.m_92750_(p_93011_, "-", (float)(this.scoreX - Minecraft.m_91087_().f_91062_.m_92895_("-") / 2), (float)this.scoreY, 16777215);
        int greenColor = 42474;
        String team = Integer.toString(LockoutBingoClientInfo.teamScore);
        Minecraft.m_91087_().f_91062_.m_92750_(p_93011_, team, (float)(this.scoreX - 9 - Minecraft.m_91087_().f_91062_.m_92895_(team) / 2), (float)this.scoreY, LockoutBingoClientInfo.clientPlayersTeamNum == 1 ? LockoutBingoClientInfo.team1ColorValue : LockoutBingoClientInfo.team2ColorValue);
        int redColor = 16742656;
        String opp = Integer.toString(LockoutBingoClientInfo.oppScore);
        Minecraft.m_91087_().f_91062_.m_92750_(p_93011_, opp, (float)(this.scoreX + 9 - Minecraft.m_91087_().f_91062_.m_92895_(opp) / 2), (float)this.scoreY, LockoutBingoClientInfo.clientPlayersTeamNum == 1 ? LockoutBingoClientInfo.team2ColorValue : LockoutBingoClientInfo.team1ColorValue);
        RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
        String longestName = "";

        for(String name : LockoutBingoClientInfo.team1) {
            if (name.length() > longestName.length()) {
                longestName = name;
            }
        }

        for(String name : LockoutBingoClientInfo.team2) {
            if (name.length() > longestName.length()) {
                longestName = name;
            }
        }

        int w = Minecraft.m_91087_().f_91062_.m_92895_(longestName);
        int left = this.scaledWidth - 6 - w;
        double d1 = (Double)Minecraft.m_91087_().f_91066_.m_232104_().m_231551_();
        double d5 = (double)1.0F;
        int i2 = (int)((double)255.0F * d5 * d1);
        Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
        int top = 120 + (int)((double)9.0F * (double)3.5F);

        for(String name : LockoutBingoClientInfo.team1) {
            int var39 = top - 1;
            int var10003 = left + w + 3;
            Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
            m_93172_(p_93011_, left, var39, var10003, top + 9 - 1, i2 << 24);
            Minecraft.m_91087_().f_91062_.m_92883_(p_93011_, name, (float)(left + 2), (float)top, LockoutBingoClientInfo.team1ColorValue);
            Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
            top += 9;
        }

        for(String name : LockoutBingoClientInfo.team2) {
            int var40 = top - 1;
            int var41 = left + w + 3;
            Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
            m_93172_(p_93011_, left, var40, var41, top + 9 - 1, i2 << 24);
            Minecraft.m_91087_().f_91062_.m_92883_(p_93011_, name, (float)(left + 2), (float)top, LockoutBingoClientInfo.team2ColorValue);
            Objects.requireNonNull(Minecraft.m_91087_().f_91062_);
            top += 9;
        }

        RenderSystem.m_157456_(0, GuiComponent.f_93098_);
    }

    private void renderSlot(int p_168678_, int p_168679_, float p_168680_, Player p_168681_, ItemStack p_168682_, int p_168683_) {
        if (!p_168682_.m_41619_()) {
            PoseStack posestack = RenderSystem.m_157191_();
            float f = (float)p_168682_.m_41612_() - p_168680_;
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                posestack.m_85836_();
                posestack.m_85837_((double)(p_168678_ + 8), (double)(p_168679_ + 12), (double)0.0F);
                posestack.m_85841_(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                posestack.m_85837_((double)(-(p_168678_ + 8)), (double)(-(p_168679_ + 12)), (double)0.0F);
                RenderSystem.m_157182_();
            }

            this.itemRenderer.m_174229_(p_168681_, p_168682_, p_168678_, p_168679_, p_168683_);
            RenderSystem.m_157427_(GameRenderer::m_172811_);
            if (f > 0.0F) {
                posestack.m_85849_();
                RenderSystem.m_157182_();
            }

            int count = p_168682_.m_41613_();
            if (count > 0) {
                String item_description = p_168682_.m_41786_().getString();
                if (item_description.contains("Sprint 1km")) {
                    count = 1000;
                } else if (item_description.contains("Take 200 Damage")) {
                    count = 200;
                } else if (item_description.contains("Deal 400 Damage")) {
                    count = 400;
                }
            }

            if (count != 1) {
                PoseStack posestack_count = new PoseStack();
                String s = String.valueOf(count);
                if (count >= 1000) {
                    String var10000 = String.valueOf((double)count / (double)1000.0F);
                    s = var10000.replaceFirst(".0", "") + "km";
                }

                posestack_count.m_85837_((double)0.0F, (double)0.0F, (double)(this.itemRenderer.f_115093_ + 200.0F));
                MultiBufferSource.BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
                this.mc.f_91062_.m_92811_(s, (float)(p_168678_ + 19 - 2 - this.mc.f_91062_.m_92895_(s)), (float)(p_168679_ + 6 + 3), 16777215, true, posestack_count.m_85850_().m_85861_(), multibuffersource$buffersource, false, 0, 15728880);
                multibuffersource$buffersource.m_109911_();
            }
        }

    }
}
