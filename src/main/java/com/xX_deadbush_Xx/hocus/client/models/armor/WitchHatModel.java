package com.xX_deadbush_Xx.hocus.client.models.armor;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class WitchHatModel extends ArmorModelBase {

	public WitchHatModel() {
		super(EquipmentSlotType.HEAD);
		this.textureWidth = 64;
        textureHeight = 64;

        bipedHead = new ModelRenderer(this);
        bipedHead.setRotationPoint(0.0F, 22.0F, 0.0F);
        bipedHead.setTextureOffset(0, 0).addBox(-6.0F, -1.0F - 8, -6.0F, 12.0F, 1.0F, 12.0F, 0.0F, false);
        bipedHead.setTextureOffset(0, 13).addBox(-3.0F, -4.0F - 8, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
        bipedHead.setTextureOffset(24, 13).addBox(-2.0F, -8.0F - 8, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        bipedHead.setTextureOffset(0, 22).addBox(-1.0F, -10.0F - 8, -1.0F, 2.0F, 2.0F, 5.0F, 0.0F, false);
	}

	@Override
	public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
		bipedHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
	}
	
	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		if(entity instanceof ArmorStandEntity) this.bipedHead.setRotationPoint(0.0F, 3.5F, -1.0F);
		this.bipedHead.rotateAngleX -= 0.15;
	}
}