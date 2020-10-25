package com.xX_deadbush_Xx.witchcraftmod.api.util;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.Random;

import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ModMathHelper {
	public static Matrix3d getRodriguezRotationMatrix(Vec3d axis, double angle) {
		Vec3d normal = axis.normalize();
		Matrix3d transform = new Matrix3d(0, -normal.z, normal.y, normal.z, 0, -normal.x, -normal.y, normal.x, 0);
		Matrix3d identity = Matrix3d.getIdentityMatrix();
			
		Matrix3d rodriguesRotationMatrix = transform.multiply(transform).multiply(1 - cos(angle))
				.add(transform.multiply(sin(angle)))
				.add(identity);
		
		return rodriguesRotationMatrix;
	}
	
	public static Vec3d rotate(Matrix3d rotation, Vec3d vecIn, Vec3d offset) {
		return rotation.multiply(vecIn.subtract(offset)).add(offset);
	}
	
	public static Matrix3d getRotationMatrixY(double angle) {
		return new Matrix3d(
				cos(angle), 		  0, 		sin(angle),
						 0,           1, 		         0,
				-sin(angle), 		  0, 		cos(angle));
	}
	
	public static Matrix3d getRotationMatrixZ(double angle) {
		return new Matrix3d(
				cos(angle), -sin(angle), 		0,
				sin(angle),  cos(angle), 		0,
						 0, 		  0, 		1);
	}
	
	public static Vec3d rotateY(Vec3d vec, double angle) {
		Matrix3d r = getRotationMatrixY(angle);
		return r.multiply(vec);
	}
	
	public static float getLength(Vector3f vec) {
		return (float)Math.sqrt(vec.getX()*vec.getX() + vec.getY()*vec.getY() + vec.getZ()*vec.getZ());
	}
	
	public static double getPitch(Vec3d vec) {
		Vec3d normalized = vec.normalize();
		return Math.asin(-normalized.y);
	}
	
	public static double getYaw(Vec3d vec) {
		Vec3d normalized = vec.normalize();
		return 2*Math.PI - Math.atan2(normalized.x, normalized.z);
	}
	
	public static int getIntDigits(int i) {
		return (int)(Math.log10(i)+1);
	}
	
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

	public static Vec3d getRandomOrthogonal(Vec3d direction, Random rand) {
		double a = 0; double b = 0;
		while(a == 0 && b == 0) {
			a = rand.nextDouble()*2 - 1;
		 	b = rand.nextDouble()*2 - 1;
		}
		double c = (-a*direction.x-b*direction.y)/(direction.z == 0 ? 1 : direction.z);
		return new Vec3d(a, b, c).normalize();
	}
	
	public static int binomial(int n, int k) {
        if (k == 0 || k == n) return 1; 
        return binomial(n - 1, k - 1) + binomial(n - 1, k); 
    } 
	
	public static Vec3d bezierCurve(double t, Vec3d... points) {
		t = MathHelper.clamp(t, 0, 1);
		
		Vec3d out = new Vec3d(0, 0, 0);
		for(int i = 0; i < points.length; i++) {
			out = out.add(points[i].scale(
					binomial(points.length - 1, i)
					* Math.pow(t, i)
					* Math.pow(1 - t, points.length - i - 1)));
		}
		return out;
	}
	
	public static double getAngleBetweenVecs(Vec3d vec1, Vec3d vec2) {
		return Math.acos(vec1.dotProduct(vec2)/(vec1.length()*vec2.length()));
	}

	public static int sign(double x) {
		return x >= 0 ? 1 : -1;
	}
}
