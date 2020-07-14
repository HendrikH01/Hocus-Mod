package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.Widget;

public class PreviousButton extends Widget{

	public PreviousButton(int xIn, int yIn, int widthIn, int heightIn, String msg) {
		super(xIn, yIn, widthIn, heightIn, msg);
	}

	@Override
	   public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
	      Minecraft minecraft = Minecraft.getInstance();
	      minecraft.getTextureManager().bindTexture(GuideBookScreen.TEXTURES_SHEET);
	      int i = this.getImage(this.isHovered());
	      AbstractGui.blit(this.x, this.y, 0, i * 23 + 5, 186 + 10 + 3, this.width, this.height, GuideBookScreen.SHEET_HEIGHT, GuideBookScreen.SHEET_WIDTH);
	      }
	
	public void onPress() {
	}
	
	protected int getImage(boolean check) {
		if(check) return 1;
		return 0;
	}
	
}
