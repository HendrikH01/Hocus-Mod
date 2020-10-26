package com.xX_deadbush_Xx.hocus.api.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IRecipeHelperPopulator;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.util.NonNullList;

/**
 * Used in Tool Table for the extra tool slots
 */
public class ToolInventory implements IInventory, IRecipeHelperPopulator { 
	private final NonNullList<ItemStack> stackList;
	private final Container container;

	public ToolInventory(Container container, int size) {
		this.container = container;
		this.stackList = NonNullList.withSize(size, ItemStack.EMPTY);
	}

	public int getSizeInventory() {
	      return this.stackList.size();
	   }

	public boolean isEmpty() {
		for(ItemStack itemstack : this.stackList) {
			if (!itemstack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	public ItemStack getStackInSlot(int index) {
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : this.stackList.get(index);
	}

	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.stackList, index);
	}

	public ItemStack decrStackSize(int index, int count) {
		ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);
		if (!itemstack.isEmpty()) {
			this.container.onCraftMatrixChanged(this);
		}	
		return itemstack;
	}

	public void setInventorySlotContents(int index, ItemStack stack) {
		this.stackList.set(index, stack);
		this.container.onCraftMatrixChanged(this);
	}

	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	public void clear() {
		this.stackList.clear();
	}

	public void fillStackedContents(RecipeItemHelper helper) {
		for(ItemStack itemstack : this.stackList) {
			helper.accountPlainStack(itemstack);
		}
	}

	@Override
	public void markDirty() {}
}
