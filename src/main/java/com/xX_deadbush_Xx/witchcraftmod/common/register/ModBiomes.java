package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.world.biomes.EnchantedForestBiome;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, WitchcraftMod.MOD_ID);

    public static final RegistryObject<Biome> ENCHANTED_FOREST = BIOMES.register("enchanted_forest", EnchantedForestBiome::new);

    public static void register() {
        registerBiome(ENCHANTED_FOREST.get(), Type.OVERWORLD, Type.PLAINS);
    }

    private static void registerBiome(Biome biome, Type... types) {

        BiomeManager.addBiome(BiomeManager.BiomeType.COOL, new BiomeManager.BiomeEntry(biome, 10)); // <---- weight=Chance das dass Biom spawnt
        BiomeDictionary.addTypes(biome, types);
        BiomeManager.addSpawnBiome(biome);
        System.out.println("registered: " + biome.getRegistryName());
    }
}
