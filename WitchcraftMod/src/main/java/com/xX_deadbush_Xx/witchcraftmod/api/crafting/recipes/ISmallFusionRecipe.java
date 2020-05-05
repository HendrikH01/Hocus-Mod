package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.common.util.WitchcraftLib;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface ISmallFusionRecipe extends IRecipe<RecipeWrapper> {
	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(WitchcraftLib.SMALL_FUSION_ID).get();
	}

	@Override
	default boolean canFit(int width, int height) {
		return false;
	}
}
