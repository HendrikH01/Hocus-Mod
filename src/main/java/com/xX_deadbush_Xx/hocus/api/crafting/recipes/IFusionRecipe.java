package com.xX_deadbush_Xx.hocus.api.crafting.recipes;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IFusionRecipe extends IRecipe<RecipeWrapper> {

	@Override
	default boolean canFit(int width, int height) {
		return false;
	}
	
	int getActivationCost();
}
