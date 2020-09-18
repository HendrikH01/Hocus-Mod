package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IFusionRecipe extends IRecipe<RecipeWrapper> {

	@Override
	default boolean canFit(int width, int height) {
		return false;
	}
	
	int getActivationCost();
}
