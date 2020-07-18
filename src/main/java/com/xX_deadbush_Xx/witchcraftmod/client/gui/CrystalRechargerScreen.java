package com.xX_deadbush_Xx.witchcraftmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.container.CrystalRechargerContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class CrystalRechargerScreen extends ContainerScreen<CrystalRechargerContainer> {

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(WitchcraftMod.MOD_ID, "textures/gui/container/crystal_recharger.png");

    public CrystalRechargerScreen(CrystalRechargerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        if (this.container.getTile().isBurning()) {
            int k = this.container.getBurnLeftScaled();
            this.blit(i + 60, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }

    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
