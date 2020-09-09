package com.xX_deadbush_Xx.witchcraftmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.container.CrystalRechargerContainer;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.CrystalRechargerTile;
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

        int x = this.guiLeft;
        int y = this.guiTop;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
        
        int burntime = this.container.getBurnTime();
        int burntimemax = this.container.getMaxBurnTime();
        
        if (this.container.getBurnTime() > 0) {
            int firelevel = (int)(13 * burntime / burntimemax);
            this.blit(x + 60, y + 60 - firelevel, 176, 12 - firelevel, 14, firelevel + 1);
        }

    }

    @Override
    public void render(final int mouseX, final int mouseY, final float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
}
