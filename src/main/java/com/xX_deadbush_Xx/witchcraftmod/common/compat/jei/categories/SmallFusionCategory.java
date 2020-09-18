package com.xX_deadbush_Xx.witchcraftmod.common.compat.jei.categories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.common.recipes.SmallFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

public class SmallFusionCategory implements IRecipeCategory<SmallFusionRecipe> {
	private final String localizedName;
	private final IDrawable background;
	private final IDrawable icon;
	
    public SmallFusionCategory(IGuiHelper guiHelper) {
		localizedName = I18n.format("witchcraftmod.jei.small_fusion");
		background = guiHelper.createBlankDrawable(150, 130);
		icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.RITUAL_STONE.get()));
    }

	public static final ResourceLocation UID = new ResourceLocation("witchcraftmod", "small_fusion");

	@Override
	public ResourceLocation getUid() {
		return this.UID;
	}

	@Override
	public Class<? extends SmallFusionRecipe> getRecipeClass() {
		return SmallFusionRecipe.class;
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
	public void setRecipe(IRecipeLayout recipeLayout, SmallFusionRecipe recipe, IIngredients ingredients) {
		recipeLayout.getItemStacks().init(0, true, 64, 80);
		recipeLayout.getItemStacks().set(0, new ItemStack(ModBlocks.RITUAL_STONE.get()));
		
		recipeLayout.getItemStacks().init(1, true, 64, 2);
		recipeLayout.getItemStacks().set(1, recipe.getRecipeOutput());
		 
		List<List<ItemStack>> stacklistlist = ingredients.getInputs(VanillaTypes.ITEM);
		if(stacklistlist.size() != 5) {
			System.out.println("NOT 5 INGREDIENTS!");
		}
		recipeLayout.getItemStacks().init(1, true, 64, 68);
		recipeLayout.getItemStacks().set(1, stacklistlist.get(0));
		recipeLayout.getItemStacks().init(3, true, 64, 102);
		recipeLayout.getItemStacks().set(3, stacklistlist.get(1));
		recipeLayout.getItemStacks().init(4, true, 96, 74);
		recipeLayout.getItemStacks().set(4, stacklistlist.get(2));
		recipeLayout.getItemStacks().init(5, true, 64, 42);
		recipeLayout.getItemStacks().set(5, stacklistlist.get(3));
		recipeLayout.getItemStacks().init(6, true, 32, 74);
		recipeLayout.getItemStacks().set(6, stacklistlist.get(4));
	}

	@Override
	public void setIngredients(SmallFusionRecipe recipe, IIngredients ingredients) {
		List<List<ItemStack>> list = new ArrayList<>();
		for (Ingredient ingr : recipe.getIngredients()) {
			list.add(Arrays.asList(ingr.getMatchingStacks()));
		}
		
		ingredients.setInputLists(VanillaTypes.ITEM, list);
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	} 
	
	/*
	@Override 
	public void draw(IAltarRecipe recipe, double mouseX, double mouseY) {
		RenderSystem.enableAlphaTest();
		RenderSystem.enableBlend();
		//... overlay etc
		RenderSystem.disableBlend();
		RenderSystem.disableAlphaTest();
	}*/
}
