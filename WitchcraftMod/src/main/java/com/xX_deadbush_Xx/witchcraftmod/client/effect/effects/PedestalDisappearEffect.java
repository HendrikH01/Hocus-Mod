package com.xX_deadbush_Xx.witchcraftmod.client.effect.effects;

import java.util.Random;

import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PedestalDisappearEffect implements IParticleEffect {
	Random rand = new Random();
	
	int ticksLeft;
	int diff = 4;
	private World world;
	private double x;
	private double y;
	private double z;
	private float[] args;
	
	public PedestalDisappearEffect(World worldIn, double x, double y, double z, float... args) {
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
		this.args = args;
		this.ticksLeft = getDuration();
	}
	
	@Override
	public int getDuration() {
		return 5;
	}

	@Override
	public int getTicksLeft() {
		return ticksLeft;
	}

	@Override
	public void addParticles() {
		double scale = 0.1;
		for(int i = 0; i < rand.nextInt(5)+10; i++) {			
			Vec3d vec = ModMathHelper.pointOnSphere(0.4, rand);
			Vec3d inv = vec.inverse();
			
			this.world.addParticle(ParticleTypes.FLAME, x + vec.getX(), y + vec.getY(), z + vec.getZ(), inv.getX()*scale, inv.getY()*scale, inv.getZ()*scale);
		}
	}

	@Override
	public void tick() {
		if(this.diff > 0) {
			this.diff--;
		} else {
			this.ticksLeft--;
			if(this.ticksLeft > 0) {
				addParticles();
			} else {
				ClientParticleHandler.remove(this);
			}
			this.diff = 4;
		}
	}
}
