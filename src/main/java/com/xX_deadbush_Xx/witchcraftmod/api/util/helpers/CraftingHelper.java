package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
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
	
	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type, World world) {
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
		@SuppressWarnings("resource")
		ClientWorld world = Minecraft.getInstance().world;
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static IRecipe<?> findCraftingRecipeByResult(ItemStack result, World world){	
			Set<IRecipe<?>> recipes = findRecipesByType(IRecipeType.CRAFTING, world);
			for(IRecipe<?> recipe : recipes) {
				if(recipe.getRecipeOutput().getItem().equals(result.getItem())) {
					return recipe;
				}
			}
			return null;
	}
	
	public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> type, World world) {		
		Set<ItemStack> out = new HashSet<>();
		Set<IRecipe<?>> recipes = findRecipesByType(type, world);
		System.out.println(recipes + " " + world);
		for(IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> inputs = recipe.getIngredients();
			inputs.forEach(i -> {
				System.out.println(i); 
				for(ItemStack stack : i.getMatchingStacks()) out.add(stack);}); 
		}
		return out;
	}
}
