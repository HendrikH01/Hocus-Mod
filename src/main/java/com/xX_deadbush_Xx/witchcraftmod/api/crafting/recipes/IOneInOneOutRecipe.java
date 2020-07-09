package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IOneInOneOutRecipe extends IRecipe<RecipeWrapper> {

	@Override
	default boolean canFit(int width, int height) {
		return false;
	}

	Ingredient getInput();
}
