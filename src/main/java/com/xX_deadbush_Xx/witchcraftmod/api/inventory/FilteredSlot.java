package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import java.util.function.Predicate;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class FilteredSlot extends SlotItemHandler {

    private Predicate<ItemStack> isItemValid;
    private int maxStackSize;

    public FilteredSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, int maxStackSize, Predicate<ItemStack> isItemValid) {
        super(itemHandler, index, xPosition, yPosition);
        this.maxStackSize = maxStackSize;
        this.isItemValid = isItemValid;
    }



    public FilteredSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, Predicate<ItemStack> isItemValid) {
        this(itemHandler, index, xPosition, yPosition, itemHandler.getSlotLimit(index), isItemValid);
    }

    @Override
    public int getSlotStackLimit() {
        return maxStackSize;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return isItemValid.test(stack);
    }
}
