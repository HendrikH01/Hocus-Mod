package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class WandItem extends Item {

	public WandItem(Properties properties) {
		super(properties);
	}
	
<<<<<<< master
	public abstract int getEnergyPerUse();
	
	public abstract int getCooldown();

	/**
	 * Removes energy from player and adds stat, returns true if successful
	 * @param player
	 * @param wand
	 * @return success
	 */
	protected boolean onWandUse(PlayerEntity player, ItemStack wand) {		
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player).orElse(null);
=======
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		ItemStack wand = playerIn.getHeldItem(handIn);
			
		int energyneeded = getEnergyPerUse(wand);
		CrystalEnergyStorage storage = PlayerCrystalEnergyProvider.getPlayerCapability(playerIn).orElse(null);
>>>>>>> 694e921 Nature Wand

		if (storage == null)
			return false;
		else if (storage.getEnergy() > energyneeded) {
			storage.removeEnergy(energyneeded);
			player.getCooldownTracker().setCooldown(this, getCooldown());
			
			//TODO: add stat
			return true;
		}
		
		return false;
	}
}