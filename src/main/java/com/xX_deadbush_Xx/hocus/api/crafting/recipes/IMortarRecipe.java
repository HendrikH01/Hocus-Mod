package com.xX_deadbush_Xx.hocus.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public interface IMortarRecipe extends IRecipe<RecipeWrapper> {
	ResourceLocation TYPE_ID = new ResourceLocation(Hocus.MOD_ID, "mortar");

	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(TYPE_ID).get();
	}
	
	@Override
	default boolean canFit(int width, int height) {
		return false;
	}
}
