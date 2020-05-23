package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.CandleBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.DryingRackBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModMushroomBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ModSaplingBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualPedestal;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualStone;
import com.xX_deadbush_Xx.witchcraftmod.common.potion.BelladonnaPoisionEffect;

import net.minecraft.block.Block;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.HealthBoostEffect;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
		private static final int HARVEST_LEVEL_WOOD = 0, HARVEST_LEVEL_STONE = 1, HARVEST_LEVEL_IRON = 2, HARVEST_LEVEL_DIAMOND = 3;

		public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, WitchcraftMod.MOD_ID);
		//PLANTS
		public static final RegistryObject<Block> HELLSHROOM = BLOCKS.register("hellshroom", () -> new ModMushroomBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));	 	
		public static final RegistryObject<Block> HELLSHROOM_BLOCK = BLOCKS.register("hellshroom_block", () -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).harvestTool(ToolType.AXE).harvestLevel(HARVEST_LEVEL_WOOD).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> HELLSHROOM_STEM = BLOCKS.register("hellshroom_stem", () -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).harvestTool(ToolType.AXE).harvestLevel(HARVEST_LEVEL_WOOD).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> BELLADONNA = BLOCKS.register("belladonna", () -> new FlowerBlock(new BelladonnaPoisionEffect(), 400, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.2F, 2.0F).sound(SoundType.PLANT)));
		public static final RegistryObject<Block> ADONIS = BLOCKS.register("adonis", () -> new FlowerBlock(new HealthBoostEffect(EffectType.BENEFICIAL, 2), 0, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.2F, 2.0F).sound(SoundType.PLANT)));
		//public static final RegistryObject<Block> DREADWOOD_SAPLING = BLOCKS.register("dreadwood_sapling", () -> new ModSaplingBlock(OakTree::new, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));

		//NORMAL BLOCKS
		public static final RegistryObject<Block> VIBRANT_CRYSTAL_ORE = BLOCKS.register("vibrant_crystal_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> VIBRANT_BLOCK = BLOCKS.register("vibrant_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> HARDENED_NETHERRACK = BLOCKS.register("hardened_netherrack", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> CANDLE = BLOCKS.register("candle", () -> new CandleBlock(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F, 1.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> DREADWOOD_PLANKS = BLOCKS.register("dreadwood_planks", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DREADWOOD_LOG = BLOCKS.register("dreadwood_planks", () -> new LogBlock(MaterialColor.GRASS, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DREADWOOD_LEAVES = BLOCKS.register("dreadwood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.3F, 0.5F).sound(SoundType.PLANT)));

		//FUNCTIONAL BLOCKS
		public static final RegistryObject<Block> DRYING_RACK = BLOCKS.register("drying_rack", () -> new DryingRackBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F, 5.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> RITUAL_STONE = BLOCKS.register("ritual_stone", () -> new RitualStone(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).notSolid().sound(SoundType.STONE)));
		public static final RegistryObject<Block> CHALK_BLOCK = BLOCKS.register("chalk_block", () -> new ChalkBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F, 0.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> RITUAL_PEDESTAL = BLOCKS.register("ritual_pedestal", () -> new RitualPedestal(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).notSolid().sound(SoundType.STONE)));

}
