package com.xX_deadbush_Xx.witchcraftmod.client;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class WitchcraftItemGroup extends ItemGroup {
	public static final WitchcraftItemGroup instance = new WitchcraftItemGroup(ItemGroup.GROUPS.length, "witchcraftmod");
	
	public WitchcraftItemGroup(int index, String label) {
		super(index, label);

	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(Items.DEAD_BUSH);
	}
	
}