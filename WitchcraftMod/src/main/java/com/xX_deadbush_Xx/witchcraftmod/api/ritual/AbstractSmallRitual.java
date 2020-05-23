package com.xX_deadbush_Xx.witchcraftmod.api.ritual;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ListHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.RitualHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.RitualStone;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractSmallRitual implements IRitual {
	protected final PlayerEntity performingPlayer;
	protected final RitualStoneTile tile;
	protected final  World worldIn;
	protected List<BlockPos> chalkpositions = new ArrayList<>();
	protected List<BlockPos> nonRitualBlocks = new ArrayList<>();
	protected List<BlockPos> junctionBlocks = new ArrayList<>();
	protected RitualEffectHandler effecthandler;
	
	//Need to be saved to TE nbt
	protected int age = 0;
	protected int chalkCooldownTimer = 0;
	
	public AbstractSmallRitual(RitualStoneTile tile, PlayerEntity player) {
		this.worldIn = tile.getWorld();
		this.tile = tile;
		this.performingPlayer = player;
		
		Map<String, List<BlockPos>> positions = RitualHelper.getRitualPositionsSmall(tile.getWorld(), tile.getPos());
		chalkpositions = positions.get("chalk"); nonRitualBlocks = positions.get("nonritual"); junctionBlocks = positions.get("junctionblocks");
	}
	
	public void tick() {
		this.age++;
		if(this.chalkCooldownTimer > 0) {
			if(this.chalkCooldownTimer == 1) this.stopRitual(false);
			this.chalkCooldownTimer--;
			return;
		}
		int stonepower = worldIn.getBlockState(tile.getPos()).get(RitualStone.POWER);
		GlowType stonetype = worldIn.getBlockState(tile.getPos()).get(RitualStone.GLOW_TYPE);
		RitualHelper.colorChalk(stonetype, stonepower, this.chalkpositions, this.worldIn, this.tile.getPos());
		if(this instanceof IStaticRitual) this.effecthandler.tick();
		if(this instanceof IContinuousRitual) ((IContinuousRitual)this).effectTick();
	}
	
	@Override
	public boolean conditionsMet() {
		return multiblockComplete((SmallRitualConfig) this.getConfig());
	}
	
	@Override
	public boolean multiblockComplete(SmallRitualConfig config) {
		for(BlockPos pos : this.nonRitualBlocks) {
			if(RitualHelper.stopsRitual(this.worldIn, pos)) {
				System.out.println("stopped ritual because of block at: " + pos);
				return false;
			}
		}
		return chalkInPlace() && junctionBlocksInPlace(config);
	}
	
	public boolean chalkInPlace() {
		for(BlockPos pos : this.chalkpositions) {
			if(!RitualHelper.isChalk(this.worldIn, pos)) return false;
		}
		return true;
	}
	
	public boolean junctionBlocksInPlace(SmallRitualConfig config) {
		List<Block> blocks = junctionBlocks.stream().map(this.worldIn::getBlockState).map(s -> s.getBlock()).collect(Collectors.toList());
		return config.matches(ListHelper.toNonNullList(blocks));
	}
	
	protected void resetChalk() {
		for(BlockPos pos : this.chalkpositions) {
			if(RitualHelper.isChalk(worldIn, pos)) worldIn.setBlockState(pos, worldIn.getBlockState(pos).with(ChalkBlock.POWER, 0).with(ChalkBlock.GLOW_TYPE, GlowType.WHITE));
		}
	}
	
	public int getAge() {
		return this.age;
	}
	
	public void stopRitual(boolean shouldDoChalkAnimation) {
		if(shouldDoChalkAnimation) {
			this.chalkCooldownTimer = 30;
		} else {
			this.tile.currentritual = null;
			this.resetChalk();
		}
	}
}
