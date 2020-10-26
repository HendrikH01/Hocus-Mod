package com.xX_deadbush_Xx.hocus.common.world.biomes.surface;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilders.ISurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

public class ModSurfaces {

    public static final SurfaceBuilder<SurfaceBuilderConfig> ENCHANTED_FOREST = register("enchanted_forest", new EnchantedForestSurface(SurfaceBuilderConfig::deserialize));

    private static <C extends ISurfaceBuilderConfig, F extends SurfaceBuilder<C>> F register(String key, F builder) {
        return Registry.register(Registry.SURFACE_BUILDER, key, builder);
    }
}
