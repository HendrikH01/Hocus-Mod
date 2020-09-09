package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerManaStorage {
	
	private int energy = 0;
	private int maxEnergy = 0;
	private int consumedEnergy = 0;
	
	public void setEnergy(int amount) {
		energy = amount;
	}
	
	public void setMaxEnergy(int amount) {
		this.maxEnergy = amount;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public int getConsumedEnergy() {
		return consumedEnergy;
	}
	
	public int getMaxEnergy() {
		return maxEnergy;
	}
	
	public void removeEnergy(int amount) {
		this.consumedEnergy += amount;
	}
	
	public static class Capability {
		
	    @CapabilityInject(PlayerManaStorage.class)
	    public static net.minecraftforge.common.capabilities.Capability<PlayerManaStorage> CRYSTAL_ENERGY_CAP;

	    public static LazyOptional<PlayerManaStorage> getWorldPressure(final World world) {
	        return world.getCapability(CRYSTAL_ENERGY_CAP, null);
	    }
	    
	    public static net.minecraftforge.common.capabilities.Capability<PlayerManaStorage> get() {
	    	return CRYSTAL_ENERGY_CAP;
	    }

	}

	public void setConsumeZero() {
		consumedEnergy = 0;
	}
}
