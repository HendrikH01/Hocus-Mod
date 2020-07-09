package com.xX_deadbush_Xx.witchcraftmod.common.compat.jei.categories;

import java.util.Arrays;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IDryingRackRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class DryingRackCategory implements IRecipeCategory<IDryingRackRecipe> {
	private final String localizedName;
	private final IDrawable background;
	private final IDrawable icon;
	
    public DryingRackCategory(IGuiHelper guiHelper) {
		localizedName = I18n.format("witchcraftmod.jei.drying_rack");
		background = guiHelper.createBlankDrawable(150, 130);
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.DRYING_RACK.get()));
    }

	public static final ResourceLocation UID = IDryingRackRecipe.TYPE_ID;

	@Override
	public ResourceLocation getUid() {
		return this.UID;
	}

	@Override
	public Class<? extends IDryingRackRecipe> getRecipeClass() {
		return IDryingRackRecipe.class;
	}
		
	@Override
	public String getTitle() {
		return this.localizedName;
	}

	@Override
	public IDrawable getBackground() {
		return this.background;
	}

	@Override
	public IDrawable getIcon() {
		return this.icon;
	}


	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IDryingRackRecipe recipe, IIngredients ingredients) { //GUI for JEI
		recipeLayout.getItemStacks().init(0, true, 64, 80);
		recipeLayout.getItemStacks().set(0, new ItemStack(ModBlocks.DRYING_RACK.get()));
		 		
		recipeLayout.getItemStacks().init(1, true, 64, 2);
		recipeLayout.getItemStacks().set(1, recipe.getRecipeOutput());
		 
		List<ItemStack> stacklistlist = ingredients.getInputs(VanillaTypes.ITEM).get(0);
		recipeLayout.getItemStacks().init(2, true, 64, 102);
		recipeLayout.getItemStacks().set(2, stacklistlist.get(0));
	}

	@Override
	public void setIngredients(IDryingRackRecipe recipe, IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, Arrays.asList(recipe.getIngredients().get(0).getMatchingStacks()));
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	} 
}
