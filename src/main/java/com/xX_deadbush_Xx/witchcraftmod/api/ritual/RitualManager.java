package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.MiscUtil;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;

public class RitualManager {
	private Set<IRitual> rituals = new HashSet<>();
	private final static RitualManager INSTANCE = new RitualManager();
    private static Logger LOGGER = LogManager.getLogger();

	private RitualManager() {}
	
	public static RitualManager getInstance() {
		return INSTANCE;
	}

	public boolean tryActivateRitual(World worldIn, PlayerEntity player, AbstractRitualCore ritualStoneTile) {
		IRitual ritual = RitualActivationHandler.tryActivateRitual(ritualStoneTile, player);
		if (ritual == null)
			return false;
		
		LOGGER.debug(player.getName().getUnformattedComponentText() + " activated ritual: " + ritual + " at position " + MiscUtil.prettyPrint(ritualStoneTile.getPos()));
		
		rituals.add(ritual);
		ritualStoneTile.currentritual = LazyOptional.of(() -> ritual);
		ritualStoneTile.currentritual.addListener(this::onRitualStop);
		ritual.activate();
		return true;
	}
	
	private void onRitualStop(LazyOptional<IRitual> optional) {
		optional.ifPresent(ritual -> {
			rituals.remove(ritual);
			System.out.println("Ritual " + ritual + " has stopped!");
		});
	}
}