package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class CraftingHelper {	
	
	public static List<IRecipe<?>> findRecipesByType(IRecipeType<?> type, World world) {
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toList()) : Collections.emptyList();
	}
	
	public static List<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
		@SuppressWarnings("resource")
		ClientWorld world = Minecraft.getInstance().world;
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toList()) : Collections.emptyList();
	}
	
	public static List<ItemStack> getAllRecipeInputs(IRecipeType<?> type, World world) {
		List<ItemStack> out = new ArrayList<>();
		List<IRecipe<?>> recipes = findRecipesByType(type, world);
		System.out.println(recipes + " " + world);
		for(IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> inputs = recipe.getIngredients();
			inputs.forEach(i -> {System.out.println(i); for(ItemStack stack : i.getMatchingStacks()) out.add(stack);}); 
		}
		return out;
	}
}
