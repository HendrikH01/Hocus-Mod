package com.xX_deadbush_Xx.witchcraftmod.client;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
	public static final ItemGroup ITEMS = new ItemGroup(ItemGroup.GROUPS.length, WitchcraftMod.MOD_ID + "_items") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.WITCH_HAT.get());
		}
	};
	
	public static final ItemGroup BLOCKS = new ItemGroup(ItemGroup.GROUPS.length, WitchcraftMod.MOD_ID + "_blocks") {
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModBlocks.RITUAL_STONE.get());
		}
	};
}