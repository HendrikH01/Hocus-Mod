package com.xX_deadbush_Xx.hocus.common.world.gen.features;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.config.FlowerFeatureConfig;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

public class FlowerFeature extends Feature<FlowerFeatureConfig> {

    public FlowerFeature(Function<Dynamic<?>, ? extends FlowerFeatureConfig> config) {
        super(config);
    }

    @Override
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, FlowerFeatureConfig config) {
        boolean any = false;
        for (int i = 0; i < config.getPatchCount(); i++) {
            int x = pos.getX() + rand.nextInt(16);
            int z = pos.getZ() + rand.nextInt(16);
            int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);

            BlockState flower = ModBlocks.ADONIS.get().getDefaultState();
            int dist = Math.min(8, Math.max(1, config.getPatchSize()));
            for (int j = 0; j < config.getFlowerDensity() * 16; j++) {
                int x1 = x + rand.nextInt(dist * 2) - dist;
                int y1 = y + rand.nextInt(4) - rand.nextInt(4);
                int z1 = z + rand.nextInt(dist * 2) - dist;
                BlockPos pos2 = new BlockPos(x1, y1, z1);
                if (world.isAirBlock(pos2) && (!world.getDimension().isNether() || y1 < 127) && flower.isValidPosition(world, pos2)) {
                    world.setBlockState(pos2, flower, 2);
                    any = true;
                }
            }
        }

        return any;
    }
}
