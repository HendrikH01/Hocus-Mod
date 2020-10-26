package com.xX_deadbush_Xx.hocus.api.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
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
	
	public static ShapedRecipe getShapedRecipeFromResult(RecipeManager recipeManager, Item item) {
		Collection<IRecipe<?>> recipes = recipeManager.getRecipes();
		for(IRecipe<?> recipe : recipes) {

			if(recipe instanceof ShapedRecipe) {
				if(recipe.getRecipeOutput().getItem().equals(item)) return (ShapedRecipe)recipe;
			}
		}
		
		return null;
	}
	
	public static List<ItemStack> asList(RecipeWrapper inv) {
		List<ItemStack> out = new ArrayList<>();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			out.add(inv.getStackInSlot(i));
		}
		
		return out;
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
		for(IRecipe<?> recipe : recipes) {
			NonNullList<Ingredient> inputs = recipe.getIngredients();
			inputs.forEach(i -> {
				for(ItemStack stack : i.getMatchingStacks()) out.add(stack);}); 
		}
		return out;
	}

	public static <A, B> boolean checkMatchUnordered(final List<A> wanted, final List<B> gotten, BiPredicate<A, B> compare) {
		int wsize = wanted.size();		
		if(wsize != gotten.size()) return false;
		List<A> missing = new ArrayList<>(wanted);

		outer:
        for (int i = wsize - 1; i >= 0; i--) {  
            for (int j = 0; j < missing.size(); j++) {
                if (compare.test(missing.get(j), gotten.get(i))) {
                	missing.remove(j);
                	continue outer;
                }
            }
            return false;
        }
         return true;
	}
	
	
	public static <A, B> boolean checkMatchOrderedRotational(final List<A> wanted, final List<B> gotten, BiPredicate<A, B> compare) {
		int wsize = wanted.size();
		if(wsize != gotten.size()) return false;
		
		outer:
        for (int i = 0; i < wsize; i++) {
            for (int j = 0; j < wsize; j++) {
                if (!compare.test(wanted.get((i + j)%wsize), gotten.get(i))) {
                	continue outer;
                }
            }
            return true;
        }
        return false;
	}
}
