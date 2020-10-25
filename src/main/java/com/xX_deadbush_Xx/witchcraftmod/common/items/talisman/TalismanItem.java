package com.xX_deadbush_Xx.witchcraftmod.common.items.talisman;

import com.xX_deadbush_Xx.witchcraftmod.common.items.IManaTickingItem;

import net.minecraft.item.Item;

public abstract class TalismanItem extends Item implements IManaTickingItem {

	public TalismanItem(Properties properties) {
		super(properties.maxStackSize(1));
	}
}
