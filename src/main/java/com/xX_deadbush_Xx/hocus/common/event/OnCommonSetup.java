package com.xX_deadbush_Xx.hocus.common.event;

import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.hocus.common.register.RitualRegistry;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;
import com.xX_deadbush_Xx.hocus.common.world.data.TileEntityManaStorage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OnCommonSetup {

	public static void commonSetup(FMLCommonSetupEvent event) {
		HocusPacketHandler.registerPackets();
		RitualRegistry.INSTANCE.registerRituals();
		registerCapabilities();
	}

	private static void registerCapabilities() {
		//Player mana storage
		CapabilityManager.INSTANCE.register(PlayerManaStorage.class, new Capability.IStorage<PlayerManaStorage>() {

			@Override
			public INBT writeNBT(Capability<PlayerManaStorage> capability, PlayerManaStorage instance, Direction side) {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putDouble("energy", instance.getEnergy());
				nbt.putDouble("max", instance.getMaxEnergy());
				return nbt;
			}

			@Override
			public void readNBT(Capability<PlayerManaStorage> capability, PlayerManaStorage instance, Direction side, INBT nbt) {
				instance.setEnergy(((CompoundNBT) nbt).getDouble("energy"));
				instance.setMaxEnergy(((CompoundNBT) nbt).getDouble("max"));
			}

		}, PlayerManaStorage::new);

		//TE mana storage
		CapabilityManager.INSTANCE.register(TileEntityManaStorage.class, new Capability.IStorage<TileEntityManaStorage>() {

			@Override
			public INBT writeNBT(Capability<TileEntityManaStorage> capability, TileEntityManaStorage instance, Direction side) {
				return DoubleNBT.valueOf(instance.getEnergy());
			}

			@Override
			public void readNBT(Capability<TileEntityManaStorage> capability, TileEntityManaStorage instance, Direction side, INBT nbt) {
				instance.setEnergy(((DoubleNBT) nbt).getInt());
			}
		}, () -> new TileEntityManaStorage(1000, 20, 20));
		
		//player spells
		CapabilityManager.INSTANCE.register(PlayerSpellCapability.class, new Capability.IStorage<PlayerSpellCapability>() {

			@Override
			public INBT writeNBT(Capability<PlayerSpellCapability> capability, PlayerSpellCapability instance, Direction side) {
				return IntNBT.valueOf(instance.ticks);
			}

			@Override
			public void readNBT(Capability<PlayerSpellCapability> capability, PlayerSpellCapability instance, Direction side, INBT nbt) {
				instance.ticks = ((IntNBT) nbt).getInt();
			}
		}, () -> new PlayerSpellCapability());
	}
}
