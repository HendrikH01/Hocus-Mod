package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IMortarRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MortarRecipe implements IMortarRecipe {
	private final ResourceLocation id;
	private final ItemStack output;
	private Ingredient[] inputs;

	
	public MortarRecipe(ResourceLocation id, ItemStack output, Ingredient[] ingredients) {
		this.id = id;
		this.output = output;
		this.inputs = ingredients;
	}
	
	@Override
	public boolean matches(RecipeWrapper inv, @Nullable World worldIn) {
		List<Ingredient> missingIngredients = Lists.newArrayList(this.inputs);
		
		int invsize = inv.getSizeInventory();
		int itemcount = 0;
		
		for(int i = 0; i < invsize; i++) {
			ItemStack item = inv.getStackInSlot(i);
			if(item.isEmpty()) break;
			itemcount++;
			for(int j = 0; j < missingIngredients.size(); j++) {
				if(missingIngredients.get(j).test(item)) {
					missingIngredients.remove(j);
					break;
				}
			}
		}
		
		return missingIngredients.size() == 0 && itemcount == this.inputs.length;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return matches(inv, null) ? this.getRecipeOutput() : null;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.MORTAR_SERIALIZER.get();
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return NonNullList.from(null, this.inputs);
	}
}
