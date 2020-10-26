package com.xX_deadbush_Xx.hocus.api.inventory;

import javax.annotation.Nonnull;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;

public class BottomlessBagInventory implements IItemHandlerModifiable {

    private final ItemStack bagitem;
    public int[] amount = new int[] {0};
    private ItemStack currentStack;

    public BottomlessBagInventory(ItemStack stack) {
        this.bagitem = stack;
        this.currentStack = ItemStack.EMPTY;
         this.loadData();
    }

    public ItemStack getStack() {
        return this.currentStack;
    }
    
    public ItemStack getShowStack() {
    	return this.getAmount() == 0 ? ItemStack.EMPTY : this.currentStack;
    }

    public void loadData() {
        CompoundNBT nbt = this.bagitem.getTag();
        if(nbt != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(1, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(nbt, list);
            this.currentStack = list.get(0);
            setAmount(nbt.getInt("totalAmount"));
        }
    }

    public void saveData() {
        CompoundNBT nbt = this.bagitem.getOrCreateTag();
        ItemStackHelper.saveAllItems(nbt, NonNullList.withSize(1, this.currentStack));
        nbt.putInt("totalAmount", this.getAmount());
        setAmount(0);
    }

    public void setCurrentStack(ItemStack stack, int amount) {
    	setAmount(stack.getCount());
        this.currentStack = stack;
        this.currentStack.setCount(1);
    }
    
    
    /**
     * This method ADDS the stack to the slot! 
     * I wanted to save myself the 3 ATs needed to get around problems created by vanilla methods calling this all the time. 
     * To set the stack and amount of the slot use setCurrentStack()
     * 
     * I know, weird code but there is no better alternative
     */
    @Deprecated
    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) { 	
        this.currentStack = stack;
        this.currentStack.setCount(1);   
        setAmount(this.getAmount() + stack.getCount());

    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (this.canInsert(stack)) {
            if (this.getAmount() == 0)
                this.currentStack = stack;
            if (!simulate) setAmount(this.getAmount() + stack.getCount());
            this.currentStack.setCount(1);
            return ItemStack.EMPTY;
        } else return stack;
    }

    public boolean canInsert(ItemStack stack) {
        return this.currentStack.isEmpty() || this.getAmount() == 0 || ItemStack.areItemsEqual(stack, this.currentStack);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.getAmount() >= amount) {
            ItemStack out = currentStack.copy();
            if (!simulate) {
            	setAmount(this.getAmount() - Math.min(amount, currentStack.getMaxStackSize()));
            }
            out.setCount(Math.min(amount, currentStack.getMaxStackSize()));
            if (this.getAmount() == 0 && !simulate)
                this.setAmount(0);
            return out;
        } else {
            ItemStack out = currentStack.copy();
            out.setCount(this.getAmount());
            if (!simulate) setAmount(0);
            this.currentStack = ItemStack.EMPTY;
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

    public int getAmount() {
        return this.amount[0];
    }

    public boolean isEmpty() {
        return this.currentStack.isEmpty() || this.getAmount() == 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return currentStack;
    }

	public void setAmount(int count) {
		this.amount[0] = count;
		if(count == 0) this.currentStack = ItemStack.EMPTY;
	}

	public void increaseAmount(int i) {
		this.amount[0] += i;
		if(amount[0] <= 0) {
			this.currentStack = ItemStack.EMPTY;
			amount[0] = 0;
		}
	}
}