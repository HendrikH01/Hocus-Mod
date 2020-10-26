package com.xX_deadbush_Xx.hocus.api.ritual;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

public interface ICraftingRitual {
	IRecipe<?> getRecipe();
	
	ItemStack[] getRecipeInputs();
}
