package com.xX_deadbush_Xx.hocus.client;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
	public static final ItemGroup ITEMS = new ItemGroup(ItemGroup.GROUPS.length, Hocus.MOD_ID + "_items") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.WITCH_HAT.get());
		}
	};
	
	public static final ItemGroup BLOCKS = new ItemGroup(ItemGroup.GROUPS.length, Hocus.MOD_ID + "_blocks") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModBlocks.RITUAL_STONE.get());
		}
	};
}