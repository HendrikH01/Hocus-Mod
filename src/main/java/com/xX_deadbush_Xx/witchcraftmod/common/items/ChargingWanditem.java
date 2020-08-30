package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaProvider;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class ChargingWanditem extends Item implements IWandItem {

	public ChargingWanditem(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onUse(World worldIn, LivingEntity entity, ItemStack wand, int count) {
		if(!(entity instanceof PlayerEntity)) return;
		PlayerEntity player = (PlayerEntity)entity;
			
		int energyneeded = getEnergyPerUse(wand);
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player).orElse(null);

		if (storage == null)
			return;
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
		
		int energyneeded = getEnergyPerUse(wand);
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player).orElse(null);

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
		int energyneeded = getEnergyPerUse(wand);
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(playerIn).orElse(null);

		if (storage == null)
			return false;
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