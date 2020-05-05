package com.xX_deadbush_Xx.witchcraftmod.client.effect.effects;

import java.util.Random;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.util.WitchcraftMathHelper;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class RitualItemCreateEffect implements IParticleEffect {
	Random rand = new Random();

	private World world;
	private double x;
	private double y;
	private double z;
	private float[] args;
	
	public RitualItemCreateEffect(World worldIn, double x, double y, double z, float... args) {
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
		this.args = args;
	}
	
	@Override
	public int getDuration() {
		return 1;
	}

	@Override
	public int getTicksLeft() {
		return 1;
	}

	@Override
	public void addParticles() {
		double scale = 0.2;
		for(int i = 0; i < rand.nextInt(5)+10; i++) {			
			Vec3d vec = WitchcraftMathHelper.pointOnSphere(1, rand);
			this.world.addParticle(ParticleTypes.POOF, x, y, z, vec.getX()*scale, vec.getY()*scale, vec.getZ()*scale);
		}
	}

	@Override
	public void tick() {
		addParticles();
		ClientParticleHandler.remove(this);
	}

}
