package com.xX_deadbush_Xx.hocus.common.items.talisman;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.LogicalSide;

public class CelestialTablet extends TalismanItem {
	
	public CelestialTablet(Properties properties) {
		super(properties);
	}

	@Override
	public int getTickInterval() {
		return 1;
	}

	@Override
	public int getManaCostPerTick() {
		return 10;
	}

	@Override
	public boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		player.abilities.allowFlying = true;
		return !player.abilities.isCreativeMode && player.abilities.isFlying;
	}
	
	@Override
	public void inactiveTick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		if(!player.abilities.isCreativeMode) {
			player.abilities.allowFlying = false;
			player.abilities.isFlying = false;
		}
	}
}
