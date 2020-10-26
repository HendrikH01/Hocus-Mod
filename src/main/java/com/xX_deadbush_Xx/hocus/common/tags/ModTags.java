package com.xX_deadbush_Xx.hocus.common.tags;

import com.xX_deadbush_Xx.hocus.Hocus;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModTags {
	public static final Tag<Item> TOOL_TABLE_TOOL = makeItemWrapper("tool_table_tool");
	public static final Tag<Item> PLANT_OIL_INGREDIENT = makeItemWrapper("plant_oil_ingredient");

	public static final Tag<Block> RITUAL_BLOCK = makeBlockWrapper("ritual_block");
	
	private static Tag<Block> makeBlockWrapper(String name) {
		return new BlockTags.Wrapper(new ResourceLocation(Hocus.MOD_ID, name));
	}
	
	private static Tag<Item> makeItemWrapper(String name) {
		return new ItemTags.Wrapper(new ResourceLocation(Hocus.MOD_ID, name));
	}
}
