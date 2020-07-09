package com.xX_deadbush_Xx.witchcraftmod.common.items;

import net.minecraft.item.ItemStack;

public interface IWandItem {
	int getEnergyPerUse(ItemStack wand);

	int getCooldown();
}
