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
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class CraftingHelper {	
	
	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type, World world) {
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<IRecipe<?>> findRecipesByType(IRecipeType<?> type) {
		@SuppressWarnings("resource")
		ClientWorld world = Minecraft.getInstance().world;
		return world != null ? world.getRecipeManager().getRecipes().stream().filter(r -> r.getType() == type).collect(Collectors.toSet()) : Collections.emptySet();
	}
	
	public static Set<ItemStack> getAllRecipeInputs(IRecipeType<?> type, World world) {		
		Set<ItemStack> out = new HashSet<>();
		Set<IRecipe<?>> recipes = findRecipesByType(type, world);
		for(IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> inputs = recipe.getIngredients();
			inputs.forEach(i -> {
				for(ItemStack stack : i.getMatchingStacks()) out.add(stack);}); 
		}
		return out;
	}
	
	public static RecipeWrapper trimEmptySlots(RecipeWrapper inv) {
		IItemHandlerModifiable out = new ItemStackHandler();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if(!stack.isEmpty()) {
				out.insertItem(i, stack, false);
			}
		}
		
		return new RecipeWrapper(out);
	}
}
