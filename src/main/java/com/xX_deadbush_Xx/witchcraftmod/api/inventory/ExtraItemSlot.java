package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import com.xX_deadbush_Xx.witchcraftmod.api.util.SlotSupplier;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ExtraItemSlot extends SlotItemHandler {

    private SlotSupplier isItemValid;
    private int maxStackSize;

    public ExtraItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, int maxStackSize, SlotSupplier isItemValid) {
        super(itemHandler, index, xPosition, yPosition);
        this.maxStackSize = maxStackSize;
        this.isItemValid = isItemValid;
    }



    public ExtraItemSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, SlotSupplier isItemValid) {
        this(itemHandler, index, xPosition, yPosition, itemHandler.getSlotLimit(index), isItemValid);
    }

    @Override
    public int getSlotStackLimit() {
        return maxStackSize;
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack) {
        return isItemValid.invoke(stack);
    }
}
