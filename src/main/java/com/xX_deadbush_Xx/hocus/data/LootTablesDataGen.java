package com.xX_deadbush_Xx.hocus.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTable.Builder;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;

public class LootTablesDataGen implements IDataProvider {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private final DataGenerator generator;
	private Map<Block, LootTable.Builder> tablemap = new HashMap<>();
	
	public LootTablesDataGen(DataGenerator generator) {
		this.generator = generator;
		
		//Init tablemap
		
	}

	@Override
	public void act(DirectoryCache cache) throws IOException {
    	System.out.println("Generating Block loot tables");

		ModBlocks.BLOCKS.getEntries().stream().map(r -> r.get()).forEach(b -> {
			LootTable.Builder table = tablemap.getOrDefault(b, selfDrop(b));
			tablemap.put(b, table);
		});
		
		for (Entry<Block, Builder> e : this.tablemap.entrySet()) {
			Path path = getPath(this.generator.getOutputFolder(), e.getKey());
	    	System.out.println(path);
			IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
		}
	}
	
	private Path getPath(Path root, Block block) {
		return root.resolve("data/" + Hocus.MOD_ID + "/loot_tables/blocks/" + block.getRegistryName().getPath() + ".json");
	}

	public LootTable.Builder selfDrop(Block b) {
		LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
		LootPool.Builder pool = LootPool.builder().name("selfdrop").rolls(ConstantRange.of(1)).addEntry(entry)
				.acceptCondition(SurvivesExplosion.builder());
		return LootTable.builder().addLootPool(pool);
	}
	
	@Override
	public String getName() {
		return "Hocus Block Loottables";
	}
}
