package com.xX_deadbush_Xx.witchcraftmod.data;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockModelsDataGen extends BlockModelProvider {

	public BlockModelsDataGen(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper) {
		super(generator, modid, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		registerCubeAll();
		registerPillarBlocks();
		
		leaves(ModBlocks.DREADWOOD_LEAVES);
		cross(getBlockName(ModBlocks.ADONIS), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.ADONIS)));
		cross(getBlockName(ModBlocks.BELLADONNA), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.BELLADONNA)));
		cross(getBlockName(ModBlocks.DREADWOOD_SAPLING), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.DREADWOOD_SAPLING)));
		cross(getBlockName(ModBlocks.HELLSHROOM), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.HELLSHROOM)));

		String hellshroomblockname = getBlockName(ModBlocks.HELLSHROOM_BLOCK);
		String hellshroomstemname = getBlockName(ModBlocks.HELLSHROOM_STEM);
		cubeFace(hellshroomblockname, getTextureRL(hellshroomblockname));
		cubeFace(hellshroomstemname, getTextureRL(hellshroomstemname));
		cubeFace(hellshroomblockname + "_inside", getTextureRL(hellshroomblockname + "_inside"));
		cubeFace(hellshroomstemname + "_inside", getTextureRL(hellshroomblockname + "_inside"));
	}
	
	private void registerCubeAll() {
		cubeAll(ModBlocks.VIBRANT_BLOCK);
		cubeAll(ModBlocks.VIBRANT_CRYSTAL_ORE);
		cubeAll(ModBlocks.HARDENED_NETHERRACK);
		cubeAll(ModBlocks.DREADWOOD_PLANKS);
		cubeAll(ModBlocks.POLISHED_WOOD);
		cubeAll(getBlockName(ModBlocks.HELLSHROOM_BLOCK) + "_inventory", getTextureRL(getBlockName(ModBlocks.HELLSHROOM_BLOCK)));
		cubeAll(getBlockName(ModBlocks.HELLSHROOM_STEM) + "_inventory", getTextureRL(getBlockName(ModBlocks.HELLSHROOM_STEM)));

	}
	
	private void cubeAll(RegistryObject<Block> r) {
		String name = getBlockName(r);
		cubeAll(name, getTextureRL(name));
	}
	
    public BlockModelBuilder leaves(RegistryObject<Block> r) {
		String name = getBlockName(r);
        return withExistingParent(name, BLOCK_FOLDER + "/leaves")
        		.texture("all", "blocks/" + name);
    }
    
    private void registerPillarBlocks() {
		String name = getBlockName(ModBlocks.DREADWOOD_LOG);
		cubeColumn(name, getTextureRL(name + "0"), getTextureRL(name + "_top"));
		for(int i = 0; i < 4; i++) cubeColumn(name + i, getTextureRL(name + i), getTextureRL(name + "_top"));
		
		pillarBlock(ModBlocks.SHALE, "_side", "_top");
    }
	
    private void cubeFace(String name, ResourceLocation texture) {
    	getBuilder(name).ao(false).texture("texture", texture).texture("particle", texture)
    	.element().from(0, 0, 0).to(16, 16, 0)
    	.face(Direction.NORTH).texture("#texture").cullface(Direction.NORTH).end().end();
    }
    
    private void pillarBlock(RegistryObject<Block> r, String suffixSide, String suffixTop) {
    	String name = getBlockName(r);
    	cubeColumn(name, getTextureRL(name + suffixSide), getTextureRL(name + suffixTop));
    }
    
	@Override
	public String getName() {
		return "Witchcraft Block Models";
	}
	
	private String getBlockName(RegistryObject<Block> r) {
		return r.get().getRegistryName().getPath();
	}
	
	private ResourceLocation getTextureRL(String name) {
		return new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + name);
	}
}
