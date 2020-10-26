package com.xX_deadbush_Xx.hocus.api.inventory;

import net.minecraft.inventory.container.Container;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class CraftingSlot extends SlotItemHandler {
	
	private final Container tooltable;

	public CraftingSlot(IItemHandler itemHandler, Container tooltable, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.tooltable = tooltable;
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		this.tooltable.onCraftMatrixChanged(null);
	}
}
