package com.xX_deadbush_Xx.hocus.api.util;

import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ModNBTUtil {
	public static int readTickCounter(ItemStack stack) {
		CompoundNBT tag =  stack.getOrCreateTag();
		if(tag.contains("tick_counter")) {
			return tag.getInt("tick_counter");
		}
		
		return ((IManaTickingItem)stack.getItem()).getTickInterval();
	}
	
	public static void writeTickCounter(ItemStack stack, int tick) {
		stack.getOrCreateTag().putInt("tick_counter", tick);
	}
	
	public static boolean toggleShiftMode(CompoundNBT tag) {
		boolean b = !tag.getBoolean("shiftmode");
		tag.putBoolean("shiftmode", b);
		return b;
	}
	
	public static boolean getShiftMode(CompoundNBT tag) {
		return !tag.getBoolean("shiftmode");
	}
}
