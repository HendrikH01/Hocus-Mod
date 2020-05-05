package com.xX_deadbush_Xx.witchcraftmod.api.util;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemStackHelper {
	
	public static boolean canMergeStacks(ItemStack stack1, ItemStack stack2) {
	    return !stack1.isEmpty() && stackEqualType(stack1, stack2) && stack1.isStackable() && stack1.getCount() < stack1.getMaxStackSize();
	}
	
	public static boolean stackEqualType(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem() == stack2.getItem() && ItemStack.areItemStackTagsEqual(stack1, stack2);
	}
	
	public static ItemStack mergeStacks(ItemStack stack1, ItemStack stack2) {
		if(canMergeStacks(stack1, stack2)) {
			if(stack1.getCount() + stack2.getCount() >= stack1.getMaxStackSize()) {
				setMaxStackCount(stack1);
				stack2.setCount(stack2.getCount() - stack1.getMaxStackSize() + stack1.getCount());
			}
			stack1.setCount(stack1.getCount() + stack2.getCount());
			stack2 = ItemStack.EMPTY;
		}
		return stack2;
	}
	
	public static void setMaxStackCount(ItemStack stack) {
		stack.setCount(stack.getMaxStackSize());
	}

	public static void spawnItem(World worldIn, ItemStack stack, BlockPos pos) {
        spawnItem(worldIn, stack, pos.getX(), pos.getY() + 0.5F, pos.getZ());
	}
	
	public static void spawnItem(World worldIn, ItemStack stack, float x, float y, float z) {
        ItemEntity itementity = new ItemEntity(worldIn, x, y, z, stack);
        worldIn.addEntity(itementity);
	}
}
