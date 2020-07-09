package com.xX_deadbush_Xx.witchcraftmod.data;

import java.io.File;
import java.io.FilenameFilter;

import org.apache.commons.io.FilenameUtils;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;

import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;

public class ItemModelsDataGen extends ItemModelProvider {
	
	public ItemModelsDataGen(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
	}
	
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
				
		Registry.ITEM.stream().filter(item -> WitchcraftMod.MOD_ID.equals(item.getRegistryName().getNamespace()))
		.forEach(item -> {
			String name = item.getRegistryName().getPath();

			if (item instanceof BlockItem && existingFileHelper.exists(item.getRegistryName(), ResourcePackType.CLIENT_RESOURCES, ".json", "models/block")) {
				if(ModBlocks.ADONIS.get().asItem().getRegistryName().getPath() == name || 
						ModBlocks.HELLSHROOM.get().asItem().getRegistryName().getPath() == name || 
						ModBlocks.BELLADONNA.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(WitchcraftMod.MOD_ID, "blocks/" + name));
				else withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name);
			} else if (!existingFileHelper.exists(new ResourceLocation(WitchcraftMod.MOD_ID, name), ResourcePackType.CLIENT_RESOURCES, ".json", "models/item")) {
				if(ModBlocks.DREADWOOD_LOG.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name + "0");
				else if(ModBlocks.HELLSHROOM_BLOCK.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name + "_inventory");
				else if(ModBlocks.HELLSHROOM_STEM.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, WitchcraftMod.MOD_ID + ":block/" + name + "_inventory");
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
