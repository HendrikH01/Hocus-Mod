package com.xX_deadbush_Xx.witchcraftmod.common.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public interface IPlayerInventoryTickingItem {
	/**
	 * called at the end of PlayerTickEvent if the player has an item that implements this in his inventory
	 * 
	 * @param stack
	 * @param player
	 */
	void tick(ItemStack stack, PlayerEntity player, LogicalSide side);
}
