package com.xX_deadbush_Xx.hocus.client.renderers.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.entity.LightningBallEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix3f;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightningBallRenderer extends EntityRenderer<LightningBallEntity> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Hocus.MOD_ID, "textures/entity/lightning_ball.png");
	private static final RenderType RENDER_TYPE = RenderType.getEntityTranslucentCull(TEXTURE);

	public LightningBallRenderer(EntityRendererManager rendermanager) {
		super(rendermanager);
	}

	protected int getBlockLight(LightningBallEntity entity, float light) {
		return 15;
	}
	
	//Vanilla copy from DragonFireballRenderer
	public void render(LightningBallEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightlevel) {
		IVertexBuilder builder = buffer.getBuffer(RENDER_TYPE);		
		matrixStack.push();
		matrixStack.scale(2.0F, 2.0F, 2.0F);
		matrixStack.rotate(this.renderManager.getCameraOrientation());
		matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0F));
		MatrixStack.Entry matrixstack$entry = matrixStack.getLast();
		Matrix4f matrix = matrixstack$entry.getMatrix();
		Matrix3f normal = matrixstack$entry.getNormal();
		addVertex(builder, matrix, normal, lightlevel, 0, 0, 0, 1);
		addVertex(builder, matrix, normal, lightlevel, 1, 0, 1, 1);
		addVertex(builder, matrix, normal, lightlevel, 1, 1, 1, 0);
		addVertex(builder, matrix, normal, lightlevel, 0, 1, 0, 0);

		matrixStack.pop();
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, lightlevel);
	}

	private static void addVertex(IVertexBuilder builder, Matrix4f matrix, Matrix3f normal, int lightlevel, float x, float y, float textureX, float textureY) {
		builder.pos(matrix, x - 0.5F, y - 0.25F, 0.0F)
		.color(255, 255, 255, 255)
		.tex(textureX, textureY)
		.overlay(OverlayTexture.NO_OVERLAY)
		.lightmap(lightlevel)
		.normal(normal, 0.0F, 1.0F, 0.0F).endVertex();   
	}

	public ResourceLocation getEntityTexture(LightningBallEntity p_110775_1_) {
		return TEXTURE;
	}
}
