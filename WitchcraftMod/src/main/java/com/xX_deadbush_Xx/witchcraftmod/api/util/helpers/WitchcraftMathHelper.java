package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.Random;

import net.minecraft.util.math.Vec3d;

// MATH YAY

public class WitchcraftMathHelper {
	public static Vec3d rotateVec3dX(Vec3d vec, double angle) {
		double dy = vec.y*Math.cos(angle) - vec.z*Math.sin(angle);
		double dz = vec.y*Math.sin(angle) + vec.z*Math.sin(angle);
		
		return new Vec3d(vec.x, vec.y + dy, vec.z + dz);
	}
	
	public static Vec3d rotateVec3dY(Vec3d vec, double angle) {
		double dx = vec.x*Math.cos(angle) + vec.z*Math.sin(angle);
		double dz = -vec.x*Math.sin(angle) + vec.z*Math.cos(angle);
		
		return new Vec3d(vec.x + dx, vec.y, vec.z + dz);
	}
	
	public static Vec3d rotateVec3dZ(Vec3d vec, double angle) {
		double dx = vec.x*Math.cos(angle) - vec.y*Math.sin(angle);
		double dy = vec.x*Math.sin(angle) + vec.y*Math.cos(angle);
		
		return new Vec3d(vec.x + dx, vec.y + dy, vec.z);
	}
	
	/**
	 * returns a vector with the length @param radius and a random direction
	 */
	public static Vec3d pointOnSphere(double radius, Random rand) {
		double x = rand.nextGaussian();
		double y = rand.nextGaussian();
		double z = rand.nextGaussian();
		double inversePythagoras =  x==0 && y==0 && z==0 ? 1 : 1/(Math.sqrt(x*x+y*y+z*z));
		x *= inversePythagoras*radius;
		y *= inversePythagoras*radius;
		z *= inversePythagoras*radius;
		return new Vec3d(x, y, z);
	}
}