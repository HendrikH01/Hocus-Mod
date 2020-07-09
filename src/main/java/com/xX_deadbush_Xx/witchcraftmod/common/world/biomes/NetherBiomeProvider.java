package com.xX_deadbush_Xx.witchcraftmod.common.world.biomes;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.world.VoronoiGenerator;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProviderSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class NetherBiomeProvider extends BiomeProvider
{
    private static Set<Biome> biomes;
	private final VoronoiGenerator biomeNoise;
	private static final int biomeSize = 3;

    public NetherBiomeProvider(OverworldBiomeProviderSettings settingsProvider) {
        super(getNetherBiomes());
		this.biomeNoise = new VoronoiGenerator();
		this.biomeNoise.setSeed((int) settingsProvider.getSeed());
	}

	@Override
	public Biome getNoiseBiome(int x, int y, int z) {
		return getBiome(new LinkedList<Biome>(biomes),
				biomeNoise.getValue((double) x / biomeSize, (double) y / biomeSize, (double) z / biomeSize));
	}

	public Biome getBiome(List<Biome> biomeList, double noiseVal) {
		for (int i = biomeList.size(); i >= 0; i--) {
			if (noiseVal > (2.0f / biomeList.size()) * i - 1)
				return biomeList.get(i);
		}
		return biomeList.get(biomeList.size() - 1);
	}
    
    private static Set<Biome> getNetherBiomes() {
    	return ModBiomes.BIOMES.getEntries().stream()
    			.map(registryObj -> registryObj.get())
    			.filter(biome -> BiomeDictionary.hasType(biome, Type.NETHER))
    			.collect(Collectors.toSet());
    }
}