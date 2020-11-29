package com.xX_deadbush_Xx.hocus.api.inventory;

import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class RecipeSlot extends SlotItemHandler{

	private final Container tooltable;
	
	public RecipeSlot(IItemHandler itemHandler, Container tooltable, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
		this.tooltable = tooltable;
	}
	@Override 
	public boolean isItemValid(ItemStack stack) {
		return stack.getItem().equals(Items.OAK_PLANKS) || stack.getItem().equals(ModItems.RECIPE.get());
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
