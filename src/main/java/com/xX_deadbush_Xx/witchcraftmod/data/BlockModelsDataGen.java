package com.xX_deadbush_Xx.witchcraftmod.data;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.IvyBlock;
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
		
		totem(ModBlocks.RED_TOTEM);
		totem(ModBlocks.GREEN_TOTEM);
		totem(ModBlocks.PURPLE_TOTEM);
		leaves(ModBlocks.DREADWOOD_LEAVES);
		cross(getBlockName(ModBlocks.ADONIS), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.ADONIS)));
		cross(getBlockName(ModBlocks.BELLADONNA), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.BELLADONNA)));
		cross(getBlockName(ModBlocks.DREADWOOD_SAPLING), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.DREADWOOD_SAPLING)));
		cross(getBlockName(ModBlocks.HELLSHROOM), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.HELLSHROOM)));
		cross(getBlockName(ModBlocks.CAVE_FLOWER), new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.CAVE_FLOWER)));

		cross(getBlockName(ModBlocks.SWIRLY_PLANT) + "_top", new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.SWIRLY_PLANT) + "_top"));
		cross(getBlockName(ModBlocks.SWIRLY_PLANT) + "_bottom", new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + getBlockName(ModBlocks.SWIRLY_PLANT) + "_bottom"));

		String hellshroomblockname = getBlockName(ModBlocks.HELLSHROOM_BLOCK);
		String hellshroomstemname = getBlockName(ModBlocks.HELLSHROOM_STEM);
		cubeFace(hellshroomblockname, getTextureRL(hellshroomblockname));
		cubeFace(hellshroomstemname, getTextureRL(hellshroomstemname));
		cubeFace(hellshroomblockname + "_inside", getTextureRL(hellshroomblockname + "_inside"));
		cubeFace(hellshroomstemname + "_inside", getTextureRL(hellshroomblockname + "_inside"));
		
		String ivyname = getBlockName(ModBlocks.POISON_IVY);
		ResourceLocation ivytex = getTextureRL(ivyname);
    	getBuilder(ivyname + "_north").texture("texture", ivytex).texture("particle", ivytex).ao(false)
    		.element().shade(false).from(0, 0, 0.5f).to(16, 16, 0.5f)
    		.face(Direction.NORTH).texture("#texture").uvs(0, 0, 16, 16).end()
    		.face(Direction.SOUTH).texture("#texture").uvs(0, 0, 16, 16).end().end();
    	
    	getBuilder(ivyname + "_south").texture("texture", ivytex).texture("particle", ivytex).ao(false)
			.element().shade(false).from(16, 0, 15.5f).to(0, 16, 15.5f)
			.face(Direction.SOUTH).texture("#texture").uvs(0, 0, 16, 16).end()
			.face(Direction.NORTH).texture("#texture").uvs(0, 0, 16, 16).end().end();
    	
    	getBuilder(ivyname + "_east").texture("texture", ivytex).texture("particle", ivytex).ao(false)
			.element().shade(false).from(15.5f, 0, 0).to(15.5f, 16, 16)
			.face(Direction.EAST).texture("#texture").uvs(0, 0, 16, 16).end()
			.face(Direction.WEST).texture("#texture").uvs(0, 0, 16, 16).end().end();
    	
    	getBuilder(ivyname + "_west").texture("texture", ivytex).texture("particle", ivytex).ao(false)
			.element().shade(false).from(0.5f, 0, 16).to(0.5f, 16, 0)
			.face(Direction.WEST).texture("#texture").uvs(0, 0, 16, 16).end()
			.face(Direction.EAST).texture("#texture").uvs(0, 0, 16, 16).end().end();
    	
    	getBuilder(ivyname + "_up").texture("texture", ivytex).texture("particle", ivytex).ao(false)
			.element().shade(false).from(0, 15.0f, 0).to(16, 15.5f, 16)
			.face(Direction.UP).texture("#texture").uvs(0, 0, 16, 16).end()
			.face(Direction.DOWN).texture("#texture").uvs(0, 0, 16, 16).end().end();
   
    	getBuilder(ivyname + "_down").texture("texture", ivytex).texture("particle", ivytex).ao(false)
			.element().shade(false).from(0, 0.5f, 0).to(16, 0.5f, 16)
			.face(Direction.UP).texture("#texture").uvs(0, 0, 16, 16).end()
			.face(Direction.DOWN).texture("#texture").uvs(0, 0, 16, 16).end().end();
	}
	
	private void registerCubeAll() {
		cubeAll(ModBlocks.VIBRANT_BLOCK);
		cubeAll(ModBlocks.VIBRANT_CRYSTAL_ORE);
		cubeAll(ModBlocks.HARDENED_NETHERRACK);
		cubeAll(ModBlocks.DREADWOOD_PLANKS);
		cubeAll(ModBlocks.POLISHED_WOOD);
		cubeAll(getBlockName(ModBlocks.HELLSHROOM_BLOCK) + "_inventory", getTextureRL(getBlockName(ModBlocks.HELLSHROOM_BLOCK)));
		cubeAll(getBlockName(ModBlocks.HELLSHROOM_STEM) + "_inventory", getTextureRL(getBlockName(ModBlocks.HELLSHROOM_STEM)));
		cubeAll(ModBlocks.CRYSTAL_RECHARGER);
		cubeAll(ModBlocks.CREATIVE_MANA_SOURCE);
		cubeAll(ModBlocks.ONYX_ORE);
		cubeAll(ModBlocks.SHALE_BRICKS);
		cubeAll(ModBlocks.CHISELED_SHALE);

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
    

    private void totem(RegistryObject<Block> r) {
		String name = getBlockName(r);
		orientable(name, getTextureRL("totem_side"), getTextureRL(name), getTextureRL("totem_top"));
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
