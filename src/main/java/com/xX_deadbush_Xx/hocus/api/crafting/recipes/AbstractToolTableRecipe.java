package com.xX_deadbush_Xx.hocus.api.crafting.recipes;

import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class AbstractToolTableRecipe implements IToolTableRecipe {
	
	protected ItemStack output;
	protected NonNullList<Ingredient> ingredients;
	protected ResourceLocation recipeId;
	protected Map<Ingredient, Integer> tools;

	public AbstractToolTableRecipe(ICraftingRecipe recipe, Map<Ingredient, Integer> tools) {
		this.output = recipe.getRecipeOutput();
		this.ingredients = recipe.getIngredients();
		this.recipeId = recipe.getId();
		this.tools = tools;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(RecipeWrapper inv) {
		NonNullList<ItemStack> remaining = IToolTableRecipe.super.getRemainingItems(inv);
		
		this.tools.forEach((tool, damage) -> {
			
			for(int i = 9; i < 12; i++) {
				if(tool.test(inv.getStackInSlot(i))) {
					remaining.set(i, inv.getStackInSlot(i));
					remaining.get(i).attemptDamageItem(damage, new Random(), null);
					if(inv.getStackInSlot(i).getDamage() >= inv.getStackInSlot(i).getMaxDamage()) remaining.set(i, ItemStack.EMPTY);
					break;
				}
			}
		});
		
		return remaining;
	}
	
	@Override
	public Map<Ingredient, Integer> getTools() {
		return this.tools;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output.copy();
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}
}
