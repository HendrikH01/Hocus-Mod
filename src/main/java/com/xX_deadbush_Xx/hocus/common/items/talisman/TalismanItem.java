package com.xX_deadbush_Xx.hocus.common.items.talisman;

import com.xX_deadbush_Xx.hocus.common.items.IManaTickingItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public abstract class TalismanItem extends Item implements IManaTickingItem {

	public TalismanItem(Properties properties) {
		super(properties.maxStackSize(1));
	}
	
	/**
	 * Gets called when there is not enough mana to use the item
	 * 
	 * @param stack
	 * @param player
	 * @param side
	 */
	public void inactiveTick(ItemStack stack, PlayerEntity player, LogicalSide side) {}
}
