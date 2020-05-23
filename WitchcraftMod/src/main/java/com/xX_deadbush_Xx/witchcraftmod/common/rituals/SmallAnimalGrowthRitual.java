package com.xX_deadbush_Xx.witchcraftmod.common.rituals;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.AbstractSmallRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IContinuousRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.SmallRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropsBlock;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class SmallAnimalGrowthRitual extends AbstractSmallRitual implements IContinuousRitual {
	public static final SmallRitualConfig config = new SmallRitualConfig(Blocks.RED_WOOL, Blocks.EMERALD_BLOCK, Blocks.RED_WOOL, Blocks.EMERALD_BLOCK);
	private final double checkradius = 8;
	private int manaconsumecooldown = 20;
	private static Random rand =  new Random();
	
	public SmallAnimalGrowthRitual(RitualStoneTile tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static SmallAnimalGrowthRitual create(RitualStoneTile tile, PlayerEntity player) {
		return new SmallAnimalGrowthRitual(tile, player);
	}
	
	@Override
	public void activate() {
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
	public IRitualConfig getConfig() {
		return this.config;
	}
	
	private void accelerateGrowth(BlockPos pos) {
		BlockState state = this.worldIn.getBlockState(pos);
		Block block = state.getBlock();
		if(block instanceof CropsBlock) {
			if(((CropsBlock)block).isMaxAge(state)) return;
			if(this.rand.nextInt(80)==0) block.tick(state, (ServerWorld) this.worldIn, pos, this.rand);
		}
	}
}
