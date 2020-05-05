package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.util.ListHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.RitualHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public interface IRitualSmall extends IRitual {
	public int TIER = 1;
	
	public default boolean chalkInPlace(BlockPos ritualStonePos, IWorldReader worldIn) {
		for(int i = 0; i < 4; i++) {
			Direction direction = Direction.byHorizontalIndex(i);
			BlockPos chalk1 = ritualStonePos.offset(direction);
			if(!RitualHelper.isChalk(worldIn, chalk1)) return false;
			if(!RitualHelper.isChalk(worldIn, chalk1.offset(direction).offset(direction.rotateYCCW()))) return false;
			BlockPos chalk2 = chalk1.offset(direction).offset(direction.rotateY());
			if(!RitualHelper.isChalk(worldIn, chalk2)) return false;
			if(!RitualHelper.isChalk(worldIn, chalk2.offset(direction.rotateY()))) return false;
		}
		return true;
	}
	
	public default List<BlockState> getJunctionBlocks(BlockPos ritualStonePos, IWorldReader worldIn) {
		List<BlockState> blocks = new ArrayList<>();
		for(int i = 0; i < 4; i++) {
			Direction direction = Direction.byHorizontalIndex(i);
			blocks.add(worldIn.getBlockState(ritualStonePos.offset(direction, 2)));
		}
		return blocks;
	}
	
	public default boolean junctionBlocksInPlace(SmallRitualConfig config, IWorldReader worldIn, BlockPos ritualStonePos) {
		List<BlockState> states = getJunctionBlocks(ritualStonePos, worldIn);
		List<Block> blocks = states.stream().map(s -> s.getBlock()).collect(Collectors.toList());
		return config.matches(ListHelper.toNonNullList(blocks));
	}

}
