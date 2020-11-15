package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class ChargingWanditem extends WandItem {

	public ChargingWanditem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onUse(World worldIn, LivingEntity entity, ItemStack wand, int count) {
		if(!(entity instanceof PlayerEntity)) return;
		PlayerEntity player = (PlayerEntity)entity;
			
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player);

		if (storage.getEnergy() < energyneeded)
			return;
		
		storage.removeEnergy(getEnergyUsedWhileCharging(wand));
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
		if(canUse(playerIn, wand))playerIn.setActiveHand(handIn);
		return ActionResult.resultSuccess(wand);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack wand, World worldIn, LivingEntity entity, int timeLeft) {
		if(!(entity instanceof PlayerEntity)) return;
		PlayerEntity player = (PlayerEntity)entity;
		
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player);

		if (storage == null)
			return;
		if (storage.getEnergy() < energyneeded)
			return;

		if(onFinishWandUse(worldIn, player, wand, timeLeft)) {
			storage.removeEnergy(energyneeded);
			player.getCooldownTracker().setCooldown(this, getCooldown());
		}
	}
	
	/**
	 * Return true if use was successful
	 */
	protected abstract boolean onFinishWandUse(World worldIn, PlayerEntity player, ItemStack wand, int timeLeft);

	protected boolean canUse(PlayerEntity playerIn, ItemStack wand) {
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(playerIn);

		if (storage.getEnergy() < energyneeded)
			return false;
		
		return true;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	public abstract int getUseDuration(ItemStack wand);
	
	public abstract int getEnergyUsedWhileCharging(ItemStack wand);

}