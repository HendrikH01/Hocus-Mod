package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRitual {
	 public boolean multiblockComplete(BlockPos ritualStonePos, World worldIn);
	 
	 public boolean conditionsMet(BlockPos ritualStonePos, World worldIn, PlayerEntity player);
	 	 
	 public void activate(BlockPos ritualStonePos, World worldIn, PlayerEntity player);
}
