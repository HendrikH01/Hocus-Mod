package com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate;

import java.awt.Color;

import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;

public enum GlowType implements IStringSerializable {
	   WHITE("white"),
	   PURPLE("purple"),
	   GREEN("green"),
	   RED("red");

	   private final String name;

	   private GlowType(String name) {
	      this.name = name;
	   }

	   public String toString() {
	      return this.getName();
	   }

	   public String getName() {
	      return this.name;
	   }
	   
	   public int getColor(int strength) {
		   return this.getColor(strength, new Color(255,255,255).getRGB());
	   }
	   
	   public int getColor(int strength, int colorToBlend) {
		   double blendingratio = (double)MathHelper.clamp(strength, 0, 15)/15;
		   double invblendingratio = 1.0D - blendingratio;		   
		   int r=255, g=255, b=255;
		   switch(this) {
			   case GREEN: {
				   r=125; g=200; b=70;
				   break;
			   }
			   case PURPLE: {
				   r=225; g=50; b=195;
				   break;
			   }
			   case RED: {
				   r=190; g=50; b=50;
				   break;
			   }
			   default: {
				   break;
			   }
		   }
		   
		   int r1 = ((colorToBlend & 0xff0000) >> 16);
		   int g1 = ((colorToBlend & 0xff00) >> 8);
		   int b1 = (colorToBlend & 0xff);
		   r = (int)(blendingratio*r + invblendingratio*r1);
		   g = (int)(blendingratio*g + invblendingratio*g1);
		   b = (int)(blendingratio*b + invblendingratio*b1);
		   return -16777216 | r << 16 | g << 8 | b;
	   }
}
