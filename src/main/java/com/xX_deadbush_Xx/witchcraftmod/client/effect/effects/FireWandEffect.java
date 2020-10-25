package com.xX_deadbush_Xx.witchcraftmod.client.effect.effects;

import java.util.Random;

import com.xX_deadbush_Xx.witchcraftmod.api.util.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;

import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireWandEffect implements IParticleEffect {
	Random rand = new Random();

	private World world;
	private double x;
	private double y;
	private double z;
	private Vec3d direction;
	
	public FireWandEffect(World worldIn, double x, double y, double z, float... args) {
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
		this.direction = new Vec3d(args[0], args[1], args[2]);
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
		for(int i = 0; i < rand.nextInt(15)+10; i++) {			
			Vec3d vec = ModMathHelper.getRandomOrthogonal(direction, rand).scale(scale);
			this.world.addParticle(ParticleTypes.POOF, x, y, z, vec.getX(), vec.getY(), vec.getZ());
		}
	}

	@Override
	public void tick() {
		addParticles();
		ClientParticleHandler.remove(this);
	}

}
