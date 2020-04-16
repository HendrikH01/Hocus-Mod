package com.xX_deadbush_Xx.witchcraftmod.common.blocks;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.block.Block;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
		
		public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, WitchcraftMod.MOD_ID);
		
		public static final RegistryObject<Block> HELLSHROOM = BLOCKS.register("hellshroom",
				() -> new ModMushroomBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));	 	
		public static final RegistryObject<Block> HELLSHROOM_BLOCK = BLOCKS.register("hellshroom_block", 
				() -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
}
