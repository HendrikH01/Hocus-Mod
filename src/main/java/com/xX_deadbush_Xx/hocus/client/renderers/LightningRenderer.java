package com.xX_deadbush_Xx.hocus.client.renderers;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.api.util.render.LightningTree;
import com.xX_deadbush_Xx.hocus.api.util.render.LightningTree.LightningSegment;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class LightningRenderer {

public static final ResourceLocation TEXTURE_RL = new ResourceLocation(Hocus.MOD_ID, "textures/misc/lightning.png");
	
	private LightningRenderer() {}
	
	public static void render(IVertexBuilder builder, ActiveRenderInfo renderInfo, float partialTicks, Vec3d offset, LightningTree tree, int color, float width) {
		Vec3d campos = renderInfo.getProjectedView();
		float alpha = (float) ((color & 0xFF000000) >> 24) / 255;
		float red = (float) ((color & 0xFF0000) >> 16) / 255;
		float green = (float) ((color & 0xFF00) >> 8) / 255;
		float blue = (float) (color & 0xFF) / 255;
		
	    renderTreeRecursively(builder, tree.getRoot(), campos, offset, red, green, blue, alpha, width);
	   
	}
	
	private static void renderTreeRecursively(IVertexBuilder builder, LightningSegment root, Vec3d camerapos, Vec3d offset, float red, float green, float blue, float alpha, float width) {
    	renderLightningSegment(builder, camerapos, root.start.add(offset), root.end.add(offset), red, green, blue, alpha, width);
    	root.children.forEach(segment -> {
    		renderTreeRecursively(builder, segment, camerapos, offset, red, green, blue, alpha, width);
    	}) ;
	}

	private static void renderLightningSegment(IVertexBuilder builder, Vec3d camerapos, Vec3d start, Vec3d end, float red, float green, float blue, float alpha, float width) {
	    Vec3d difference = start.subtract(end);
	    Vec3d particleToCamPos = start.subtract(camerapos).add(difference);
	    Vec3d cross = particleToCamPos.crossProduct(difference).normalize();

		Vector3f[] positions = new Vector3f[] {
				new Vector3f(cross.inverse()), 
				new Vector3f(cross), 
				new Vector3f(cross), 
				new Vector3f(cross.inverse())};
	    
	    for (int i = 0; i < 4; ++i) {
	    	positions[i].mul(width, width, width);
	    	if(i > 1) {
				positions[i].add(new Vector3f(start));
			} else {
				positions[i].add(new Vector3f(end));
			}
	    	
	    	positions[i].sub(new Vector3f(camerapos));
		}

	    builder.pos((double)positions[0].getX(), (double)positions[0].getY(), (double)positions[0].getZ()).color(red, green, blue, alpha).tex(1, 1).endVertex();
	    builder.pos((double)positions[1].getX(), (double)positions[1].getY(), (double)positions[1].getZ()).color(red, green, blue, alpha).tex(1, 0).endVertex();
	    builder.pos((double)positions[2].getX(), (double)positions[2].getY(), (double)positions[2].getZ()).color(red, green, blue, alpha).tex(0, 0).endVertex();
	    builder.pos((double)positions[3].getX(), (double)positions[3].getY(), (double)positions[3].getZ()).color(red, green, blue, alpha).tex(0, 1).endVertex();
	}
}
