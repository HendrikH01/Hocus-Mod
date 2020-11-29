package com.xX_deadbush_Xx.hocus.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.container.AutoToolTableContainer;
import com.xX_deadbush_Xx.hocus.common.network.HocusCPrintRecipePacket;
import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AutoToolTableScreen extends ContainerScreen<AutoToolTableContainer>{
	private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(Hocus.MOD_ID, "textures/gui/container/auto_tooltable_container.png");
	private Button print;
	private Minecraft mc = Minecraft.getInstance();

	public AutoToolTableScreen(AutoToolTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		this.xSize = 176;
		this.ySize = 196;
	}
	
	@Override
	protected void init() {
		super.init();
		print = new Button(mc.getMainWindow().getScaledWidth() / 2 - xSize / 2 + 30, mc.getMainWindow().getScaledHeight() / 2 - 23, 40, 20, "Print", (x)->{
			HocusPacketHandler.INSTANCE.sendToServer(new HocusCPrintRecipePacket(this.container.tile.getPos(), true));
		});
		this.addButton(this.print);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
		this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
		this.blit(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
	}
	
	@Override
	public void render(final int mouseX, final int mouseY, final float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}
