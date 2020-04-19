package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IDryingRackRecipe extends IRecipe<RecipeWrapper> {
	ResourceLocation TYPE_ID = new ResourceLocation(WitchcraftMod.MOD_ID, "drying_rack");

	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(TYPE_ID).get();
	}

	@Override
	default boolean canFit(int width, int height) {
		return false;
	}

	Ingredient getInput();
}
