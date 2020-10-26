package com.xX_deadbush_Xx.hocus.api.crafting.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public abstract class ToolTableSpecialRecipe implements IToolTableRecipe {
	private final ResourceLocation id;

	public ToolTableSpecialRecipe(ResourceLocation idIn) {
		this.id = idIn;
	}

	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public Map<Ingredient, Integer> getTools() {
		return new HashMap<>();
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
	protected List<ItemStack> getInputs(RecipeWrapper inv) {
		List<ItemStack> inputs = new ArrayList<>();
		for (int i = 0; i < 9; ++i)
			if (!inv.getStackInSlot(i).isEmpty())
				inputs.add(inv.getStackInSlot(i));
		return inputs;
	}
	
	protected List<ItemStack> getTools(RecipeWrapper inv) {
		List<ItemStack> tools = new ArrayList<>();
		for (int i = 9; i < 12; ++i)
			if (!inv.getStackInSlot(i).isEmpty())
				tools.add(inv.getStackInSlot(i));
		return tools;
	}
}
