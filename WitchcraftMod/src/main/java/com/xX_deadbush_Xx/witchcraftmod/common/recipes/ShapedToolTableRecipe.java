package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.mojang.datafixers.util.Pair;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IToolTableRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ToolTableCraftingInventory;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ShapedToolTableRecipe implements IToolTableRecipe {

	private ItemStack output;
	private NonNullList<Ingredient> ingredients;
	private ResourceLocation recipeId;
	private Map<Ingredient, Integer> tools;
	private int width;
	private int height;

	public ShapedToolTableRecipe(ShapedRecipe shaped, Map<Ingredient, Integer> tools) {
		this.output = shaped.getRecipeOutput();
		this.ingredients = shaped.getIngredients();
		this.recipeId = shaped.getId();
		this.tools = tools;
		this.width = shaped.getWidth();
		this.height = shaped.getWidth();
	}
	
	private ToolTableCraftingInventory getCraftingInv(RecipeWrapper inv) {
		NonNullList<ItemStack> list = NonNullList.create();
		for(int i = 0; i < 9; i++) list.add(inv.getStackInSlot(i));
		return new ToolTableCraftingInventory(null, 3, 3, list);
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		ToolTableCraftingInventory craftingGridIn = getCraftingInv(inv);
		List<ItemStack> toolsIn = Arrays.asList(inv.getStackInSlot(9), inv.getStackInSlot(10), inv.getStackInSlot(11));

		if(!checkTools(toolsIn)) return false;

		for (int i = 0; i <= craftingGridIn.getWidth() - this.width; ++i) {
			for (int j = 0; j <= craftingGridIn.getHeight() - this.height; ++j) {
				if (this.checkMatch(craftingGridIn, i, j, true)) {
					return true;
				} 
				if (this.checkMatch(craftingGridIn, i, j, false)) {
					return true;
				} 
			}
		}

		return false;
	}

	private boolean checkTools(List<ItemStack> toolsIn) {
		for(Ingredient tool : this.tools.keySet()) 
			if(!toolsIn.stream().anyMatch(tool::test)) return false;
		return true;
	}

	private boolean checkMatch(CraftingInventory craftingInventory, int slotx, int sloty, boolean mirrored) {
		for (int i = 0; i < craftingInventory.getWidth(); ++i) {
			for (int j = 0; j < craftingInventory.getHeight(); ++j) {
				int k = i - slotx;
				int l = j - sloty;
				
				Ingredient ingredient = Ingredient.EMPTY;
				if (k >= 0 && l >= 0 && k < this.width && l < this.height) {
					if (mirrored) {
						ingredient = this.ingredients.get(this.width - k - 1 + l * this.width);
					} else {
						ingredient = this.ingredients.get(k + l * this.width);
					}
				}
				if (!ingredient.test(craftingInventory.getStackInSlot(i + j * craftingInventory.getWidth()))) {
					return false;
				}
			}
		}

		return true;
	}
	
	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.getRecipeOutput();
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.recipeId;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.TOOL_TABLE_SHAPED_SERIALIZER.get();
	}

	public ShapedRecipe convertShaped() {
		return new ShapedRecipe(recipeId, "", 3, 3, ingredients, output);
	}

	public Map<Ingredient, Integer> getTools() {
		return this.tools;
	}
}