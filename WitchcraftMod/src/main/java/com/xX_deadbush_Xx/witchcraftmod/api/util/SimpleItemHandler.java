package com.xX_deadbush_Xx.witchcraftmod.api.util;

import java.util.Arrays;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.items.IItemHandlerModifiable;

public class SimpleItemHandler implements IItemHandlerModifiable {
	protected ItemStack[] inventory;
	protected int size;
	
	public SimpleItemHandler(int size, ItemStack... stacks) {
		this.inventory =  new ItemStack[size];
		this.size = size;
		
		Arrays.fill(this.inventory, ItemStack.EMPTY);
		for(int i = 0; i < stacks.length; i++) {
			this.inventory[i] = stacks[i];
		}
	}
		
	public void clear() {
		for(int i = 0; i < this.size; i++) {
			this.inventory[i] = ItemStack.EMPTY;
		}
	}

	public boolean isEmpty() {
		for(ItemStack stack : this.inventory) {
			if(stack == ItemStack.EMPTY || stack.getItem() == Items.AIR) return true;
		}
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory[index];
	}

	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.shrink(count);
		return stack;
	}

	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.inventory[index].copy();
		this.inventory[index] = ItemStack.EMPTY;
		return stack;
	}

	@Override
	public int getSlots() {
		return this.size;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		return ItemStackHelper.mergeStacks(inventory[slot], stack);
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack stack = inventory[slot];
		ItemStack stack2 = stack.copy();
		if(stack.getCount() > amount) {
			stack2.setCount(amount);
			stack.shrink(amount);
		} else {
			stack = ItemStack.EMPTY;
		}
		return stack2;
	}

	@Override
	public int getSlotLimit(int slot) {
		return this.size;
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		if(inventory[slot].equals(ItemStack.EMPTY)) return true;
		return ItemStackHelper.canMergeStacks(inventory[slot], stack);
	}

	@Override
	public void setStackInSlot(int slot, ItemStack stack) {
		this.inventory[slot] = stack;
	}
}
