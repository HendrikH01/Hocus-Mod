package com.xX_deadbush_Xx.hocus.common.items.talisman;

import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;

import net.minecraft.item.Item;

public abstract class TalismanItem extends Item implements IManaTickingItem {

	public TalismanItem(Properties properties) {
		super(properties.maxStackSize(1));
	}
}
