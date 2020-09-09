package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper.RitualPositionHolder;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class SmallRitual extends AbstractRitual {

	public SmallRitual(AbstractRitualCore tile, PlayerEntity player) {
		super(tile, player);
	}

	@Override
	protected RitualPositionHolder getPositions(World world, BlockPos pos) {
		return RitualHelper.getRitualPositionsSmall(world, pos);
	}

}
