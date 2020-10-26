package com.xX_deadbush_Xx.hocus.api.ritual;
import com.xX_deadbush_Xx.hocus.api.util.RitualHelper;
import com.xX_deadbush_Xx.hocus.api.util.RitualHelper.RitualPositionHolder;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public abstract class LargeRitual extends AbstractRitual {

	public LargeRitual(RitualStoneTile tile, PlayerEntity player) {
		super(tile, player);
		RitualPositionHolder positions = getRitualPositions(tile.getWorld(), tile.getPos());
		this.chalkpositions = positions.chalkpositions;
		this.anchorBlocks = positions.anchorblocks;
		this.totems = positions.totems;
		this.nonRitualBlocks = positions.nonRitualBlocks;
	}

	public static RitualPositionHolder getRitualPositions(IWorldReader worldIn, BlockPos pos) {
		RitualPositionHolder out = new RitualPositionHolder();

		for(Direction direction : RitualHelper.getHorizontalDirections()) {
			Direction right = direction.rotateY(); Direction back = direction.getOpposite();
			BlockPos pos1 = pos.offset(direction);
			BlockPos pos2 = pos.offset(direction, 2);
			BlockPos pos3 = pos.offset(direction, 3);
			BlockPos pos4 = pos.offset(direction, 4);
			BlockPos pos5 = pos.offset(direction, 5);
			BlockPos pos6 = pos.offset(direction, 6);
			BlockPos pos7 = pos.offset(direction, 7);
			BlockPos pos8 = pos.offset(direction, 8);
			BlockPos pos9 = pos.offset(direction, 9);
			BlockPos pos10 = pos5.offset(right, 5);

			out.chalkpositions.add(pos1);
			out.chalkpositions.add(pos2);
			out.chalkpositions.add(pos3);
			out.chalkpositions.add(pos4);
			out.chalkpositions.add(pos6);
			out.chalkpositions.add(pos7);
			out.chalkpositions.add(pos8);
			out.anchorblocks.add(pos2.offset(right, 2));
			out.anchorblocks.add(pos10);
			out.totems.add(new TotemPattern(pos5, pos5.up()));
			out.totems.add(new TotemPattern(pos10.offset(direction, 3), pos10.offset(direction, 3).up(), pos10.offset(direction, 3).up(2)));
			out.totems.add(new TotemPattern(pos10.offset(right, 3), pos10.offset(right, 3).up(), pos10.offset(right, 3).up(2)));

			out.chalkpositions.add(pos1.offset(right, 2));
			out.chalkpositions.add(pos2.offset(right));
			
			for(int i = 1; i < 5; i++) {
				out.chalkpositions.add(pos5.offset(right, i));
				out.chalkpositions.add(pos8.offset(right, i));
				out.chalkpositions.add(pos10.offset(back, i));
				
				out.nonRitualBlocks.add(pos3.offset(right, i));
				out.nonRitualBlocks.add(pos4.offset(right, i));
				out.nonRitualBlocks.add(pos6.offset(right, i));
				out.nonRitualBlocks.add(pos7.offset(right, i));
			}
			
			out.nonRitualBlocks.add(pos2.offset(right, 3));
			out.nonRitualBlocks.add(pos2.offset(right, 4));				
			out.nonRitualBlocks.add(pos1.offset(right, 3));
			out.nonRitualBlocks.add(pos1.offset(right, 4));
			for(int i = -5; i < 5; i++) {
				if(i != 0) out.nonRitualBlocks.add(pos6.offset(right, i));
				out.nonRitualBlocks.add(pos9.offset(right, i));
			}
		}
		return out;
	}
}
