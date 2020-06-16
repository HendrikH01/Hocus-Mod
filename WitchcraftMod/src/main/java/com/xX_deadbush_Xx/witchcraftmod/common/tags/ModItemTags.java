package com.xX_deadbush_Xx.witchcraftmod.common.tags;

import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

public class ModItemTags {
	public static final Tag<Item> TOOL_TABLE_TOOL = makeWrapperTag("tool_table_tool");
	
	private static Tag<Item> makeWrapperTag(String name) {
		return new ItemTags.Wrapper(new ResourceLocation(name));
	}
}
