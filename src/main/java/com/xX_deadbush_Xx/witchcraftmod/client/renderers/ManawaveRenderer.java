package com.xX_deadbush_Xx.witchcraftmod.client.renderers;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.api.util.ModMathHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ManawaveRenderer {
	public static final ResourceLocation TEXTURE_RL = new ResourceLocation(WitchcraftMod.MOD_ID, "textures/misc/blank.png");
	
	private ManawaveRenderer() {}
	
	@SuppressWarnings("resource")
	public static void render(IVertexBuilder iVertexBuilder, ActiveRenderInfo renderInfo, float partialTicks, Vec3d start, Vec3d end, int color, double thickness, int tick) {
		Minecraft.getInstance().textureManager.bindTexture(TEXTURE_RL);
		
		float alpha = (float) ((color & 0xFF000000) >> 24) / 255;
		float red = (float) ((color & 0xFF0000) >> 16) / 255;
		float green = (float) ((color & 0xFF00) >> 8) / 255;
		float blue = (float) (color & 0xFF) / 255;
		double distance = start.distanceTo(end);
		int segments = (int)Math.sqrt(distance)*15;
		int bumpcount = (int)segments/10 + 1;
		
		Vec3d difference = end.subtract(start);
	    Vec3d particleToCamPos = start.subtract(renderInfo.getProjectedView()).add(difference);
	    Vec3d cross = particleToCamPos.crossProduct(difference).normalize();
	    
	    Vec3d[] points1 = new Vec3d[bumpcount + 2];
	    points1[bumpcount+1] = new Vec3d(distance, 0, 0);
	    Vec3d[] points2 = points1.clone();
	    points1[0] = new Vec3d(0, thickness/3, 0);
	    points2[0] = new Vec3d(0, -thickness/3, 0);
	
	    Vec3d prev1 = start.add(cross.scale(thickness/3));
	    Vec3d prev2 = start.add(cross.scale(-thickness/3));
	    double intensity = (bumpcount == 2) ? 0.2 : 0.6;
		for(int j = 0; j < bumpcount; j++) {
			double pos = (double)(j+1)/(bumpcount+2)*distance;
			points1[j + 1] = new Vec3d(pos, Math.pow(-1, j)*MathHelper.sin((float) (((tick+partialTicks)*j/2)*Math.PI/40))*intensity + thickness, 0);
			points2[j + 1] = new Vec3d(pos, Math.pow(-1, j)*MathHelper.sin((float) (((tick+partialTicks)*j/2)*Math.PI/40))*intensity, 0);
		}
			
	    for(int i = 0; i < segments ; i++) {
	    	Vec3d pos = start.add(difference.normalize().scale((double)(i+1)/segments*distance));
	    	double offset1 = ModMathHelper.bezierCurve((double)(i+1)/segments, points1).y;
	    	double offset2 = ModMathHelper.bezierCurve((double)(i+1)/segments, points2).y;
	    	Vec3d pos1 = pos.add(cross.scale(offset1));
	    	Vec3d pos2 = pos.add(cross.scale(offset2));
	
	    	renderSegment(iVertexBuilder, renderInfo, partialTicks, pos1, pos2, prev2, prev1, red, green, blue, alpha);
	    	prev1 = pos1.scale(1);
	    	prev2 = pos2.scale(1);
	    }
	}

	private static void renderSegment(IVertexBuilder iVertexBuilder, ActiveRenderInfo renderInfo, float partialTicks, Vec3d pos1, Vec3d pos2, Vec3d pos3, Vec3d pos4, float red, float green, float blue, float alpha) {
		Vec3d camera = renderInfo.getProjectedView();
		double x = camera.x;
		double y = camera.y;
		double z = camera.z;
		
		iVertexBuilder.pos(pos1.x-x, pos1.y-y, pos1.z-z).color(red, green, blue, alpha).tex(16, 16).endVertex();
		iVertexBuilder.pos(pos2.x-x, pos2.y-y, pos2.z-z).color(red, green, blue, alpha).tex(16, 0).endVertex();
		iVertexBuilder.pos(pos3.x-x, pos3.y-y, pos3.z-z).color(red, green, blue, alpha).tex(0, 0).endVertex();
		iVertexBuilder.pos(pos4.x-x, pos4.y-y, pos4.z-z).color(red, green, blue, alpha).tex(0, 16).endVertex();
	}
}
