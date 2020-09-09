package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;

//mostly taken from net.minecraftforge.energy.EnergyStorage
public class CrystalLinkedManaStorage extends TileEntityManaStorage {

	private ItemStack crystal;

	public CrystalLinkedManaStorage(ItemStack crystal, int maxExtract, int maxReceive) {
		super(EnergyCrystal.getMaxEnergy(crystal), maxExtract, maxReceive, EnergyCrystal.getEnergyStored(crystal));
		if(!EnergyCrystal.isStackEnergyCrystal(crystal)) {
			throw new IllegalArgumentException("Stack " + crystal.toString() + " is not an Energy crystal!");
		} else {
			this.crystal = crystal;
		}
	}

	public int receiveEnergy(int maxReceive, boolean simulate) {
		int received = super.receiveEnergy(maxReceive, simulate);
		if(!simulate) {
			EnergyCrystal.setEnergyStored(this.crystal, this.energy);
		}
		
		return received;
	}

	public int extractEnergy(int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(maxExtract, simulate);
		if(!simulate) {
			EnergyCrystal.setEnergyStored(this.crystal, this.energy);
		}
		
		return extracted;
	}
}
