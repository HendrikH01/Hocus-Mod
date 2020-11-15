package com.xX_deadbush_Xx.hocus.common.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;

public abstract class HocusBiome extends Biome {

	protected HocusBiome(Builder biomeBuilder) {
		super(biomeBuilder);
		
        DefaultBiomeFeatures.addCarvers(this);
	}
	
	public void init() {
		addEntities();
		addFeatures();
	}
	
	protected abstract void addEntities();
	
	protected abstract void addFeatures();
}
