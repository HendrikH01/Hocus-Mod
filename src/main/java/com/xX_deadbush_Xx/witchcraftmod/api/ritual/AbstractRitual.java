package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualTask;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ListHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

public abstract class AbstractRitual implements IRitual {
	protected final PlayerEntity performingPlayer;	
	protected final AbstractRitualCore tile;
	protected final  World worldIn;
	protected RitualEffectHandler effecthandler;
	protected RitualActivationTaskHandler taskhandler;
	
	protected Set<BlockPos> chalkpositions = new HashSet<>();
	protected Set<BlockPos> nonRitualBlocks = new HashSet<>();
	protected Set<BlockPos> anchorBlocks = new HashSet<>();
	protected Set<TotemPattern> totems = new HashSet<>();
	
	protected Phase phase = Phase.ACTIVATION;
	protected int age = 0;
	
	public AbstractRitual(AbstractRitualCore tile, PlayerEntity player) {
		this.worldIn = tile.getWorld();
		this.tile = tile;
		this.performingPlayer = player;
 		if(this instanceof IStaticRitual) this.effecthandler = ((IStaticRitual)this).getEffectHandler();
	}
	
	protected void init() {};
	
	public void activate() {
		this.init();

		this.taskhandler = getActivationHandler();
		this.taskhandler.init();
	}
		
	public void tick() {
		this.age++;
		if(this.phase == Phase.POWERDOWN) {
			if(this.tile.glowpower <= 1) this.stopRitual(false);
			return;
		} else if(this.phase == Phase.ACTIVATION) {
			this.taskhandler.tick();
		} else { 
			if(this instanceof IStaticRitual) this.effecthandler.tick();
			if(this instanceof IContinuousRitual) {
				((IContinuousRitual)this).effectTick();
			}
		}
	}
	
	@Override
	public void enterEffectPhase() {
		this.phase = Phase.EFFECT;
	}
	
	@Override
	public boolean conditionsMet() {
		return multiblockComplete(this.getConfig());
	}
	
	@Override
	public boolean multiblockComplete(RitualConfig config) {
		for(BlockPos pos : this.nonRitualBlocks) {
			if(RitualHelper.stopsRitual(this.worldIn, pos)) {
				System.out.println("stopped ritual because of block at: " + pos);
				return false;
			}
		}
		
		if(!(this instanceof SmallRitual))
			if(!this.totemsInPlace(config)) return false;
		
		return chalkInPlace() && anchorBlocksInPlace(config);
	}
	
	private boolean totemsInPlace(RitualConfig config) {
		return config.matchesTotems(tile.getWorld(), new ArrayList<>(totems));
	}

	public boolean chalkInPlace() {
		for(BlockPos pos : this.chalkpositions) {
			if(!RitualHelper.isChalk(this.worldIn, pos)) {
				System.out.println("Chalk missing at: " + pos);
				return false;
			}
		}
		
		return true;
	}
	
	public boolean anchorBlocksInPlace(RitualConfig config) {
		List<Block> blocks = anchorBlocks.stream().map(this.worldIn::getBlockState).map(s -> s.getBlock()).collect(Collectors.toList());
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
			this.phase = Phase.POWERDOWN;
		} else {
			this.tile.currentritual.invalidate();
			this.resetChalk();
		}
	}
	
	@Override
	public PlayerEntity getPerformingPlayer() {
		return this.performingPlayer;
	}
	
	@Override
	public AbstractRitualCore getRitualStone() {
		return this.tile;
	}
	
	@Override
	public Set<BlockPos> getChalkPositions() {
		return this.chalkpositions;
	}
	
	@Override
	public Set<BlockPos> getAnchorBlocks() {
		return this.anchorBlocks;
	}
	
	@Override
	public Phase getPhase() {
		return this.phase;
	}
	
	@Override
	public <T extends Event> boolean handleActivationEvent(T event, Class<? extends RitualTask<T>> clazz) {
		this.taskhandler.handleEvent(event, clazz);
		return true;
	}
	
	public enum Phase {
		ACTIVATION,
		EFFECT,
		POWERDOWN
	}
}
