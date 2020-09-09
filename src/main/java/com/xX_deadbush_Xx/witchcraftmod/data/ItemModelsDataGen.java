package com.xX_deadbush_Xx.witchcraftmod.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class ItemModelsDataGen extends ItemModelProvider {
	private Set<Block> blockitemsWithGeneratedModel = new HashSet<>();
	
	public ItemModelsDataGen(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
		blockitemsWithGeneratedModel.add(ModBlocks.HELLSHROOM.get());
		blockitemsWithGeneratedModel.add(ModBlocks.BELLADONNA.get());
		blockitemsWithGeneratedModel.add(ModBlocks.ADONIS.get());
		blockitemsWithGeneratedModel.add(ModBlocks.DREADWOOD_SAPLING.get());
		blockitemsWithGeneratedModel.add(ModBlocks.CAVE_FLOWER.get());
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void registerModels() {
		File dir = generator.getOutputFolder().resolve("assets/" + WitchcraftMod.MOD_ID + "/models/item").toFile();
		
		for(File f : dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File parent, String name) { 
				if (name.endsWith(".json")) System.out.println("deleting: " + name);
				return name.endsWith(".json");
			}})) {
			f.delete();
		}
		
		Set<String> names = blockitemsWithGeneratedModel.stream().map(b -> b.asItem().getRegistryName().getPath()).collect(Collectors.toSet());
		
		Registry.ITEM.stream().filter(item -> WitchcraftMod.MOD_ID.equals(item.getRegistryName().getNamespace()))
		.forEach(item -> {
			String name = item.getRegistryName().getPath();

			if (item instanceof BlockItem && existingFileHelper.exists(item.getRegistryName(), ResourcePackType.CLIENT_RESOURCES, ".json", "models/block")) {
				if(names.contains(name)) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + name));
				else if(ModBlocks.HELLSHROOM_BLOCK.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name + "_inventory");
				else if(ModBlocks.HELLSHROOM_STEM.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name + "_inventory");
				else if(ModBlocks.ENERGY_RELAY.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(WitchcraftMod.MOD_ID, "items/" + name));
				else withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name);
			} else if (!existingFileHelper.exists(new ResourceLocation(WitchcraftMod.MOD_ID, name), ResourcePackType.CLIENT_RESOURCES, ".json", "models/item")) {
				 if(ModBlocks.SWIRLY_PLANT.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + name + "_top"));
				 else withExistingParent(name, "item/generated").texture("layer0", getTextureRL(name));
			}
		});
	}
	
	private ResourceLocation getTextureRL(String name) {
		return new ResourceLocation(WitchcraftMod.MOD_ID, "items/" + name);
	}
	
	@Override
	public String getName() {
		return "Witchcraft Item Models";
	}
	
	public File getModel(String name) {
		return generator.getOutputFolder().resolve("assets/" + WitchcraftMod.MOD_ID + "/models/item/" + name).toFile();
	}
}
