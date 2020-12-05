package com.xX_deadbush_Xx.hocus.data;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;

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
		
		slabAndStairs(ModBlocks.SHALE_BRICKS, ModBlocks.SHALE_BRICKS_STAIRS, ModBlocks.SHALE_BRICKS_SLAB);
		slabAndStairs(ModBlocks.POLISHED_WOOD, ModBlocks.POLISHED_WOOD_STAIRS, ModBlocks.POLISHED_WOOD_SLAB);
		slabAndStairs(ModBlocks.DREADWOOD_PLANKS, ModBlocks.DREADWOOD_STAIRS, ModBlocks.DREADWOOD_SLAB);		
		
		ResourceLocation brickTex = getTextureRL(getBlockName(ModBlocks.SHALE_BRICKS));
		wallSide(getBlockName(ModBlocks.SHALE_BRICKS_WALL), brickTex);
		wallPost(getBlockName(ModBlocks.SHALE_BRICKS_WALL), brickTex);
		
		ResourceLocation woodTex = getTextureRL(getBlockName(ModBlocks.DREADWOOD_PLANKS));
		stairs(getBlockName(ModBlocks.DREADWOOD_STAIRS), woodTex, woodTex, woodTex);
		slab(getBlockName(ModBlocks.DREADWOOD_SLAB), woodTex, woodTex, woodTex);
		
		cross(getBlockName(ModBlocks.ADONIS), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.ADONIS)));
		cross(getBlockName(ModBlocks.BELLADONNA), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.BELLADONNA)));
		cross(getBlockName(ModBlocks.DREADWOOD_SAPLING), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.DREADWOOD_SAPLING)));
		cross(getBlockName(ModBlocks.FUNKY_MUSHROOM), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.FUNKY_MUSHROOM)));
		cross(getBlockName(ModBlocks.CAVE_FLOWER), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.CAVE_FLOWER)));
		cross(getBlockName(ModBlocks.FUNGAL_GRASS), new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.FUNGAL_GRASS)));

		cross(getBlockName(ModBlocks.SWIRLY_PLANT) + "_top", new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.SWIRLY_PLANT) + "_top"));
		cross(getBlockName(ModBlocks.SWIRLY_PLANT) + "_bottom", new ResourceLocation(Hocus.MOD_ID, "blocks/" + getBlockName(ModBlocks.SWIRLY_PLANT) + "_bottom"));

		String hellshroomblockname = getBlockName(ModBlocks.FUNKY_MUSHROOM_BLOCK);
		String hellshroomstemname = getBlockName(ModBlocks.FUNKY_MUSHROOM_STEM);
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
    	
    	getBuilder(getBlockName(ModBlocks.SHIMMER_BLOCK));
	}
	
	private void registerCubeAll() {
		cubeAll(ModBlocks.VIBRANT_BLOCK);
		cubeAll(ModBlocks.VIBRANT_CRYSTAL_ORE);
		cubeAll(ModBlocks.DREADWOOD_PLANKS);
		cubeAll(ModBlocks.POLISHED_WOOD);
		cubeAll(getBlockName(ModBlocks.FUNKY_MUSHROOM_BLOCK) + "_inventory", getTextureRL(getBlockName(ModBlocks.FUNKY_MUSHROOM_BLOCK)));
		cubeAll(getBlockName(ModBlocks.FUNKY_MUSHROOM_STEM) + "_inventory", getTextureRL(getBlockName(ModBlocks.FUNKY_MUSHROOM_STEM)));
		cubeAll(ModBlocks.CRYSTAL_RECHARGER);
		cubeAll(ModBlocks.CREATIVE_MANA_SOURCE);
		cubeAll(ModBlocks.ONYX_ORE);
		cubeAll(ModBlocks.SHALE_BRICKS);
		cubeAll(ModBlocks.CHISELED_SHALE);
		cubeAll(ModBlocks.ONYX_CRYSTAL_BLOCK);
		cubeAll(ModBlocks.MUD);
		cubeAll(ModBlocks.MOSSY_SHALE_BRICKS);

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
    
    private void slabAndStairs(RegistryObject<Block> base, RegistryObject<Block> stair, RegistryObject<Block> slab) {
		ResourceLocation brickTex = getTextureRL(getBlockName(base));
		stairs(getBlockName(stair), brickTex, brickTex, brickTex);
		slab(getBlockName(slab), brickTex, brickTex, brickTex);
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
		return "Hocus Block Models";
	}
	
	private String getBlockName(RegistryObject<Block> r) {
		return r.get().getRegistryName().getPath();
	}
	
	private ResourceLocation getTextureRL(String name) {
		return new ResourceLocation(Hocus.MOD_ID, "blocks/" + name);
	}
}
