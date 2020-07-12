package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InfiniteSlot extends SlotItemHandler {

    public InfiniteSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }


    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        return super.onTake(thePlayer, stack);
    }

    @Override
    public int getSlotStackLimit() {
        return 1000000;
    }
}
