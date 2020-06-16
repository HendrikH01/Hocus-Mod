package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

public class CraftingInventoryFilled extends CraftingInventory {
	public CraftingInventoryFilled(Container eventHandlerIn, int width, int height, NonNullList<ItemStack> inv) {
		super(eventHandlerIn, width, height);
		for(int i=0; i<9; i++) this.stackList.set(i, inv.get(i));

	}
	
	public CraftingInventoryFilled(Container eventHandlerIn, int width, int height, IItemHandler invIn) {
		super(eventHandlerIn, width, height);
		for(int i=0; i<9; i++) this.stackList.set(i, invIn.getStackInSlot(i));
	}
}
