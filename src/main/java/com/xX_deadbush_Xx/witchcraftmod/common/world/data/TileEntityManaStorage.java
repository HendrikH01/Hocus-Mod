package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

//mostly taken from net.minecraftforge.energy.EnergyStorage
public class TileEntityManaStorage {

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
	
	public static net.minecraftforge.common.capabilities.Capability<TileEntityManaStorage> getCap() {
		return Capability.CRYSTAL_ENERGY_CAP;
	}
	
	public static class Capability {
		
	    @CapabilityInject(TileEntityManaStorage.class)
	    public static net.minecraftforge.common.capabilities.Capability<TileEntityManaStorage> CRYSTAL_ENERGY_CAP;

	    public static LazyOptional<TileEntityManaStorage> getWorldPressure(final World world) {
	        return world.getCapability(CRYSTAL_ENERGY_CAP, null);
	    }
	    
	    public static net.minecraftforge.common.capabilities.Capability<TileEntityManaStorage> get() {
	    	return CRYSTAL_ENERGY_CAP;
	    }

	}
}
