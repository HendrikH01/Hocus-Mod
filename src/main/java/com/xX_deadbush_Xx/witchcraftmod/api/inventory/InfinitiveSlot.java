package com.xX_deadbush_Xx.witchcraftmod.api.inventory;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class InfinitiveSlot extends SlotItemHandler {

    public InfinitiveSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public int getSlotStackLimit() {
        return Integer.MAX_VALUE;
    }
}
