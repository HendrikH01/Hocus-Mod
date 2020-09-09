package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.Set;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.IRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public interface IRitual {
	public boolean conditionsMet();
	
	/**
	 * Server only! Only called when conditionsMet() is true
	 */
	public void tick();

	public IRitualConfig getConfig();

	public void stopRitual(boolean shouldDoChalkAnimation);

	public void activate();

	public GlowType getGlowType();

	public int getAge();

	boolean multiblockComplete(IRitualConfig config);

	boolean isPoweringDown();

	Set<BlockPos> getChalkPositions();
	
	AbstractRitualCore getRitualStone();
	
	PlayerEntity getPerformingPlayer();
}
