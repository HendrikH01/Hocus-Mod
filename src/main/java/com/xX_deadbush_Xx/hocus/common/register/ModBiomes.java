package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.world.biomes.EnchantedForestBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
	
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, Hocus.MOD_ID);

    public static final RegistryObject<Biome> ENCHANTED_FOREST = BIOMES.register("enchanted_forest", EnchantedForestBiome::new);

    public static void register() {
        registerBiome(ENCHANTED_FOREST.get(), Type.OVERWORLD, Type.PLAINS);
    }

    private static void registerBiome(Biome biome, Type... types) {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biome, 30));
        BiomeManager.addSpawnBiome(biome);
    }
}
