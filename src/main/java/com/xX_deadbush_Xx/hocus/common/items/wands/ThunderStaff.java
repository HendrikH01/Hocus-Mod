package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.common.entity.LightningBallEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ThunderStaff extends ChargingWanditem {
	
	public ThunderStaff(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean onFinishWandUse(World worldIn, PlayerEntity player, ItemStack wand, int timeLeft) {
		Vec3d vec = player.getEyePosition(1);
		Vec3d look = player.getLookVec().scale(0.2);
		LightningBallEntity entity = new LightningBallEntity(worldIn, vec.x, vec.y, vec.z, look.x, look.y, look.z, 2*((float)Math.min(72, getUseDuration(wand) - timeLeft)/72));
		worldIn.addEntity(entity);
		return true;
	}
	
	@Override
	public int getEnergyPerUse() {
		return 500;
	}

	@Override
	public int getCooldown() {
		return 0;
	}

	@Override
	public int getUseDuration(ItemStack wand) {
		return 72000;
	}

	@Override
	public int getEnergyUsedWhileCharging(ItemStack wand) {
		return 1;
	}	
 }