package com.xX_deadbush_Xx.hocus.common.world.data;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.hocus.common.items.wands.WandItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerSpellProvider implements ICapabilitySerializable<INBT> {
    private final LazyOptional<PlayerSpellCapability> instance;
    public final PlayerEntity player;
    
    public PlayerSpellProvider(PlayerEntity playerEntity) {
        this.instance = LazyOptional.of(() -> new PlayerSpellCapability());
        this.player = playerEntity;
    }
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        return PlayerSpellCapability.getCap().orEmpty(cap, this.instance);
	}

	@Override
	public INBT serializeNBT() {
		PlayerSpellCapability storage = this.instance.orElse(new PlayerSpellCapability());
		return PlayerSpellCapability.getCap().writeNBT(storage, null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		PlayerSpellCapability storage = this.instance.orElse(new PlayerSpellCapability());

		if(player.getHeldItemMainhand().getItem() instanceof WandItem) {
			storage.setActiveWand(player, player.getHeldItemMainhand());
		}
		
		PlayerSpellCapability.getCap().readNBT(storage, null, nbt);
	}
}
