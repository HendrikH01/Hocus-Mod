package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class BottomlessBagInventory implements IItemHandlerModifiable {

    private final ItemStack bagitem;
    private int amount;
    private ItemStack currentStack;

    public BottomlessBagInventory(ItemStack stack) {
        this.bagitem = stack;
        this.currentStack = ItemStack.EMPTY;
        this.amount = 0;
        this.loadData();
    }

    public ItemStack getStack() {
        return this.currentStack;
    }

    public void loadData() {
        CompoundNBT nbt = this.bagitem.getTag();
        if(nbt != null) {
            NonNullList<ItemStack> list = NonNullList.withSize(1, ItemStack.EMPTY);
            ItemStackHelper.loadAllItems(nbt, list);
            System.out.println("AMOUNT: " + nbt.getInt("totalAmount"));
            this.amount = nbt.getInt("totalAmount");
            this.currentStack = list.get(0);
        }
    }

    public void saveData() {
        CompoundNBT nbt = this.bagitem.getOrCreateTag();
        nbt.remove("Items");
        ItemStackHelper.saveAllItems(nbt, NonNullList.withSize(1, this.currentStack));
        nbt.remove("totalAmount");
        nbt.putInt("totalAmount", this.amount);
        this.amount = 0;
    }

    public void setCurrentStack(ItemStack currentStack) {
        this.currentStack = currentStack;
        this.currentStack.setCount(1);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (this.canInsert(stack)) {
            if (this.amount == 0)
                this.currentStack = stack;
            if (!simulate) this.amount += stack.getCount();
            this.currentStack.setCount(1);
            System.out.println("INSERT_ITEM");
            return ItemStack.EMPTY;
        } else return stack;
    }

    private boolean canInsert(ItemStack stack) {
        return this.currentStack.isEmpty() || this.amount == 0 || stack.isItemEqual(this.currentStack);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.amount >= amount) {
            if (!simulate) this.amount -= Math.min(amount, currentStack.getMaxStackSize());
            ItemStack out = currentStack.copy();
            out.setCount(currentStack.getMaxStackSize());
            if (this.amount == 0)
                this.currentStack = ItemStack.EMPTY;
            return out;
        } else {
            ItemStack out = currentStack.copy();
            out.setCount(this.amount);
            if (!simulate) this.amount = 0;
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