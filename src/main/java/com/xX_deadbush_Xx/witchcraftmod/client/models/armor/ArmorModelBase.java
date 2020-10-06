package com.xX_deadbush_Xx.witchcraftmod.client.models.armor;

import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class ArmorModelBase extends BipedModel<LivingEntity>{
	EquipmentSlotType type;
	
	public ArmorModelBase(EquipmentSlotType type) {
		super(1);
		this.type = type;
	}

	@Override
	public void setRotationAngles(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (entity instanceof ArmorStandEntity) {
			ArmorStandEntity armorstand = (ArmorStandEntity) entity;
			switch(this.type) {
			case CHEST:
				this.bipedBody.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getBodyRotation().getX();
				this.bipedBody.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getBodyRotation().getY();
				this.bipedBody.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getBodyRotation().getZ();
				this.bipedLeftArm.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getLeftArmRotation().getX();
				this.bipedLeftArm.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getLeftArmRotation().getY();
				this.bipedLeftArm.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getLeftArmRotation().getZ();
				this.bipedRightArm.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getRightArmRotation().getX();
				this.bipedRightArm.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getRightArmRotation().getY();
				this.bipedRightArm.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getRightArmRotation().getZ();
				break;
			case HEAD:
				this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
				this.bipedHead.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getHeadRotation().getX();
				this.bipedHead.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getHeadRotation().getY();
				this.bipedHead.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getHeadRotation().getZ();
				this.bipedHeadwear.copyModelAngles(this.bipedHead);		
				break;
			default: //FEET or LEGS
				this.bipedLeftLeg.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getLeftLegRotation().getX();
				this.bipedLeftLeg.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getLeftLegRotation().getY();
				this.bipedLeftLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getLeftLegRotation().getZ();
				this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
				this.bipedRightLeg.rotateAngleX = ((float) Math.PI / 180F) * armorstand.getRightLegRotation().getX();
				this.bipedRightLeg.rotateAngleY = ((float) Math.PI / 180F) * armorstand.getRightLegRotation().getY();
				this.bipedRightLeg.rotateAngleZ = ((float) Math.PI / 180F) * armorstand.getRightLegRotation().getZ();
				this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
				break;
			}
		} else
			super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}
}