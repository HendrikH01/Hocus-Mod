package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper.RitualPositionHolder;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SmallRitual extends AbstractRitual {

	public SmallRitual(AbstractRitualCore tile, PlayerEntity player) {
		super(tile, player);
		RitualPositionHolder positions = getRitualPositions(tile.getWorld(), tile.getPos());
		this.chalkpositions = positions.chalkpositions;
		this.anchorBlocks = positions.anchorblocks;
		this.totems = positions.totems;
		this.nonRitualBlocks = positions.nonRitualBlocks;
	}

	public static RitualPositionHolder getRitualPositions(World world, BlockPos pos) {
		RitualPositionHolder out = new RitualPositionHolder();
		
		for(Direction direction : RitualHelper.getHorizontalDirections()) {
			Direction right = direction.rotateY();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos.offset(direction, 2);
			BlockPos pos3 = pos.offset(direction, 3);

			out.chalkpositions.add(pos2.offset(right));
			out.chalkpositions.add(pos1.offset(right, 2)); 
			out.chalkpositions.add(pos1); out.chalkpositions.add(pos2);

			out.nonRitualBlocks.add(pos1.offset(right));
			for(int i = -2; i < 2; i++) out.nonRitualBlocks.add(pos3.offset(right, i));
			
			out.anchorblocks.add(pos2.offset(right, 2));
		}
		return out;
	}
}
