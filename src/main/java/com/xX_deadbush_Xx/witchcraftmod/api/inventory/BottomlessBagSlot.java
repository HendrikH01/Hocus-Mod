package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BottomlessBagSlot extends SlotItemHandler {

    public BottomlessBagSlot(BottomlessBagInventory itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }
    
    @Override
    public ItemStack getStack() {
        return this.getItemHandler().getStackInSlot(0);
    }

    @Override
    public void onSlotChange(@Nonnull ItemStack oldStackIn, @Nonnull ItemStack newStackIn) {
        this.getItemHandler().insertItem(0, newStackIn, false);
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        return super.onTake(thePlayer, stack);
    }

    @Override
    public int getSlotStackLimit() {
        return Integer.MAX_VALUE;
    }
    
    @Override
    public int getItemStackLimit(ItemStack stack) {
        ItemStack maxAdd = stack.copy();
        int maxInput = stack.getMaxStackSize();
        maxAdd.setCount(maxInput);
        BottomlessBagInventory handler = (BottomlessBagInventory) this.getItemHandler();
		ItemStack remainder = handler.canInsert(maxAdd) ? maxAdd : ItemStack.EMPTY;
		return maxInput - remainder.getCount();
    }
    
    @Override
    public void putStack(@Nonnull ItemStack stack)
    {
        ((BottomlessBagInventory) this.getItemHandler()).setCurrentStack(stack, 0);
        this.onSlotChanged();
    }
}
