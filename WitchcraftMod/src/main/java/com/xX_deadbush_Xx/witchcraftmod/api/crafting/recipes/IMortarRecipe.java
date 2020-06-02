package com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IMortarRecipe extends IOneInOneOutRecipe {
	ResourceLocation TYPE_ID = new ResourceLocation(WitchcraftMod.MOD_ID, "mortar");

	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(TYPE_ID).get();
	}
}
