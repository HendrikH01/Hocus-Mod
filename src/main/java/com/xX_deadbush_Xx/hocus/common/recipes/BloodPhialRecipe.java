package com.xX_deadbush_Xx.hocus.common.recipes;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.hocus.api.crafting.recipes.ToolTableSpecialRecipe;
import com.xX_deadbush_Xx.hocus.common.items.BloodPhial;
import com.xX_deadbush_Xx.hocus.common.items.SacrificeKnife;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class BloodPhialRecipe extends ToolTableSpecialRecipe {
	public BloodPhialRecipe(ResourceLocation idIn) {
		super(idIn);
	}

	/**
	 * Used to check if a recipe matches current crafting inventory
	 */
	public boolean matches(RecipeWrapper inv, World worldIn) {
		ItemStack knife = getTools(inv).stream().filter(t -> t.getItem() == ModItems.SACRIFICE_KNIFE.get()).findFirst().orElse(ItemStack.EMPTY);

		List<Item> inputs = getInputs(inv).stream().map(s -> s.getItem()).collect(Collectors.toList());
		if (inputs.size() != 1) return false;
		return inputs.get(0) == ModItems.EMPTY_PHIAL.get() && SacrificeKnife.isBloody(knife);
	}

	/**
	 * Returns an Item that is the result of this recipe	
	 */
	public ItemStack getCraftingResult(RecipeWrapper inv) {
		List<ItemStack> tools = getTools(inv);
		ItemStack knife = tools.stream().filter(t -> t.getItem() == ModItems.SACRIFICE_KNIFE.get()).findFirst().orElse(ItemStack.EMPTY);
		ItemStack phial = new ItemStack(ModItems.BLOOD_PHIAL.get());
		BloodPhial.copySacrificeKnife(phial, knife);
		return phial;
	}

	/**
	 * Used to determine if this recipe can fit in a grid of the given width/height
	 */
	public boolean canFit(int width, int height) {
		return width >= 2 && height >= 2;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(RecipeWrapper inv) {
		NonNullList<ItemStack> remaining = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
		for(int i = 9; i < 12; i++) {
			if(inv.getStackInSlot(i).getItem() == ModItems.SACRIFICE_KNIFE.get()) {
				ItemStack stack = inv.getStackInSlot(i);
				SacrificeKnife.cleanKnife(stack);
				stack.attemptDamageItem(2, new Random(), null);
				if(inv.getStackInSlot(i).getDamage() < inv.getStackInSlot(i).getMaxDamage()) remaining.set(i, stack);
				break;
			}
		}
		
		return remaining;
	}

	public IRecipeSerializer<?> getSerializer() {
		return ModRecipeTypes.BLOOD_VIAL_SERIALIZER.get();
	}
}
