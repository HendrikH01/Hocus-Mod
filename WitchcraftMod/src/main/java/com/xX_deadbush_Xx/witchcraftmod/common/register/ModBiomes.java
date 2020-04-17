package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.world.biomes.AncientNetherPlateauBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
	public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, WitchcraftMod.MOD_ID);
	
	public static final RegistryObject<Biome> ANCIENT_NETHER_PLATEAU = BIOMES.register("ancient_nether_plateau",
			() -> new AncientNetherPlateauBiome());	
	
	public static void register() {
		registerBiome(ANCIENT_NETHER_PLATEAU.get(), Type.NETHER);
	}
	
	private static void registerBiome(Biome biome, Type... types) {
		System.out.println("registered: " + biome.getRegistryName());

		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addSpawnBiome(biome);
	}
}
