package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerManaProvider;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class WandItem extends Item {

	public WandItem(Properties properties) {
		super(properties);
	}
	
	public abstract int getEnergyPerUse();
	
	public abstract int getCooldown();

	/**
	 * Removes energy from player and sets the cooldown, returns true if successful
	 * @param player
	 * @param wand
	 * @return success
	 */
	protected boolean attemptWandUse(PlayerEntity player, ItemStack wand) {		
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player).orElse(null);

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