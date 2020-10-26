package com.xX_deadbush_Xx.hocus.api.crafting.recipes;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public interface IDryingRackRecipe extends IOneInOneOutRecipe {
	ResourceLocation TYPE_ID = new ResourceLocation(Hocus.MOD_ID, "drying_rack");

	@Nonnull
	@Override
	default IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(TYPE_ID).get();
	}
}
