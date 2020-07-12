package com.xX_deadbush_Xx.witchcraftmod.api.crafting.serializers;

import com.google.gson.JsonObject;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IOneInOneOutRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class OneInOneOutSerializer<T extends IOneInOneOutRecipe> extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {
	
	private IFactory<? extends IRecipe<?>> factory;

	public OneInOneOutSerializer(IFactory<T> factory) {
		this.factory = factory;
	}
	
	@Override
	public T read(ResourceLocation recipeId, JsonObject json) {
		Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
		ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
		return (T) factory.create(recipeId, input, output);
	}

	@Override
	public T read(ResourceLocation recipeId, PacketBuffer buffer) {	
		Ingredient input = Ingredient.read(buffer);
		ItemStack output = buffer.readItemStack();
		return (T) this.factory.create(recipeId, input, output);
	}

	@Override
	public void write(PacketBuffer buffer, T recipe) {
		recipe.getInput().write(buffer);
		buffer.writeItemStack(recipe.getRecipeOutput(), false);
	}
	
	public interface IFactory<T extends IOneInOneOutRecipe> {
		T create(ResourceLocation recipeId, Ingredient input, ItemStack output);
	}
}
