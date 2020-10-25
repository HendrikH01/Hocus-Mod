package com.xX_deadbush_Xx.witchcraftmod.api.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemStackHelper {

	public static void spawnItem(World worldIn, ItemStack stack, BlockPos pos) {
        spawnItem(worldIn, stack, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
	}
	
	public static void spawnItem(World worldIn, ItemStack stack, float x, float y, float z) {
        ItemEntity itementity = new ItemEntity(worldIn, x, y, z, stack);
        worldIn.addEntity(itementity);
	}

	public static boolean isFuel(ItemStack stack) {
		return ForgeHooks.getBurnTime(stack) > 0 || isBucket(stack);
	}

	public static boolean isBucket(ItemStack stack) {
		return stack.getItem() == Items.BUCKET;
	}
}
