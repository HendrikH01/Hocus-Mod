package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import com.xX_deadbush_Xx.witchcraftmod.common.tags.ModItemTags;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ToolSlot extends SlotItemHandler {
	
	private final Container tooltable;

	public ToolSlot(IItemHandler itemHandler, Container tooltable, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.tooltable = tooltable;
	}

	@Override 
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem().isIn(ModItemTags.TOOL_TABLE_TOOL);
	}
	
	@Override
	public int getSlotStackLimit() {
		return 1;
	}
	
	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		this.tooltable.onCraftMatrixChanged(null);
	}
}
