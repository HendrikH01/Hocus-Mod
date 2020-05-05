package com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.DryingRackRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class DryingRackSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DryingRackRecipe> {

	@Override
	public DryingRackRecipe read(ResourceLocation recipeId, JsonObject json) {
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));

		return new DryingRackRecipe(recipeId, output, input);
	}

	@Override
	public DryingRackRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		ItemStack output = buffer.readItemStack();
		Ingredient input = Ingredient.read(buffer);
		
		return new DryingRackRecipe(recipeId, output, input);
	}

	@Override
	public void write(PacketBuffer buffer, DryingRackRecipe recipe) {
		Ingredient input = recipe.getIngredients().get(0);
		input.write(buffer);
		
		buffer.writeItemStack(recipe.getRecipeOutput(), false);
	}
}
