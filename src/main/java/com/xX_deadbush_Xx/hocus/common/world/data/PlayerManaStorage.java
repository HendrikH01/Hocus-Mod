package com.xX_deadbush_Xx.hocus.common.world.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class PlayerManaStorage {
	
	@CapabilityInject(PlayerManaStorage.class)
	public static Capability<PlayerManaStorage> CRYSTAL_ENERGY_CAP;
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

	public void setConsumeZero() {
		consumedEnergy = 0;
	}

	public static net.minecraftforge.common.capabilities.Capability<PlayerManaStorage> getCap() {
		return CRYSTAL_ENERGY_CAP;
	}
}
