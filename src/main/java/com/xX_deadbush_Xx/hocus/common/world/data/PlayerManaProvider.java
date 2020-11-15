package com.xX_deadbush_Xx.hocus.common.world.data;

import java.util.Optional;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerManaProvider implements ICapabilitySerializable<INBT> {
    private final LazyOptional<PlayerManaStorage> instance;

    public PlayerManaProvider() {
        this.instance = LazyOptional.of(() -> new PlayerManaStorage());
    }
    
    @OnlyIn(Dist.CLIENT)
	@SuppressWarnings("resource")
	public static LazyOptional<PlayerManaStorage> getClientCapability() {
        return Optional.of(Minecraft.getInstance().player).map(p -> p.getCapability(PlayerManaStorage.getCap())).orElse(null);
	}
	
	public static  PlayerManaStorage getPlayerCapability(PlayerEntity player) {
        return player.getCapability(PlayerManaStorage.getCap()).orElse(new PlayerManaStorage());
	}
	
	public static double getEnergy(PlayerEntity player) {
        return player.getCapability(PlayerManaStorage.getCap()).map(s -> s.getEnergy()).orElse(0.0);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return PlayerManaStorage.getCap().orEmpty(cap, this.instance);
	}

	@Override
	public INBT serializeNBT() {
		PlayerManaStorage storage = this.instance.orElse(new PlayerManaStorage());
		return PlayerManaStorage.getCap().writeNBT(storage, null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		PlayerManaStorage storage = this.instance.orElse(new PlayerManaStorage());
		PlayerManaStorage.getCap().readNBT(storage, null, nbt);
	}
}
