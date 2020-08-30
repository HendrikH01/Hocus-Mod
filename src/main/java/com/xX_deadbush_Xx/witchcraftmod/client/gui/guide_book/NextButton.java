package com.xX_deadbush_Xx.witchcraftmod.client.gui.guide_book;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.widget.button.Button;

public class NextButton extends Button {

	public NextButton(int widthIn, int heightIn, int width, int height, String text, IPressable onPress) {
		super(widthIn, heightIn, width, height, text, onPress);
	}

	@Override
	public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
		Minecraft minecraft = Minecraft.getInstance();
		minecraft.getTextureManager().bindTexture(GuideBookScreen.TEXTURES_SHEET);
		int i = this.getImage(this.isHovered());
		AbstractGui.blit(this.x, this.y, 0, i * 23 + 5, 186, this.width, this.height, GuideBookScreen.SHEET_HEIGHT, GuideBookScreen.SHEET_WIDTH);
	}
	
	protected int getImage(boolean check) {
		if(check) return 1;
		return 0;
	}
	
}
