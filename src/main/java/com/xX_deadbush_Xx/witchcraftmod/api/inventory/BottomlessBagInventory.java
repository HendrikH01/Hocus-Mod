package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

public class BottomlessBagInventory implements IItemHandlerModifiable {

	private final ItemStack bagitem;
	private int amount = 0;
	private ItemStack currentStack = ItemStack.EMPTY;

	public BottomlessBagInventory(ItemStack stack) {
		this.bagitem = stack;
		// readItemStack();
	}

	public ItemStack getStack() {
		return this.bagitem;
	}

	/*
	 * public void readItemStack() { if (this.bagitem.getTag() != null) {
	 * readNBT(this.bagitem.getTag()); } }
	 * 
	 * public void writeItemStack() { if (isEmpty()) {
	 * this.bagitem.removeChildTag("Items"); } else {
	 * writeNBT(this.bagitem.getOrCreateTag()); } }
	 * 
	 * private void readNBT(CompoundNBT compound) { final NonNullList<ItemStack>
	 * list = NonNullList.withSize(1, ItemStack.EMPTY);
	 * ItemStackHelper.loadAllItems(compound, list); setStackInSlot(0, list.get(0));
	 * this.amount = compound.getInt("stackAmount"); }
	 * 
	 * private void writeNBT(CompoundNBT compound) { final NonNullList<ItemStack>
	 * list = NonNullList.withSize(1, currentStack);
	 * ItemStackHelper.saveAllItems(compound, list, false);
	 * compound.putInt("stackAmount", amount); }
	 */

	@Override
	public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
		this.currentStack = stack;
		amount = stack.getCount();
		System.out.println("SET_STACK_IN_SLOT");
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Nonnull
	@Override
	public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
		if(this.canInsert(stack)) {
			if(!simulate) this.amount += stack.getCount();
			System.out.println("INSERT_ITEM");
			return ItemStack.EMPTY;
		} else return stack;
	}

	private boolean canInsert(ItemStack stack) {
		return this.currentStack.isEmpty() || this.amount == 0 || stack.equals(this.currentStack);
	}

	@Nonnull
	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		if (this.amount > amount) {
			if(!simulate) this.amount -= amount;
			return ItemStack.EMPTY;
		} else {
			ItemStack out = currentStack.copy();
			out.setCount(amount - this.amount);
			if(!simulate) this.amount = 0;
			return out;
		}
	}

	@Override
	public int getSlotLimit(int slot) {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
		return true;
	}

	public int getActuallyStack() {
		return amount;
	}

	public boolean isEmpty() {
		return this.currentStack.isEmpty() || this.amount == 0;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return currentStack;
	}
}