package com.xX_deadbush_Xx.hocus.client.renderers.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.xX_deadbush_Xx.hocus.common.entity.RavenEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

// Made with Blockbench 3.6.6

@OnlyIn(Dist.CLIENT)
public class RavenModel extends EntityModel<RavenEntity> {
	
	private final ModelRenderer rightFoot;
	private final ModelRenderer leftFoot;
	private final ModelRenderer body;
	private final ModelRenderer leftWing;
	private final ModelRenderer rightWing;
	private final ModelRenderer tail;
	private final ModelRenderer head;

	public RavenModel() {
		textureWidth = 32;
		textureHeight = 32;

		rightFoot = new ModelRenderer(this);
		rightFoot.setRotationPoint(2.0F, 24.0F, 0.0F);
		rightFoot.setTextureOffset(2, 4).addBox(-3.5F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
		rightFoot.setTextureOffset(0, 10).addBox(-3.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, false);

		leftFoot = new ModelRenderer(this);
		leftFoot.setRotationPoint(2.0F, 24.0F, 0.0F);
		leftFoot.setTextureOffset(0, 4).addBox(-1.5F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, 0.0F, false);
		leftFoot.setTextureOffset(0, 4).addBox(-1.5F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 21.75F, 0.5F);
		setRotationAngle(body, -0.6981F, 0.0F, 0.0F);
		body.setTextureOffset(0, 0).addBox(-1.5F, -4.0F, -4.0F, 3.0F, 4.0F, 6.0F, 0.0F, false);

		leftWing = new ModelRenderer(this);
		leftWing.setRotationPoint(0.0F, 24.25F, -0.5F);
		setRotationAngle(leftWing, -0.5236F, 0.0F, 0.0F);
		leftWing.setTextureOffset(0, 10).addBox(1.5F, -6.991F, -3.2835F, 1.0F, 3.0F, 6.0F, 0.0F, false);

		rightWing = new ModelRenderer(this);
		rightWing.setRotationPoint(0.0F, 23.25F, -0.5F);
		setRotationAngle(rightWing, -0.5236F, 0.0F, 0.0F);
		rightWing.setTextureOffset(12, 4).addBox(-2.5F, -6.0678F, -2.7505F, 1.0F, 3.0F, 6.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 22.8F, 0.0F);
		setRotationAngle(tail, -0.3054F, 0.0F, 0.0F);
		tail.setTextureOffset(14, 14).addBox(-1.5F, -4.2037F, 2.1993F, 3.0F, 1.0F, 4.0F, 0.0F, false);

		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 24.0F, 0.0F);
		head.setTextureOffset(0, 19).addBox(-1.0F, -9.25F, -2.25F, 2.0F, 4.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(0, 0).addBox(-1.0F, -9.25F, -3.25F, 2.0F, 3.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 10).addBox(-0.5F, -8.25F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		head.setTextureOffset(8, 10).addBox(-0.5F, -8.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(RavenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		rightFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		leftFoot.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
		rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
		tail.render(matrixStack, buffer, packedLight, packedOverlay);
		head.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}