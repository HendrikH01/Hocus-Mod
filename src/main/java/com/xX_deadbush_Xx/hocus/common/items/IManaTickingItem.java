package com.xX_deadbush_Xx.hocus.common.items;

import com.xX_deadbush_Xx.hocus.api.util.ModNBTUtil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public interface IManaTickingItem {
	/**
	 * called at the end of PlayerTickEvent if the player has an item that implements this in his inventory
	 * 
	 * @param stack
	 * @param player
	 */
	default int tick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		int interval = this.getTickInterval();
		if(interval <= 0) return 0;
		int tick = ModNBTUtil.readTickCounter(stack) - 1;
		if(tick > 0) {
			ModNBTUtil.writeTickCounter(stack, tick);
		} else {
			if(interval != 1) ModNBTUtil.writeTickCounter(stack, interval);
			if(this.effectTick(stack, player, side)) return this.getManaCostPerTick();
		}
		
		return 0;
	}
	
	public int getTickInterval();
	
	public int getManaCostPerTick();
	
	public boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side);
}
