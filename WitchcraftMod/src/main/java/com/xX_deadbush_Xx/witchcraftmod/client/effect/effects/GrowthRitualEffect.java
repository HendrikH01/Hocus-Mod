package com.xX_deadbush_Xx.witchcraftmod.client.effect.effects;

import java.util.Random;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualTier;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.World;

public class GrowthRitualEffect implements IParticleEffect {
	Random rand = new Random();

	private World world;
	private double x;
	private double y;
	private double z;
	private RitualTier tier;

	private int ticksLeft = 1;
	
	/**
	 * Adds composter and green potion particles for 20 ticks. Configure placement with argument: arg[0] must be a ritual tier size
	 */
	public GrowthRitualEffect(World worldIn, double x, double y, double z, float... args) {
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
		this.tier = RitualTier.getTier((int) args[0]);
	}
	
	@Override
	public int getDuration() {
		return 1;
	}

	@Override
	public int getTicksLeft() {
		return ticksLeft;
	}

	@Override
	public void addParticles() {
		switch(this.tier) {
		case SMALL: {
			for(int i = 0; i < rand.nextInt(3)+1; i++) {
				this.world.addParticle(ParticleTypes.COMPOSTER, x+rand.nextFloat()*4-1.5, y+rand.nextFloat()/2+0.2, z+rand.nextFloat()*4-1.5, 0.0F, rand.nextFloat()*0.15F, 0.0F);
				if(rand.nextInt(3) == 0) this.world.addParticle(ParticleTypes.ENTITY_EFFECT, x+rand.nextFloat()*4-1.5, y+rand.nextFloat()/2+0.2, z+rand.nextFloat()*4-1.5, 0.392F, 0.686F, 0.0F);

			}
		}
		}
	}

	@Override
	public void tick() {
		if(this.ticksLeft > 0) {
			addParticles();
			this.ticksLeft--;
		} else {
			ClientParticleHandler.remove(this);
		}
	}
}
