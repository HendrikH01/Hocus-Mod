package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.IRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.SmallRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ListHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper.RitualPositionHolder;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractRitual implements IRitual {
	protected final PlayerEntity performingPlayer;
	protected final AbstractRitualCore tile;
	protected final  World worldIn;
	protected Set<BlockPos> chalkpositions = new HashSet<>();
	protected Set<BlockPos> nonRitualBlocks = new HashSet<>();
	protected Set<BlockPos> junctionBlocks = new HashSet<>();
	protected RitualEffectHandler effecthandler;
	
	//Need to be saved to TE nbt
	protected int age = 0;
	protected boolean doespowerdownanimation = false;
	
	public AbstractRitual(AbstractRitualCore tile, PlayerEntity player) {
		this.worldIn = tile.getWorld();
		this.tile = tile;
		this.performingPlayer = player;
		
		RitualPositionHolder positions = getPositions(tile.getWorld(), tile.getPos());
		chalkpositions = positions.chalkpositions;
		nonRitualBlocks = positions.nonRitualBlocks;
		junctionBlocks = positions.junctionBlocks;
	}
	
	protected abstract RitualPositionHolder getPositions(World world, BlockPos pos);
	
	public void tick() {
		this.age++;
		if(this.doespowerdownanimation) {
			if(this.tile.glowpower <= 1) this.stopRitual(false);
			return;
		}
		
		if(this instanceof IStaticRitual) this.effecthandler.tick();
		if(this instanceof IContinuousRitual) ((IContinuousRitual)this).effectTick();
	}
	
	@Override
	public boolean conditionsMet() {
		return multiblockComplete((SmallRitualConfig) this.getConfig());
	}
	
	@Override
	public boolean multiblockComplete(IRitualConfig config) {
		for(BlockPos pos : this.nonRitualBlocks) {
			if(RitualHelper.stopsRitual(this.worldIn, pos)) {
				System.out.println("stopped ritual because of block at: " + pos);
				return false;
			}
		}
		return chalkInPlace() && junctionBlocksInPlace((SmallRitualConfig) config);
	}
	
	public boolean chalkInPlace() {
		for(BlockPos pos : this.chalkpositions) {
			if(!RitualHelper.isChalk(this.worldIn, pos)) return false;
		}
		return true;
	}
	
	public boolean junctionBlocksInPlace(SmallRitualConfig config) {
		List<Block> blocks = junctionBlocks.stream().map(this.worldIn::getBlockState).map(s -> s.getBlock()).collect(Collectors.toList());
		return config.matchesAnchorblocks(ListHelper.toNonNullList(blocks));
	}
	
	protected void resetChalk() {
		for(BlockPos pos : this.chalkpositions) {
			if(RitualHelper.isChalk(worldIn, pos)) worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ChalkBlock.POWER, 0).with(ChalkBlock.GLOW_TYPE, GlowType.WHITE));
		}
	}
	
	@Override
	public int getAge() {
		return this.age;
	}
	
	@Override
	public void stopRitual(boolean shouldDoChalkAnimation) {
 		if(shouldDoChalkAnimation) {
			this.doespowerdownanimation = true;
		} else {
			this.tile.currentritual.invalidate();
			this.resetChalk();
		}
	}
	
	@Override
	public PlayerEntity getPerformingPlayer() {
		return performingPlayer;
	}
	
	@Override
	public AbstractRitualCore getRitualStone() {
		return tile;
	}
	
	@Override
	public boolean isPoweringDown() {
		return doespowerdownanimation;
	}
	
	@Override
	public Set<BlockPos> getChalkPositions() {
		return chalkpositions;
	}
}
