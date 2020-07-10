package com.xX_deadbush_Xx.witchcraftmod.common.world.biomes.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class EnchantedForestSurface extends SurfaceBuilder<SurfaceBuilderConfig> {


    public EnchantedForestSurface(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config) {
        super(config);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, config.getTop(), config.getUnder(), config.getUnderWaterMaterial(), seaLevel);
    }

    int p = 0;

    protected void buildSurface(Random random, IChunk chunk, Biome biome, int x, int z, int startHeight, double noise,
                                BlockState defaultBlock, BlockState defaultFluid, BlockState top, BlockState under, BlockState underWater, int seaLevel) {
        BlockState topBlock = top;
        BlockState underBlock = under;
        BlockPos.Mutable mut = new BlockPos.Mutable();
        int i = -1;
        int nh = (int)(noise / 3.0D + 3.0D + random.nextDouble() * 0.25D);
        int xCord = x & 15;
        int zCord = z & 15;

        for(int sh = startHeight; sh >= 0; --sh) {
            mut.setPos(xCord, sh, zCord);
            BlockState blockByMut = chunk.getBlockState(mut);
            if(p == 0) {
                System.out.println("enchanted_forest_position: " + xCord + " - " + sh + " - " + zCord);
                p = 1;
            }
            if (blockByMut.isAir()) {
                i = -1;
            } else if (blockByMut.getBlock() == defaultBlock.getBlock()) {
                if (i == -1) {
                    if (nh <= 0) {
                        topBlock = Blocks.AIR.getDefaultState();
                        underBlock = defaultBlock;
                    } else if (sh >= seaLevel - 4 && sh <= seaLevel + 1) {
                        topBlock = top;
                        underBlock = under;
                    }

                    if (sh < seaLevel && (topBlock == null || topBlock.isAir())) {
                        if (biome.getTemperature(mut.setPos(x, sh, z)) < 0.15F) {
                            topBlock = Blocks.ICE.getDefaultState();
                        } else {
                            topBlock = defaultFluid;
                        }

                        mut.setPos(xCord, sh, zCord);
                    }

                    i = nh;
                    if (sh >= seaLevel - 1) {
                        chunk.setBlockState(mut, topBlock, false);
                    } else if (sh < seaLevel - 7 - nh) {
                        topBlock = Blocks.AIR.getDefaultState();
                        underBlock = defaultFluid;
                        chunk.setBlockState(mut, underWater, false);
                    } else {
                        chunk.setBlockState(mut, underBlock, false);
                    }
                } else if (i > 0) {
                    --i;
                    chunk.setBlockState(mut, underBlock, false);
                    if (i == 0 && underBlock.getBlock() == Blocks.SAND && nh > 1) {
                        i = random.nextInt(4) + Math.max(0, sh - 63);
                        underBlock = underBlock.getBlock() == Blocks.RED_SAND ? Blocks.RED_SANDSTONE.getDefaultState() : Blocks.SANDSTONE.getDefaultState();
                    }
                }
            }
        }

    }
}
