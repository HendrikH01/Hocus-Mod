package com.xX_deadbush_Xx.hocus.common.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class RecipeItem extends Item {

	public RecipeItem(Properties properties) {
		super(properties);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if (stack.getOrCreateTag().contains("output")) {
			tooltip.add(new TranslationTextComponent("Current: " + "\u00A7d" + stack.getOrCreateTag().getString("output") + "\u00A7r"));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
