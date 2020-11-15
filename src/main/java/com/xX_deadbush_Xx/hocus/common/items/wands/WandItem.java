package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaProvider;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class WandItem<S extends SpellCast> extends Item {

	public WandItem(Properties properties) {
		super(properties);
	}
	
	public abstract int getEnergyPerUse();
	
	public abstract int getCooldown();

	/**
	 * Removes energy from player and adds stat, returns true if successful
	 * @param player
	 * @param wand
	 * @return success
	 */
	protected boolean attemptWandUse(PlayerEntity player, ItemStack wand) {		
		int energyneeded = getEnergyPerUse();
		PlayerManaStorage storage = PlayerManaProvider.getPlayerCapability(player);

		if (storage.getEnergy() > energyneeded) {
			storage.removeEnergy(energyneeded);
			player.getCooldownTracker().setCooldown(this, getCooldown());
					
			//TODO: add stat
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Override this method if the wand has a spell renderer. Otherwise particles should be used
	 * 
	 * @return SpellRenderer
	 */
	public S getSpell(PlayerEntity caster, ItemStack wand, int... args) {
		return null;
	}
}