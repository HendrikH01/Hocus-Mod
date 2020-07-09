package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerCrystalEnergyProvider implements ICapabilityProvider {
    private final LazyOptional<CrystalEnergyStorage> energy;

    public PlayerCrystalEnergyProvider() {
        this.energy = LazyOptional.of(() -> new CrystalEnergyStorage());
    }
    
	@SuppressWarnings("resource")
	public static LazyOptional<CrystalEnergyStorage> getClientCapability() {
        return Optional.of(Minecraft.getInstance().player).map(p -> p.getCapability(CrystalEnergyStorage.Capability.get())).orElse(null);
	}
	
	public static  LazyOptional<CrystalEnergyStorage> getPlayerCapability(PlayerEntity player) {
        return player.getCapability(CrystalEnergyStorage.Capability.get());
	}
	
	public static int getEnergy(PlayerEntity player) {
        return player.getCapability(CrystalEnergyStorage.Capability.get()).map(s -> s.getEnergy()).orElse(0);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return CrystalEnergyStorage.Capability.get().orEmpty(cap, this.energy);
	}
}
