package com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.ShapelessToolTableRecipe;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class ToolTableShapelessSerializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<ShapelessToolTableRecipe> {
	
	@Override
	public ShapelessToolTableRecipe read(ResourceLocation recipeId, JsonObject json) {
		ShapelessRecipe shapeless = IRecipeSerializer.CRAFTING_SHAPELESS.read(recipeId, json);
		Map<Ingredient, Integer> tools = new HashMap<>();
		JSONUtils.getJsonArray(json, "tools").forEach(elem -> {
			tools.put(Ingredient.deserialize(elem), JSONUtils.getInt(elem.getAsJsonObject(), "damage"));
		});
		
		return new ShapelessToolTableRecipe(shapeless, tools);
	}

	@Override
	public ShapelessToolTableRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		ShapelessRecipe shapeless = IRecipeSerializer.CRAFTING_SHAPELESS.read(recipeId, buffer	);
		Map<Ingredient, Integer> tools = new HashMap<>();
		for(int i = 0; i < buffer.readInt(); i++) tools.put(Ingredient.read(buffer), buffer.readInt()); 
		return new ShapelessToolTableRecipe(shapeless, tools);
	}

	@Override
	public void write(PacketBuffer buffer, ShapelessToolTableRecipe recipe) {
		IRecipeSerializer.CRAFTING_SHAPELESS.write(buffer, recipe.convertShapeless());
		buffer.writeInt(recipe.getTools().size());
		recipe.getTools().forEach((tool, damage) -> {
			tool.write(buffer);	
			buffer.writeInt(damage);
		});	
	}
}