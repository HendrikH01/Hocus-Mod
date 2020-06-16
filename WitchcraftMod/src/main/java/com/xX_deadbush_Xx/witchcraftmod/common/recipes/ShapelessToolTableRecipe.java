package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IToolTableRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ToolTableCraftingInventory;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.item.crafting.ShapelessRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class ShapelessToolTableRecipe implements IToolTableRecipe {

	private ItemStack output;
	private NonNullList<Ingredient> ingredients;
	private ResourceLocation recipeId;
	private Map<Ingredient, Integer> tools;
	private boolean isSimple;

	public ShapelessToolTableRecipe(ShapelessRecipe shapeless, Map<Ingredient, Integer> tools) {
		this.output = shapeless.getRecipeOutput();
		this.ingredients = shapeless.getIngredients();
		this.recipeId = shapeless.getId();
		this.tools = tools;
	    this.isSimple = this.ingredients.stream().allMatch(Ingredient::isSimple);
	}

	@Override
	public boolean matches(RecipeWrapper inv, World worldIn) {
		List<ItemStack> toolsIn = Arrays.asList(inv.getStackInSlot(9), inv.getStackInSlot(10), inv.getStackInSlot(11));

		if (!checkTools(toolsIn))
			return false;

		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
		List<ItemStack> inputs = new ArrayList<>();
		int i = 0;

		for (int j = 0; j < 9; ++j) {
			ItemStack itemstack = inv.getStackInSlot(j);
			if (!itemstack.isEmpty()) {
				++i;
				if (isSimple)
					recipeitemhelper.func_221264_a(itemstack, 1);
				else
					inputs.add(itemstack);
			}
		}

		return i == this.ingredients.size() && (isSimple ? recipeitemhelper.canCraft(this, (IntList) null)
				: RecipeMatcher.findMatches(inputs, this.ingredients) != null);
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