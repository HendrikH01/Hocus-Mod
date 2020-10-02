package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerManaStorage {
	
	private double energy = 0;
	private double maxEnergy = 0;
	private double consumedEnergy = 0;
	
	public void setEnergy(double amount) {
		energy = amount;
	}
	
	public void setMaxEnergy(double amount) {
		this.maxEnergy = amount;
	}
	
	public double getEnergy() {
		return energy;
	}
	
	public double getConsumedEnergy() {
		return consumedEnergy;
	}
	
	public double getMaxEnergy() {
		return maxEnergy;
	}
	
	public void removeEnergy(double amount) {
		if(amount > this.energy);
		this.consumedEnergy += amount;
	}
	
	public static class Capability {
		
	    @CapabilityInject(PlayerManaStorage.class)
	    public static net.minecraftforge.common.capabilities.Capability<PlayerManaStorage> CRYSTAL_ENERGY_CAP;

	    public static LazyOptional<PlayerManaStorage> getWorldPressure(final World world) {
	        return world.getCapability(CRYSTAL_ENERGY_CAP, null);
	    }
	}

	public void setConsumeZero() {
		consumedEnergy = 0;
	}

	public static net.minecraftforge.common.capabilities.Capability<PlayerManaStorage> getCap() {
		return Capability.CRYSTAL_ENERGY_CAP;
	}
}
