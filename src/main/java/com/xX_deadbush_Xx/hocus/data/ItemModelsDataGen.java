package com.xX_deadbush_Xx.hocus.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class ItemModelsDataGen extends ItemModelProvider {
	private Set<Block> blockitemsWithGeneratedModel = new HashSet<>();
	
	public ItemModelsDataGen(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
		
		//Blockitems that should have generated item models go here
		blockitemsWithGeneratedModel.add(ModBlocks.HELLSHROOM.get());
		blockitemsWithGeneratedModel.add(ModBlocks.BELLADONNA.get());
		blockitemsWithGeneratedModel.add(ModBlocks.ADONIS.get());
		blockitemsWithGeneratedModel.add(ModBlocks.DREADWOOD_SAPLING.get());
		blockitemsWithGeneratedModel.add(ModBlocks.CAVE_FLOWER.get());
	}
	
	@Override
	protected void registerModels() {
		//Previously generated items need to be deleted in order for this to work
		File dir = generator.getOutputFolder().resolve("assets/" + Hocus.MOD_ID + "/models/item").toFile();
		
		for(File file : dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File parent, String name) { 
				if (name.endsWith(".json")) System.out.println("deleting: " + name);
				return name.endsWith(".json");
			}})) {
			file.delete();
		}
		
		//First generate models for all items that don't already have a model
		ModItems.ITEMS.getEntries().stream()
		.map(i -> i.get())
		.forEach(item -> {
			String name = item.getRegistryName().getPath();
			if (!existingFileHelper.exists(new ResourceLocation(Hocus.MOD_ID, name), ResourcePackType.CLIENT_RESOURCES, ".json", "models/item")) {
				 withExistingParent(name, "item/generated").texture("layer0", getTextureRL(name));
			}
		});
		
		//Then generate block models
		Set<String> names = blockitemsWithGeneratedModel.stream().map(b -> b.asItem().getRegistryName().getPath()).collect(Collectors.toSet());
		ModBlocks.BLOCKS.getEntries().stream()
		.map(b -> b.get().asItem())
		.filter(i -> i != Blocks.AIR.asItem())
		.forEach(item -> {
			String name = item.getRegistryName().getPath();
			
			//register special blockitem models here
			if(names.contains(name)) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "blocks/" + name));
			else if(ModBlocks.HELLSHROOM_BLOCK.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, Hocus.MOD_ID + ":block/" + name + "_inventory");
			else if(ModBlocks.HELLSHROOM_STEM.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, Hocus.MOD_ID + ":block/" + name + "_inventory");
			else if(ModBlocks.ENERGY_RELAY.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "items/" + name));
			else if(ModBlocks.FIRE_BOWL.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "items/" + name));
			else if(ModBlocks.CANDLE.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "items/" + name));
			else if(ModBlocks.POISON_IVY.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "items/" + name));
			else if(ModBlocks.SWIRLY_PLANT.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "blocks/" + name + "_top"));

			else if (existingFileHelper.exists(item.getRegistryName(), ResourcePackType.CLIENT_RESOURCES, ".json", "models/block")) {
				withExistingParent(name, Hocus.MOD_ID + ":block/" + name);
			} else Hocus.LOGGER.warn("No Blockmodel exists for {}, item model could not be generated!", name);
		});
	}
	
	private ResourceLocation getTextureRL(String name) {
		return new ResourceLocation(Hocus.MOD_ID, "items/" + name);
	}
	
	@Override
	public String getName() {
		return "Hocus Item Models";
	}
	
	public File getModel(String name) {
		return generator.getOutputFolder().resolve("assets/" + Hocus.MOD_ID + "/models/item/" + name).toFile();
	}
}
