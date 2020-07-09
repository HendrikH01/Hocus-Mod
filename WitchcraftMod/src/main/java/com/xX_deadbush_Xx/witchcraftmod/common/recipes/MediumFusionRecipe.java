package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IMediumFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MediumFusionRecipe implements IMediumFusionRecipe {
	private final ResourceLocation id;
	private final ItemStack output;
	private NonNullList<Ingredient> inputs;
	private final boolean shapeless;

	
	public MediumFusionRecipe(ResourceLocation id, ItemStack output, boolean shapeless, Ingredient... inputs) {
		this.id = id;
		this.output = output;
		this.inputs = NonNullList.create();
		this.shapeless = shapeless;
		for(Ingredient input : inputs) {
			this.inputs.add(input);
		}
	}
	
	@Override
	public boolean matches(RecipeWrapper inv, @Nullable World worldIn) {
		List<Ingredient> missingIngredients = new ArrayList<>(this.inputs); 
		if(!missingIngredients.get(0).test(inv.getStackInSlot(0))) return false; //center Item
		
		int invsize = inv.getSizeInventory();
		if(shapeless) {
			for(int i = 1; i < invsize; i++) {
				for(int j = 1; j < missingIngredients.size(); j++) {
					if(missingIngredients.get(j).test(inv.getStackInSlot(i))) {
						missingIngredients.remove(j);
						break;
					}
				}
			}
		} else {
			for(int i = 1; i < invsize; i++) {
				for(int j = 1; j < invsize; j++) {
					if(missingIngredients.get(j).test(inv.getStackInSlot((j+i)%invsize))) {
						missingIngredients.remove(j);
					} else break;	
				}
			}
		}
		missingIngredients.remove(0);
		return missingIngredients.size() == 0;
	}

	@Override
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		return this.output;
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
		return ModRecipeTypes.MEDIUM_FUSION_SERIALIZER.get();
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	public boolean isShapeless() {
		return this.shapeless;
	}
}