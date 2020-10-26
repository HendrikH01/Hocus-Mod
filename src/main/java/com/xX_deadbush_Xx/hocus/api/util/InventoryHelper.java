package com.xX_deadbush_Xx.hocus.api.util;

import com.xX_deadbush_Xx.hocus.api.inventory.SimpleItemHandler;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public class InventoryHelper {
	public static IItemHandlerModifiable getPartialInv(IItemHandler inv, int slot1, int slot2) {
		IItemHandlerModifiable out = new SimpleItemHandler(slot2 - slot1 + 1);
		for(int i = slot1; i <= slot2; i++) out.setStackInSlot(i - slot1, inv.getStackInSlot(i)); 
		return out;
	}
}
