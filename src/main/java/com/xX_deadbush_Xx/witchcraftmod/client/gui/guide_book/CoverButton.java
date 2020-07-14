package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;

public class CoverButton extends Widget {
	
	
	public CoverButton(int xIn, int yIn, int widthIn, int heightIn, String msg) {
		super(xIn, yIn, widthIn, heightIn, msg);
	}
	
	@Override
	   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
	      Minecraft minecraft = Minecraft.getInstance();
	      minecraft.getTextureManager().bindTexture(GuideBookScreen.TEXTURES_SHEET);
	      AbstractGui.blit(this.x, this.y, 0, 310, 0, this.width, this.height, GuideBookScreen.SHEET_HEIGHT, GuideBookScreen.SHEET_WIDTH);
	      }
	
	public void onPress() {
	}
}
