package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.blocks.CandleBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.CreativeManaSourceBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.CrystalRechargerBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.DreadwoodLog;
import com.xX_deadbush_Xx.hocus.common.blocks.DryingRackBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.EnergyRelayBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.FireBowlBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.IvyBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.ModMushroomBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.ModSaplingBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.MortarBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.MudBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.RitualPedestal;
import com.xX_deadbush_Xx.hocus.common.blocks.RitualStone;
import com.xX_deadbush_Xx.hocus.common.blocks.ShimmerBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.SimpleFourWayBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.Table;
import com.xX_deadbush_Xx.hocus.common.blocks.ToolTable;
import com.xX_deadbush_Xx.hocus.common.potion.BelladonnaPoisionEffect;
import com.xX_deadbush_Xx.hocus.common.world.gen.features.DreadwoodTree;

import net.minecraft.block.Block;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Effects;
import net.minecraft.potion.HealthBoostEffect;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
public class ModBlocks {
		private static final int HARVEST_LEVEL_WOOD = 0, HARVEST_LEVEL_STONE = 1, HARVEST_LEVEL_IRON = 2, HARVEST_LEVEL_DIAMOND = 3;

		public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Hocus.MOD_ID);
		
		//PLANTS
		public static final RegistryObject<Block> FUNKY_MUSHROOM = BLOCKS.register("hellshroom", () -> new ModMushroomBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));	 	
		public static final RegistryObject<Block> FUNKY_MUSHROOM_BLOCK = BLOCKS.register("hellshroom_block", () -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).notSolid().harvestTool(ToolType.AXE).harvestLevel(HARVEST_LEVEL_WOOD).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> FUNKY_MUSHROOM_STEM = BLOCKS.register("hellshroom_stem", () -> new HugeMushroomBlock(Block.Properties.create(Material.TALL_PLANTS, MaterialColor.DIRT).harvestTool(ToolType.AXE).harvestLevel(HARVEST_LEVEL_WOOD).hardnessAndResistance(0.2F, 2.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> BELLADONNA = BLOCKS.register("belladonna", () -> new FlowerBlock(new BelladonnaPoisionEffect(), 400, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.2F, 2.0F).sound(SoundType.PLANT)));
		public static final RegistryObject<Block> CAVE_FLOWER = BLOCKS.register("cave_flower", () -> new FlowerBlock(Effects.LUCK, 400, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.2F, 2.0F).sound(SoundType.PLANT)));
		public static final RegistryObject<Block> ADONIS = BLOCKS.register("adonis", () -> new FlowerBlock(new HealthBoostEffect(EffectType.BENEFICIAL, 2), 0, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().hardnessAndResistance(0.2F, 2.0F).sound(SoundType.PLANT)));
		public static final RegistryObject<Block> DREADWOOD_SAPLING = BLOCKS.register("dreadwood_sapling", () -> new ModSaplingBlock(DreadwoodTree::new, Block.Properties.create(Material.PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		public static final RegistryObject<Block> SWIRLY_PLANT = BLOCKS.register("swirly_plant", () -> new DoublePlantBlock(Block.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement().sound(SoundType.PLANT)));
		public static final RegistryObject<Block> POISON_IVY = BLOCKS.register("poison_ivy", () -> new IvyBlock(Block.Properties.create(Material.TALL_PLANTS).tickRandomly().hardnessAndResistance(0.2F).doesNotBlockMovement().sound(SoundType.PLANT)));

		//NORMAL BLOCKS
		public static final RegistryObject<Block> VIBRANT_CRYSTAL_ORE = BLOCKS.register("vibrant_crystal_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> ONYX_ORE = BLOCKS.register("onyx_ore", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));

		public static final RegistryObject<Block> VIBRANT_BLOCK = BLOCKS.register("vibrant_block", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> ONYX_CRYSTAL_BLOCK = BLOCKS.register("block_of_onyx", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_IRON).hardnessAndResistance(5.0F, 15.0F).sound(SoundType.STONE)));

		public static final RegistryObject<Block> RED_TOTEM = BLOCKS.register("red_totem", () -> new SimpleFourWayBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).hardnessAndResistance(3.0F, 12.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> GREEN_TOTEM = BLOCKS.register("green_totem", () -> new SimpleFourWayBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).hardnessAndResistance(3.0F, 12.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> PURPLE_TOTEM = BLOCKS.register("purple_totem", () -> new SimpleFourWayBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).hardnessAndResistance(3.0F, 12.0F).sound(SoundType.STONE)));

		public static final RegistryObject<Block> CANDLE = BLOCKS.register("candle", () -> new CandleBlock(Block.Properties.create(Material.ORGANIC).hardnessAndResistance(0.5F, 1.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> DREADWOOD_LOG = BLOCKS.register("dreadwood_log", () -> new DreadwoodLog(MaterialColor.GRASS, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DREADWOOD_LEAVES = BLOCKS.register("dreadwood_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.3F, 0.5F).notSolid().sound(SoundType.PLANT)));
		public static final RegistryObject<Block> CREATIVE_MANA_SOURCE = BLOCKS.register("creative_mana_source", () -> new CreativeManaSourceBlock(Block.Properties.create(Material.IRON).notSolid().harvestTool(ToolType.PICKAXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.METAL)));
		
		//DECORATION
		public static final RegistryObject<Block> MUD = BLOCKS.register("mud", () -> new Block(Block.Properties.create(Material.CLAY).harvestTool(ToolType.SHOVEL).hardnessAndResistance(0.6F).sound(SoundType.GROUND)));
		public static final RegistryObject<Block> CHISELED_SHALE = BLOCKS.register("chiseled_shale", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> MOSSY_SHALE_BRICKS = BLOCKS.register("mossy_shale_bricks", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> POLISHED_WOOD = BLOCKS.register("polished_wood", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> POLISHED_WOOD_SLAB = BLOCKS.register("polished_wood_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> POLISHED_WOOD_STAIRS = BLOCKS.register("polished_wood_stair", () -> new StairsBlock(POLISHED_WOOD.get()::getDefaultState, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> TABLE = BLOCKS.register("table", () -> new Table(Block.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));

		//FUNCTIONAL BLOCKS
		public static final RegistryObject<Block> TOOL_TABLE = BLOCKS.register("tool_table", () -> new ToolTable(Block.Properties.create(Material.WOOD).notSolid().harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DRYING_RACK = BLOCKS.register("drying_rack", () -> new DryingRackBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(1.5F, 5.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> RITUAL_STONE = BLOCKS.register("ritual_stone", () -> new RitualStone(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).notSolid().sound(SoundType.STONE)));
		public static final RegistryObject<Block> RITUAL_PEDESTAL = BLOCKS.register("ritual_pedestal", () -> new RitualPedestal(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).notSolid().sound(SoundType.STONE)));
		public static final RegistryObject<Block> STONE_MORTAR = BLOCKS.register("stone_mortar", () -> new MortarBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_WOOD).notSolid().sound(SoundType.STONE)));
		public static final RegistryObject<Block> FIRE_BOWL = BLOCKS.register("fire_bowl", () -> new FireBowlBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(HARVEST_LEVEL_STONE).notSolid().sound(SoundType.STONE)));
		public static final RegistryObject<Block> CRYSTAL_RECHARGER = BLOCKS.register("crystal_recharger", () -> new CrystalRechargerBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)));
		public static final RegistryObject<Block> ENERGY_RELAY = BLOCKS.register("energy_relay", () -> new EnergyRelayBlock(Block.Properties.create(Material.IRON).hardnessAndResistance(1.0F, 2.0F).sound(SoundType.METAL)));
		
		//OTHER
		public static final RegistryObject<Block> SHALE = BLOCKS.register("shale", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> SHALE_BRICKS = BLOCKS.register("shale_bricks", () -> new Block(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> SHALE_BRICKS_STAIRS = BLOCKS.register("shale_brick_stairs", () -> new StairsBlock(SHALE_BRICKS.get()::getDefaultState, Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> SHALE_BRICKS_SLAB = BLOCKS.register("shale_bricks_slab", () -> new SlabBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));
		public static final RegistryObject<Block> SHALE_BRICKS_WALL = BLOCKS.register("shale_bricks_wall", () -> new WallBlock(Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).hardnessAndResistance(2.0F, 10.0F).sound(SoundType.STONE)));

		public static final RegistryObject<Block> SHIMMER_BLOCK = BLOCKS.register("shimmer_block", () -> new ShimmerBlock(Block.Properties.create(Material.AIR).doesNotBlockMovement().hardnessAndResistance(0.0f).lightValue(13).notSolid().noDrops()));
		public static final RegistryObject<Block> CHALK_BLOCK = BLOCKS.register("chalk_block", () -> new ChalkBlock(Block.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.0F, 0.0F).sound(SoundType.STONE)));
		
		public static final RegistryObject<Block> DREADWOOD_PLANKS = BLOCKS.register("dreadwood_planks", () -> new Block(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DREADWOOD_STAIRS = BLOCKS.register("dreadwood_stairs", () -> new StairsBlock(DREADWOOD_PLANKS.get()::getDefaultState, Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
		public static final RegistryObject<Block> DREADWOOD_SLAB = BLOCKS.register("dreadwood_slab", () -> new SlabBlock(Block.Properties.create(Material.WOOD).harvestTool(ToolType.AXE).hardnessAndResistance(3.0F, 10.0F).sound(SoundType.WOOD)));
}
