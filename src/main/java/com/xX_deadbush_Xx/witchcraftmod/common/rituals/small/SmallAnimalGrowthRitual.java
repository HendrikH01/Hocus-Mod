package com.xX_deadbush_Xx.witchcraftmod.common.rituals.small;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IContinuousRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.SmallRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Blocks;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class SmallAnimalGrowthRitual extends SmallRitual implements IContinuousRitual {
	public static final RitualConfig config = new RitualConfig(ConfigType.SMALL).addAnchorBlocks(2, Blocks.RED_WOOL, Blocks.EMERALD_BLOCK);
	private final double checkradius = 8;
	private int manaconsumecooldown = 20;
	private static Random rand =  new Random();
	
	public SmallAnimalGrowthRitual(AbstractRitualCore tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static SmallAnimalGrowthRitual create(AbstractRitualCore tile, PlayerEntity player) {
		return new SmallAnimalGrowthRitual(tile, player);
	}
	
	@Override
	public void init() {
		if(!conditionsMet()) { 
			stopRitual(false);
		} else {
			BlockPos pos = this.tile.getPos();
			List<AnimalEntity> entities = this.worldIn.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos.getX() - checkradius, pos.getY() - 2, pos.getZ() - checkradius, pos.getX() + checkradius, pos.getY() + 3, pos.getZ() + checkradius));
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
	public int manaPerSecond() {
		return 10;
	}

	@Override
	public void effectTick() {
		BlockPos pos = this.tile.getPos();
		List<AnimalEntity> entities = this.worldIn.getEntitiesWithinAABB(AnimalEntity.class, new AxisAlignedBB(pos.getX() - checkradius, pos.getY() - 2, pos.getZ() - checkradius, pos.getX() + checkradius, pos.getY() + 3, pos.getZ() + checkradius));
		List<AnimalEntity> children = entities.stream().filter(entity -> entity.isChild()).collect(Collectors.toList());
		children.forEach(child -> {
			if(Math.random() > 0.1) child.addGrowth(1);
		});
		WitchcraftPacketHandler.sendToNearby(this.worldIn, pos, new WitchcraftParticlePacket(EffectType.GROWTH_RITUAL, pos.getX(), pos.getY(), pos.getZ(), 1));
	}

	@Override
	public RitualConfig getConfig() {
		return SmallAnimalGrowthRitual.config;
	}

	@Override
	public RitualActivationTaskHandler getActivationHandler() {
		return new RitualActivationTaskHandler(this.tile, this.performingPlayer) {

			@Override
			public void init() {}
			
		};
	}
}
