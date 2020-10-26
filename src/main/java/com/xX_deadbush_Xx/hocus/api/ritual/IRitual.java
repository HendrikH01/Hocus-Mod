package com.xX_deadbush_Xx.hocus.api.ritual;

import java.util.Set;

import com.xX_deadbush_Xx.hocus.api.ritual.AbstractRitual.Phase;
import com.xX_deadbush_Xx.hocus.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.hocus.api.ritual.activation.RitualTask;
import com.xX_deadbush_Xx.hocus.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.Event;

public interface IRitual {
	public boolean conditionsMet();
	
	/**
	 * Server only! Only called when conditionsMet() is true
	 */
	public void tick();

	public RitualConfig getConfig();

	public void stopRitual(boolean shouldDoChalkAnimation);

	public void activate();

	public GlowType getGlowType();

	public int getAge();

	boolean multiblockComplete(RitualConfig config);

	Set<BlockPos> getChalkPositions();
	
	RitualStoneTile getRitualStone();
	
	PlayerEntity getPerformingPlayer();
	
	Phase getPhase();
	
	RitualActivationTaskHandler getActivationHandler();

	<T extends Event> boolean handleActivationEvent(T event, Class<? extends RitualTask<T>> clazz);

	public void enterEffectPhase();

	Set<BlockPos> getAnchorBlocks();
}
