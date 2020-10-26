package com.xX_deadbush_Xx.hocus.common.world.data;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

//mostly taken from net.minecraftforge.energy.EnergyStorage
public class TileEntityManaStorage {
	
    @CapabilityInject(TileEntityManaStorage.class)
    public static net.minecraftforge.common.capabilities.Capability<TileEntityManaStorage> CRYSTAL_ENERGY_CAP;
    
	protected double energy;
	protected double maxenergy;
	protected double maxinput;
	protected double maxoutput;

	public TileEntityManaStorage(double capacity, double maxReceive, double maxExtract) {
		this(capacity, maxReceive, maxExtract, 0);
	}

	public TileEntityManaStorage(double capacity, double maxReceive, double maxExtract, double energy) {
		this.maxenergy = capacity;
		this.maxinput = maxReceive;
		this.maxoutput = maxExtract;
		this.energy = Math.max(0, Math.min(capacity, energy));
	}

	public double receiveEnergy(double maxReceive, boolean simulate) {
		if (!canReceive())
			return 0;

		double energyReceived = Math.min(maxenergy - energy, Math.min(this.maxinput, maxReceive));
		if (!simulate)
			energy += energyReceived;
		return energyReceived;
	}

	public double extractEnergy(double maxExtract, boolean simulate) {
		if (!canExtract())
			return 0;

		double energyExtracted = Math.min(energy, Math.min(this.maxoutput, maxExtract));
		if (!simulate)
			energy -= energyExtracted;
		return energyExtracted;
	}

	public double getEnergy() {
		return this.energy;
	}

	public double getMaxEnergy() {
		return this.maxenergy;
	}
	
	public boolean canExtract() {
		return this.maxoutput > 0;
	}

	public boolean canReceive() {
		return this.maxinput > 0 && this.energy < this.maxenergy;
	}
	
	public void setEnergy(double amount) {
		this.energy = Math.min(amount, maxenergy);
	}
	
	public static Capability<TileEntityManaStorage> getCap() {
		return CRYSTAL_ENERGY_CAP;
	}
}
