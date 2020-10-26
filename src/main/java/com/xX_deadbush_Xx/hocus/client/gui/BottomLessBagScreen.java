package com.xX_deadbush_Xx.hocus.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.container.BottomLessBagContainer;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BottomLessBagScreen extends ContainerScreen<BottomLessBagContainer> {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Hocus.MOD_ID, "textures/gui/container/bottomless_bag.png");

    public BottomLessBagScreen(BottomLessBagContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = 176;
        this.ySize = 166;
    }



    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String count = "Count: " + this.container.getAmount();
        this.font.drawString(count, this.xSize / 2 - this.font.getStringWidth(count) / 2, 60, 4210752);
        //this.itemRenderer.renderItemAndEffectIntoGUI(this.container.getStoredItem(), 81, 35);
        this.itemRenderer.renderItemOverlayIntoGUI(this.font, this.container.getAmount() == 0 ? ItemStack.EMPTY : this.container.getStoredItem(), 90, 35, "");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }


    @Override
    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        this.renderBackground();
        super.render(p_render_1_, p_render_2_, p_render_3_);
        this.renderHoveredToolTip(p_render_1_, p_render_2_);
    }
}
