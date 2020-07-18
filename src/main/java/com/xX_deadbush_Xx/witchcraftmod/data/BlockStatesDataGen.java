package com.xX_deadbush_Xx.witchcraftmod.data;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.ModBlockStateProperties;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStatesDataGen extends BlockStateProvider {

	public BlockStatesDataGen(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		//simple blocks
		simpleBlock(ModBlocks.ADONIS.get(), models().getExistingFile(getModelPath(ModBlocks.ADONIS)));
		simpleBlock(ModBlocks.BELLADONNA.get(), models().getExistingFile(getModelPath(ModBlocks.BELLADONNA)));
		simpleBlock(ModBlocks.DREADWOOD_LEAVES.get(), models().getExistingFile(getModelPath(ModBlocks.DREADWOOD_LEAVES)));
		simpleBlock(ModBlocks.DREADWOOD_PLANKS.get(), models().getExistingFile(getModelPath(ModBlocks.DREADWOOD_PLANKS)));
		simpleBlock(ModBlocks.DREADWOOD_SAPLING.get(), models().getExistingFile(getModelPath(ModBlocks.DREADWOOD_SAPLING)));
		simpleBlock(ModBlocks.HARDENED_NETHERRACK.get(), models().getExistingFile(getModelPath(ModBlocks.HARDENED_NETHERRACK)));
		simpleBlock(ModBlocks.HELLSHROOM.get(), models().getExistingFile(getModelPath(ModBlocks.HELLSHROOM)));
		simpleBlock(ModBlocks.RITUAL_PEDESTAL.get(), models().getExistingFile(getModelPath(ModBlocks.RITUAL_PEDESTAL)));
		simpleBlock(ModBlocks.RITUAL_STONE.get(), models().getExistingFile(getModelPath(ModBlocks.RITUAL_STONE)));
		simpleBlock(ModBlocks.VIBRANT_BLOCK.get(), models().getExistingFile(getModelPath(ModBlocks.VIBRANT_BLOCK)));
		simpleBlock(ModBlocks.VIBRANT_CRYSTAL_ORE.get(), models().getExistingFile(getModelPath(ModBlocks.VIBRANT_CRYSTAL_ORE)));
		simpleBlock(ModBlocks.STONE_MORTAR.get(), models().getExistingFile(getModelPath(ModBlocks.STONE_MORTAR)));
		simpleBlock(ModBlocks.CRYSTAL_RECHARGER.get(), models().getExistingFile(getModelPath(ModBlocks.CRYSTAL_RECHARGER)));

		horizontalBlock(ModBlocks.TABLE.get(), models().getExistingFile(getModelPath(ModBlocks.TABLE)));
		horizontalBlock(ModBlocks.TOOL_TABLE.get(), models().getExistingFile(getModelPath(ModBlocks.TOOL_TABLE)));
		horizontalBlock(ModBlocks.DRYING_RACK.get(), models().getExistingFile(getModelPath(ModBlocks.DRYING_RACK)));
		
		getVariantBuilder(ModBlocks.CANDLE.get())
			.partialState().with(ModBlockStateProperties.CANDLES_1_3, 1).setModels(new ConfiguredModel(models().getExistingFile(getModelPath("one_" + getBlockName(ModBlocks.CANDLE)))))
			.partialState().with(ModBlockStateProperties.CANDLES_1_3, 2).setModels(new ConfiguredModel(models().getExistingFile(getModelPath("two_" + getBlockName(ModBlocks.CANDLE) + "s"))))
			.partialState().with(ModBlockStateProperties.CANDLES_1_3, 3).setModels(new ConfiguredModel(models().getExistingFile(getModelPath("three_" + getBlockName(ModBlocks.CANDLE) + "s"))));
		
		buildMushroomBlock(ModBlocks.HELLSHROOM_BLOCK);
		buildMushroomBlock(ModBlocks.HELLSHROOM_STEM);
	}
	
	private void buildMushroomBlock(RegistryObject<Block> block) {
		getMultipartBuilder(block.get())
			.part().modelFile(models().getExistingFile(getModelPath(block))).addModel().condition(BlockStateProperties.NORTH, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).addModel().condition(BlockStateProperties.NORTH, false).end()
			.part().modelFile(models().getExistingFile(getModelPath(block))).uvLock(true).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).uvLock(false).rotationY(180).addModel().condition(BlockStateProperties.SOUTH, false).end()
			.part().modelFile(models().getExistingFile(getModelPath(block))).uvLock(true).rotationY(90).addModel().condition(BlockStateProperties.EAST, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).uvLock(false).rotationY(90).addModel().condition(BlockStateProperties.EAST, false).end()
			.part().modelFile(models().getExistingFile(getModelPath(block))).uvLock(true).rotationY(270).addModel().condition(BlockStateProperties.WEST, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).uvLock(false).rotationY(270).addModel().condition(BlockStateProperties.WEST, false).end()
			.part().modelFile(models().getExistingFile(getModelPath(block))).uvLock(true).rotationX(270).addModel().condition(BlockStateProperties.UP, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).uvLock(false).rotationX(270).addModel().condition(BlockStateProperties.UP, false).end()
			.part().modelFile(models().getExistingFile(getModelPath(block))).uvLock(true).rotationX(90).addModel().condition(BlockStateProperties.DOWN, true).end()
			.part().modelFile(models().getExistingFile(getModelPath(getBlockName(block) + "_inside"))).uvLock(false).rotationX(90).addModel().condition(BlockStateProperties.DOWN, false).end();
	}
	
	private String getBlockName(RegistryObject<Block> block) {
		return block.get().getRegistryName().getPath();
	}
	
	private ResourceLocation getModelPath(String name) {
		return new ResourceLocation(WitchcraftMod.MOD_ID, "block/" + name);
	}
	
	private ResourceLocation getModelPath(RegistryObject<Block> block) {
		return getModelPath(getBlockName(block));
	}
	
	@Override
	public String getName() {
		return "Witchcraft Blockstates";
	}
}
