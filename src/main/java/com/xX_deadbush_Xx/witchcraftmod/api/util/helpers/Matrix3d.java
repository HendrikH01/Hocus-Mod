package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import net.minecraft.util.math.Vec3d;

public class Matrix3d {
	public double d00;
	public double d01;
	public double d02;
	public double d10;
	public double d11;
	public double d12;
	public double d20;
	public double d21;
	public double d22;

	public Matrix3d(Vec3d v1, Vec3d v2, Vec3d v3) {
		d00 = v1.x;
		d01 = v2.x;
		d02 = v3.x;
		d10 = v1.y;
		d11 = v2.y;
		d12 = v3.y;
		d20 = v1.z;
		d21 = v2.z;
		d22 = v3.z;
	}
	
	public Matrix3d(double d00, double d01, double d02, double d10, double d11, double d12, double d20, double d21, double d22) {
		this.d00 = d00;
		this.d01 = d01;
		this.d02 = d02;
		this.d10 = d10;
		this.d11 = d11;
		this.d12 = d12;
		this.d20 = d20;
		this.d21 = d21;
		this.d22 = d22;
	}
	
	public Matrix3d copy() {
		return new Matrix3d(d00, d01, d02, d10, d11, d12, d20, d21, d22);
	}
	
	public Vec3d multiply(Vec3d vec) {
		return new Vec3d(
				vec.x*d00 + vec.y*d01 + vec.z*d02, 
				vec.x*d10 + vec.y*d11 + vec.z*d12, 
				vec.x*d20 + vec.y*d21 + vec.z*d22);
	}
	
	public Matrix3d multiply(double d) {
		Matrix3d out = this.copy();
		out.d00 *= d;
		out.d01 *= d;
		out.d02 *= d;
		out.d10 *= d;
		out.d11 *= d;
		out.d12 *= d;
		out.d20 *= d;
		out.d21 *= d;
		out.d22 *= d;
		//System.out.println(out);
		return out;
	}
	
	public Matrix3d multiply(Matrix3d m) {
		Matrix3d out = this.copy();
		out.d00 = d00*m.d00 + d01*m.d10 + d02*m.d20;
		out.d01 = d00*m.d01 + d01*m.d11 + d02*m.d21;
		out.d02 = d00*m.d02 + d01*m.d12 + d02*m.d22;
		out.d10 = d10*m.d00 + d11*m.d10 + d12*m.d20;
		out.d11 = d10*m.d01 + d11*m.d11 + d12*m.d21;
		out.d12 = d10*m.d02 + d11*m.d12 + d12*m.d22;
		out.d20 = d20*m.d00 + d21*m.d10 + d22*m.d20;
		out.d21 = d20*m.d01 + d21*m.d11 + d22*m.d21;
		out.d22 = d20*m.d02 + d21*m.d12 + d22*m.d22;
		return out;
	}
	
	public Matrix3d add(Matrix3d m) {
		Matrix3d out = this.copy();
		out.d00 = d00 + m.d00;
		out.d01 = d01 + m.d01;
		out.d02 = d02 + m.d02;
		out.d10 = d10 + m.d10;
		out.d11 = d11 + m.d11;
		out.d12 = d12 + m.d12;
		out.d20 = d20 + m.d20;
		out.d21 = d21 + m.d21;
		out.d22 = d22 + m.d22;
		return out;
	}
	
	public static  Matrix3d getIdentityMatrix() {
		return new Matrix3d(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}
	
	@Override
	public String toString() {
		return "[" + d00 + ", " + d01 + ", "+ d02 + ", "+ d10 + ", "+ d11 + ", "+ d12 + ", "+ d20 + ", "+ d21 + ", "+ d22 + "]";
	}
}
