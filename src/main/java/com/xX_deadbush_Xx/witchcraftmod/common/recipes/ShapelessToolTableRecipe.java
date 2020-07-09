package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.AbstractToolTableRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ShapelessToolTableRecipe extends AbstractToolTableRecipe {

	public ShapelessToolTableRecipe(ShapelessRecipe shapeless, Map<Ingredient, Integer> tools) {
		super(shapeless, tools);
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		List<ItemStack> toolsIn = Arrays.asList(inv.getStackInSlot(9), inv.getStackInSlot(10), inv.getStackInSlot(11));
		if (!checkTools(toolsIn))
			return false;
		
		List<ItemStack> inputs = new ArrayList<>();
		for(int i = 0; i < 9; i++) if(!inv.getStackInSlot(i).isEmpty())inputs.add(inv.getStackInSlot(i));

		if(inputs.size() == this.ingredients.size()) {
			for(Ingredient ingred : this.ingredients) {
				for(int i =  0; i < inputs.size(); i++) {
					if(ingred.test(inputs.get(i))) {
						inputs.remove(i);
					} 
				} 
			}
		} else return false;
		
		return inputs.size() == 0;
	}

	private boolean checkTools(List<ItemStack> toolsIn) {
		for(Ingredient tool : this.tools.keySet()) 
			if(!toolsIn.stream().anyMatch(tool::test)) return false;
		return true;
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

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.TOOL_TABLE_SHAPED_SERIALIZER.get();
	}

	public Map<Ingredient, Integer> getTools() {
		return this.tools;
	}

	public ShapelessRecipe convertShapeless() {
		return new ShapelessRecipe(recipeId, "", output, ingredients);
	}
}