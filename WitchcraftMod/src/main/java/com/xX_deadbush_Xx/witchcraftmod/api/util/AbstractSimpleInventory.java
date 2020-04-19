package com.xX_deadbush_Xx.witchcraftmod.api.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public abstract class AbstractSimpleInventory implements IInventory {
	List<ItemStack> inventory = new ArrayList<>();
	
	AbstractSimpleInventory(ItemStack... stacks) {
		for(ItemStack stack : stacks) {
			this.inventory.add(stack);
		}
	}
		
	@Override
	public void clear() {
		this.inventory.clear();
	}

	@Override
	public int getSizeInventory() {
		return inventory.size();
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.shrink(count);
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.inventory.get(index).copy();
		this.inventory.set(index, ItemStack.EMPTY);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory.set(index, stack);
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return false;
	}
	
	@Override
	public void markDirty() {}
}
