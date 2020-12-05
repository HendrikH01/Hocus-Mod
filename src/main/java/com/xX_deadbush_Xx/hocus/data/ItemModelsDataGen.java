package com.xX_deadbush_Xx.hocus.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.fml.RegistryObject;

public class ItemModelsDataGen extends ItemModelProvider {
	private static final Set<Block> GENERATED_MODEL = new HashSet<>();
	private static final Map<Block, Function<String, ResourceLocation>> GENERATED_MODEL_SPECIAL = new HashMap<>();

	
	static {
		//Blockitems that should have generated item models go here
		GENERATED_MODEL.add(ModBlocks.FUNKY_MUSHROOM.get());
		GENERATED_MODEL.add(ModBlocks.DREADWOOD_SAPLING.get());
		GENERATED_MODEL.add(ModBlocks.CAVE_FLOWER.get());
		GENERATED_MODEL.add(ModBlocks.FUNGAL_GRASS.get());

		GENERATED_MODEL_SPECIAL.put(ModBlocks.ENERGY_RELAY.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.FIRE_BOWL.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.CANDLE.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.SWIRLY_PLANT.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "blocks/" + name + "_top"));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.POISON_IVY.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.BELLADONNA.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
		GENERATED_MODEL_SPECIAL.put(ModBlocks.ADONIS.get(), (name) -> new ResourceLocation(Hocus.MOD_ID, "items/" + name));
	}
	
	public ItemModelsDataGen(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
		super(generator, modId, existingFileHelper);
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
		Set<String> generated = GENERATED_MODEL.stream().map(b -> b.asItem().getRegistryName().getPath()).collect(Collectors.toSet());
		Set<String> generatedSpecial = GENERATED_MODEL_SPECIAL.keySet().stream().map(b -> b.asItem().getRegistryName().getPath()).collect(Collectors.toSet());

		for(RegistryObject<Block> block : ModBlocks.BLOCKS.getEntries()) {
			Item item = block.get().asItem();
			if(item == Blocks.AIR.asItem())
				continue;

			String name = item.getRegistryName().getPath();
			
			//register special blockitem models here
			if(generated.contains(name)) 
				withExistingParent(name, "item/generated").texture("layer0", new ResourceLocation(Hocus.MOD_ID, "blocks/" + name));
			
			if(generatedSpecial.contains(name)) 
				withExistingParent(name, "item/generated").texture("layer0", GENERATED_MODEL_SPECIAL.get(block.get()).apply(name));
			
			else if(ModBlocks.FUNKY_MUSHROOM_BLOCK.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, Hocus.MOD_ID + ":block/" + name + "_inventory");
			else if(ModBlocks.FUNKY_MUSHROOM_STEM.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, Hocus.MOD_ID + ":block/" + name + "_inventory");
			else if(ModBlocks.SHALE_BRICKS_WALL.get().asItem().getRegistryName().getPath() == name) withExistingParent(name, Hocus.MOD_ID + ":block/" + name + "_post");
			else if (existingFileHelper.exists(item.getRegistryName(), ResourcePackType.CLIENT_RESOURCES, ".json", "models/block")) {
				withExistingParent(name, Hocus.MOD_ID + ":block/" + name);
			} else Hocus.LOGGER.warn("No Blockmodel exists for {}, item model could not be generated!", name);
		}
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
