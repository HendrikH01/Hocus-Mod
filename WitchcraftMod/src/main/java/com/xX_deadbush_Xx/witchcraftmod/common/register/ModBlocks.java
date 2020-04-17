package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.DryingRackBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModMushroomBlock;

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
		public static final RegistryObject<Block> HELLSHROOM_STEM = BLOCKS.register("hellshroom_stem", 
				() -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> VIBRANT_CRYSTAL_ORE = BLOCKS.register("vibrant_crystal_ore", 
				() -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> VIBRANT_BLOCK = BLOCKS.register("vibrant_block", 
				() -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> HARDENED_NETHERRACK = BLOCKS.register("hardened_netherrack", 
				() -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> DRYING_RACK = BLOCKS.register("drying_rack", 
				() -> new DryingRackBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F, 5.0F).sound(SoundType.WOOD)));
}
