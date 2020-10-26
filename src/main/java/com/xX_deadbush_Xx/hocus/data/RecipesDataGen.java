package com.xX_deadbush_Xx.hocus.data;

import java.util.function.Consumer;

import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;

public class RecipesDataGen extends RecipeProvider {

	public RecipesDataGen(DataGenerator generator) {
		super(generator);
	}


	@Override
	public void registerRecipes(Consumer<IFinishedRecipe> consumer) {
		registerStandardRecipes(consumer);
	}
	
	private void registerStandardRecipes(Consumer<IFinishedRecipe> consumer) {
    	System.out.println("Creating recipes");
		ShapedRecipeBuilder.shapedRecipe(ModBlocks.VIBRANT_BLOCK.get())
				.key('P', ModItems.VIBRANT_CRYSTAL.get())
				.patternLine("PPP")
				.patternLine("PPP")
				.patternLine("PPP")
				.addCriterion("has_item", hasItem(ModItems.VIBRANT_CRYSTAL.get()))
				.build(consumer);
	}
	
	@Override
	public String getName() {
		return "Hocus Crafting recipes";
	}
}
