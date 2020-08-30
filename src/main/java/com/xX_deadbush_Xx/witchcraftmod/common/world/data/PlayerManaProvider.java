package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerManaProvider implements ICapabilitySerializable<IntArrayNBT> {
    private final LazyOptional<PlayerManaStorage> energy;

    public PlayerManaProvider() {
        this.energy = LazyOptional.of(() -> new PlayerManaStorage());
    }
    
	@SuppressWarnings("resource")
	public static LazyOptional<PlayerManaStorage> getClientCapability() {
        return Optional.of(Minecraft.getInstance().player).map(p -> p.getCapability(PlayerManaStorage.Capability.get())).orElse(null);
	}
	
	public static  LazyOptional<PlayerManaStorage> getPlayerCapability(PlayerEntity player) {
        return player.getCapability(PlayerManaStorage.Capability.get());
	}
	
	public static int getEnergy(PlayerEntity player) {
        return player.getCapability(PlayerManaStorage.Capability.get()).map(s -> s.getEnergy()).orElse(0);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return PlayerManaStorage.Capability.get().orEmpty(cap, this.energy);
	}

	@Override
	public IntArrayNBT serializeNBT() {
		return new IntArrayNBT(new int[] {this.energy.map(s -> s.getEnergy()).orElse(0), this.energy.map(s -> s.getMaxEnergy()).orElse(0)});
	}

	@Override
	public void deserializeNBT(IntArrayNBT nbt) {
		PlayerManaStorage storage = this.energy.orElse(new PlayerManaStorage());
		storage.setEnergy(nbt.get(0).getInt());
		storage.setMaxEnergy(nbt.get(1).getInt());
	}
}
