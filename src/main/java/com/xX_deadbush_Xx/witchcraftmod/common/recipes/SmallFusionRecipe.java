package com.xX_deadbush_Xx.witchcraftmod.common.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class SmallFusionRecipe implements IFusionRecipe {
	public static final ResourceLocation TYPE_ID = new ResourceLocation(WitchcraftMod.MOD_ID, "small_fusion");
	private final ResourceLocation id;
	private final ItemStack output;
	private NonNullList<Ingredient> inputs;
	private final boolean shapeless;
	private int activationcost;

	
	public SmallFusionRecipe(ResourceLocation id, ItemStack output, boolean shapeless, int activationcost, Ingredient... inputs) {
		this.id = id;
		this.output = output;
		this.inputs = NonNullList.create();
		this.shapeless = shapeless;
		this.activationcost = activationcost;
		for(Ingredient input : inputs) {
			this.inputs.add(input);
		}
	}
	
	@Override
	public boolean matches(RecipeWrapper inv, @Nullable World worldIn) {
		if(shapeless) {
			return CraftingHelper.checkMatchUnordered(this.inputs, CraftingHelper.asList(inv), (ingred, stack) -> ingred.test(stack));
		} else {
			return CraftingHelper.checkMatchOrderedRotational(this.inputs, CraftingHelper.asList(inv), (ingred, stack) -> ingred.test(stack));
		}
	}

	@Nonnull
	@Override
	public IRecipeType<?> getType() {
		return Registry.RECIPE_TYPE.getValue(TYPE_ID).get();
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
		return ModRecipeTypes.SMALL_FUSION_SERIALIZER.get();
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputs;
	}

	public boolean isShapeless() {
		return this.shapeless;
	}

	@Override
	public int getActivationCost() {
		return this.activationcost;
	}
}
