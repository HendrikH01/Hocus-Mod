package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.entity.player.PlayerEntity;

public interface IRitual {	 
	 public boolean conditionsMet();
	 
	 /**
	  * Only call when conditionsMet is true
	  */
	 public void tick();
	 
	 public IRitualConfig getConfig();
	 	 
	 public void stopRitual(boolean shouldDoChalkAnimation);

	 public void activate();
	 	 
	 public GlowType getGlowType();

	 public boolean multiblockComplete(SmallRitualConfig config);
	 
	 public int getAge();
}
