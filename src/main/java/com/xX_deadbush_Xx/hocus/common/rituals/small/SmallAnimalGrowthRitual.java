package com.xX_deadbush_Xx.hocus.common.rituals.small;

import java.util.List;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.api.ritual.IContinuousRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.SmallRitual;
import com.xX_deadbush_Xx.hocus.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.hocus.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.hocus.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.hocus.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.hocus.common.network.HocusSParticlePacket;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;

import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class SmallAnimalGrowthRitual extends SmallRitual implements IContinuousRitual {
	public static final RitualConfig CONFIG = new RitualConfig(ConfigType.SMALL).addAnchorBlocks(2, Blocks.RED_WOOL, Blocks.EMERALD_BLOCK);
	private final double checkradius = 8;
	
	public SmallAnimalGrowthRitual(RitualStoneTile tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static SmallAnimalGrowthRitual create(RitualStoneTile tile, PlayerEntity player) {
		return new SmallAnimalGrowthRitual(tile, player);
	}
	
	@Override
	public void init() {
		if(!conditionsMet()) { 
			stopRitual(false);
		} else {
			BlockPos pos = this.tile.getPos();
			List<AnimalEntity> entities = this.world.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos.getX() - checkradius, pos.getY() - 2, pos.getZ() - checkradius, pos.getX() + checkradius, pos.getY() + 3, pos.getZ() + checkradius));
			entities.forEach(entity -> {
				if(entity.canBreed() || !entity.isChild()) {
					entity.setInLove(this.performingPlayer);
				}
			});
		}
	}

	@Override
	public GlowType getGlowType() {
		return GlowType.GREEN;
	}

	@Override
	public double manaPerSecond() {
		return 1;
	}

	@Override
	public void effectTick() {
		BlockPos pos = this.tile.getPos();
		List<AnimalEntity> entities = this.world.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos.getX() - checkradius, pos.getY() - 2, pos.getZ() - checkradius, pos.getX() + checkradius, pos.getY() + 3, pos.getZ() + checkradius));
		List<AnimalEntity> children = entities.stream().filter(entity -> entity.isChild()).collect(Collectors.toList());
		children.forEach(child -> {
			if(Math.random() > 0.1) child.addGrowth(1);
		});
		HocusPacketHandler.sendToNearby(this.world, pos, new HocusSParticlePacket(EffectType.GROWTH_RITUAL, pos.getX(), pos.getY(), pos.getZ(), 1));
	}

	@Override
	public RitualConfig getConfig() {
		return SmallAnimalGrowthRitual.CONFIG;
	}

	@Override
	public RitualActivationTaskHandler getActivationHandler() {
		return new RitualActivationTaskHandler(this.tile, this.performingPlayer) {

			@Override
			public void init() {}
			
		};
	}
}
