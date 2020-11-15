package com.xX_deadbush_Xx.hocus.common.event;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.hocus.common.register.ModBiomes;
import com.xX_deadbush_Xx.hocus.common.register.RitualRegistry;
import com.xX_deadbush_Xx.hocus.common.register.SpellRegistry;
import com.xX_deadbush_Xx.hocus.common.world.biomes.HocusBiome;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerManaStorage;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;
import com.xX_deadbush_Xx.hocus.common.world.data.TileEntityManaStorage;
import com.xX_deadbush_Xx.hocus.common.world.gen.OreGeneration;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class OnCommonSetup {

	@SuppressWarnings("deprecation")
	public static void commonSetup(FMLCommonSetupEvent event) {
		HocusPacketHandler.registerPackets();
		RitualRegistry.INSTANCE.registerRituals();
		SpellRegistry.INSTANCE.registerSpells();
		registerCapabilities();
		
		DeferredWorkQueue.runLater(() -> {
			ModBiomes.BIOMES.getEntries().forEach(biome -> {
				if(biome.get() instanceof HocusBiome) {
					((HocusBiome)biome.get()).init();
				}
			});
			
			OreGeneration.generateOre();
		});
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
				return SpellRegistry.serializeSpell(instance.getSpell());
			}

			@Override
			public void readNBT(Capability<PlayerSpellCapability> capability, PlayerSpellCapability instance, Direction side, INBT nbt) {
				if(nbt instanceof CompoundNBT) {
					try {
						instance.spell = SpellRegistry.deserializeSpell(nbt);
						if(instance.spell == null) {
							instance.stopSpell();
						}
					} 
					catch(Exception e) {
						Hocus.LOGGER.error("An exception occured while trying to load spell!");
						e.printStackTrace();
					}
				}
			}
		}, () -> new PlayerSpellCapability());
	}
}
