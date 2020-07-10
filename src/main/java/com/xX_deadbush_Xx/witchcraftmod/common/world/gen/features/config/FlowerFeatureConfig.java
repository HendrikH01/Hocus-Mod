package com.xX_deadbush_Xx.witchcraftmod.common.world.gen.features.config;

import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class FlowerFeatureConfig implements IFeatureConfig {

    private int patchSize;
    private int patchCount;
    private int flowerDensity;

    public FlowerFeatureConfig(int patchCount, int flowerDensity, int patchSize) {
        this.patchCount = patchCount;
        this.flowerDensity = flowerDensity;
        this.patchSize = patchSize;
    }


    public int getPatchCount() {
        return patchCount;
    }

    public int getFlowerDensity() {
        return flowerDensity;
    }

    public int getPatchSize() {
        return patchSize;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> dynamicOps) {
        dynamicOps.createInt(patchCount);
        dynamicOps.createInt(flowerDensity);
        dynamicOps.set(null, "patchCount", dynamicOps.createInt(patchCount));
        dynamicOps.set(null, "flowerDensity", dynamicOps.createInt(flowerDensity));
        dynamicOps.set(null, "patchSize", dynamicOps.createInt(patchSize));
        return new Dynamic<>(dynamicOps);
    }

    public static FlowerFeatureConfig deserialize(Dynamic<?> dynamic) {
        return new FlowerFeatureConfig(dynamic.get("patchCount").asInt(2), dynamic.get("flowerDensity").asInt(2), dynamic.get("patchSize").asInt(3));
    }
}
