package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class ThunderStaff extends ChargingWanditem {
	
	public ThunderStaff(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean onFinishWandUse(World worldIn, PlayerEntity player, ItemStack wand, int timeLeft) {
		player.sendMessage(new StringTextComponent("success! hooray "  + timeLeft));
		return true;
	}
	
	@Override
	public int getEnergyPerUse(ItemStack wand) {
		return 500;
	}

	@Override
	public int getCooldown() {
		return 0;
	}

	@Override
	public int getUseDuration(ItemStack wand) {
		return 72;
	}

	@Override
	public int getEnergyUsedWhileCharging(ItemStack wand) {
		return 1;
	}	
 }