package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

public class SimpleItemHandler extends ItemStackHandler {
	
	public SimpleItemHandler(int size, ItemStack... stacks) {
		super(size);
		
		for(int i = 0; i < stacks.length; i++) {
			this.stacks.set(i, stacks[i]);
		}
	}
		
	public void clear() {
		for(int i = 0; i < this.getSlots(); i++) {
			this.stacks.set(i, ItemStack.EMPTY);
			this.onContentsChanged(i);
		}
	}

	public boolean isEmpty() {
		for(ItemStack stack : this.stacks) {
			if(stack.isEmpty() || stack.getItem() == Items.AIR) return true;
		}
		return false;
	}

	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.shrink(count);
		this.onContentsChanged(index);
		return stack;
	}
	
	public void removeStackFromSlot(int index) {
		this.stacks.set(index, ItemStack.EMPTY);
		this.onContentsChanged(index);
	}

	public NonNullList<ItemStack> toNonNullList() {
		NonNullList<ItemStack> out = NonNullList.create();
		for(ItemStack stack : this.stacks) out.add(stack);
		return out;
	}

	public void setNonNullList(NonNullList<ItemStack> inv) {
		if(inv.size() == 0) return;
		if(inv.size() != this.getSlots()) throw new IndexOutOfBoundsException("NonNullList not the same size as ItemHandler!");
		for(int i = 0; i < inv.size(); i++) {
			this.stacks.set(i, inv.get(i));
		}
	}
	
	@Override
	public String toString() {
		return this.stacks.toString();
	}
}
