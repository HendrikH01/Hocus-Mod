package com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.ShapedToolTableRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ToolTableShapedSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapedToolTableRecipe> {
	
	@Override
	public ShapedToolTableRecipe read(ResourceLocation recipeId, JsonObject json) {
		ShapedRecipe shaped = IRecipeSerializer.CRAFTING_SHAPED.read(recipeId, json);
		Map<Ingredient, Integer> tools = new HashMap<>();
		JSONUtils.getJsonArray(json, "tools").forEach(elem -> {
			tools.put(Ingredient.deserialize(elem), JSONUtils.getInt(elem, "damage"));
		});
		
		return new ShapedToolTableRecipe(shaped, tools);
	}

	@Override
	public ShapedToolTableRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		ShapedRecipe shaped = IRecipeSerializer.CRAFTING_SHAPED.read(recipeId, buffer);
		Map<Ingredient, Integer> tools = new HashMap<>();
		for(int i = 0; i < buffer.readInt(); i++) tools.put(Ingredient.read(buffer), buffer.readInt()); 
		return new ShapedToolTableRecipe(shaped, tools);
	}

	@Override
	public void write(PacketBuffer buffer, ShapedToolTableRecipe recipe) {
		IRecipeSerializer.CRAFTING_SHAPED.write(buffer, recipe.convertShaped());
		buffer.writeInt(recipe.getTools().size());
		recipe.getTools().forEach((tool, damage) -> {
			tool.write(buffer);
			buffer.writeInt(damage);});	
	}
}
