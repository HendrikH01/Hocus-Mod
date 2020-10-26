package com.xX_deadbush_Xx.hocus.api.ritual;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.hocus.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.hocus.api.ritual.activation.RitualTask;
import com.xX_deadbush_Xx.hocus.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.hocus.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.hocus.api.util.ListHelper;
import com.xX_deadbush_Xx.hocus.api.util.RitualHelper;
import com.xX_deadbush_Xx.hocus.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.hocus.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.eventbus.api.Event;

public abstract class AbstractRitual implements IRitual {
	protected final PlayerEntity performingPlayer;	
	protected final RitualStoneTile tile;
	protected final  World world;
	protected RitualEffectHandler effecthandler;
	protected RitualActivationTaskHandler taskhandler;
	
	protected Set<BlockPos> chalkpositions = new HashSet<>();
	protected Set<BlockPos> nonRitualBlocks = new HashSet<>();
	protected Set<BlockPos> anchorBlocks = new HashSet<>();
	protected Set<TotemPattern> totems = new HashSet<>();
	
	protected Phase phase = Phase.ACTIVATION;
	protected int age = 0;
	
	public AbstractRitual(RitualStoneTile tile, PlayerEntity player) {
		this.world = tile.getWorld();
		this.tile = tile;
		this.performingPlayer = player;
 		if(this instanceof IStaticRitual) this.effecthandler = ((IStaticRitual)this).getEffectHandler();
	}
	
	protected void init() {};
	
	public void activate() {
		this.init();
		if(this.phase == Phase.STOPPED) return; //if ritual was stopped return
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
				if(RitualHelper.removeEnergy(((IContinuousRitual)this).manaPerSecond(), tile, performingPlayer)) {
					((IContinuousRitual)this).effectTick();
				} else stopRitual(true);
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
			if(RitualHelper.stopsRitual(this.world, pos)) {
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
			if(!RitualHelper.isChalk(this.world, pos)) {
				System.out.println("Chalk missing at: " + pos);
				return false;
			}
		}
		
		return true;
	}
	
	public boolean anchorBlocksInPlace(RitualConfig config) {
		List<Block> blocks = anchorBlocks.stream().map(this.world::getBlockState).map(s -> s.getBlock()).collect(Collectors.toList());
		return config.matchesAnchorblocks(ListHelper.toNonNullList(blocks));
	}
	
	protected void resetChalk() {
		for(BlockPos pos : this.chalkpositions) {
			if(RitualHelper.isChalk(world, pos)) world.setBlockState(pos, world.getBlockState(pos).with(ChalkBlock.POWER, 0).with(ChalkBlock.GLOW_TYPE, GlowType.WHITE));
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
			this.phase = Phase.STOPPED;
			this.tile.currentritual.invalidate();
			this.resetChalk();
		}
	}
	
	@Override
	public PlayerEntity getPerformingPlayer() {
		return this.performingPlayer;
	}
	
	@Override
	public RitualStoneTile getRitualStone() {
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
		POWERDOWN,
		STOPPED
	}
}
