package com.xX_deadbush_Xx.hocus.api.util;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;

public class RenderHelper {
	public static boolean isSolidOrTranslucent(RenderType layerToCheck) {
		return layerToCheck == RenderType.getSolid() || layerToCheck == RenderType.getTranslucent();
	}
	
	public static boolean isSolidOrCutout(RenderType layerToCheck) {
	    return layerToCheck == RenderType.getSolid() || layerToCheck == RenderType.getCutout();
	}
	
	//Copy of PlayerEntity.applyRotations
	@SuppressWarnings("unchecked")
	public static void positionMatrixStackToHand(PlayerEntity player, MatrixStack stack, HandSide hand) {
		if (player.getPose() == Pose.SLEEPING) {
			Direction direction = player.getBedDirection();
			if (direction != null) {
				float f4 = player.getEyeHeight(Pose.STANDING) - 0.1F;
				stack.translate((double) ((float) (-direction.getXOffset()) * f4), 0.0D, (double) ((float) (-direction.getZOffset()) * f4));
			}
		}
	  
		float partialTicks = Minecraft.getInstance().getRenderPartialTicks();
		float rotationYaw = MathHelper.interpolateAngle(partialTicks, player.prevRenderYawOffset, player.renderYawOffset);
		float ageInTicks = (float) player.ticksExisted + partialTicks;

		float f = player.getSwimAnimation(partialTicks);
		if (player.isElytraFlying()) {
			applyLivingEntityRotations(player, stack, ageInTicks, rotationYaw, partialTicks);
			float f1 = (float) player.getTicksElytraFlying() + partialTicks;
			float f2 = MathHelper.clamp(f1 * f1 / 100.0F, 0.0F, 1.0F);
			if (!player.isSpinAttacking()) {
				stack.rotate(Vector3f.XP.rotationDegrees(f2 * (-90.0F - player.rotationPitch)));
			}

			Vec3d vec3d = player.getLook(partialTicks);
			Vec3d vec3d1 = player.getMotion();
			double d0 = Entity.horizontalMag(vec3d1);
			double d1 = Entity.horizontalMag(vec3d);
			if (d0 > 0.0D && d1 > 0.0D) {
				double d2 = (vec3d1.x * vec3d.x + vec3d1.z * vec3d.z) / (Math.sqrt(d0) * Math.sqrt(d1));
				double d3 = vec3d1.x * vec3d.z - vec3d1.z * vec3d.x;
				stack.rotate(Vector3f.YP.rotation((float) (Math.signum(d3) * Math.acos(d2))));
			}
		} else if (f > 0.0F) {
			applyLivingEntityRotations(player, stack, ageInTicks, rotationYaw, partialTicks);
			float f3 = player.isInWater() ? -90.0F - player.rotationPitch : -90.0F;
			float f4 = MathHelper.lerp(f, 0.0F, f3);
			stack.rotate(Vector3f.XP.rotationDegrees(f4));
			if (player.isActualySwimming()) {
				stack.translate(0.0D, -1.0D, (double) 0.3F);
			}
		} else {
			applyLivingEntityRotations(player, stack, ageInTicks, rotationYaw, partialTicks);
		}
		
		
		stack.scale(-1.0F, -1.0F, 1.0F);
	    stack.scale(0.9375F, 0.9375F, 0.9375F);
		stack.translate(0.0D, (double) -1.501F, 0.0D);
		
		BipedModel<?> model = (BipedModel<?>) ((LivingRenderer<? extends LivingEntity, ? extends EntityModel<?>>) 
				(Minecraft.getInstance().getRenderManager().getRenderer(player))).getEntityModel();
		model.translateHand(hand, stack);
	}
	
	//Copy of LivingEntity.applyRotations
	private static void applyLivingEntityRotations(PlayerEntity player, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
		Pose pose = player.getPose();
		if (pose != Pose.SLEEPING) {
			stack.rotate(Vector3f.YP.rotationDegrees(rotationYaw));
		}

		if (player.deathTime > 0) {
			float f = ((float) player.deathTime + partialTicks - 1.0F) / 20.0F * 1.6F;
			f = MathHelper.sqrt(f);
			if (f > 1.0F) {
				f = 1.0F;
			}

			stack.rotate(Vector3f.ZP.rotationDegrees(f * 90));
		} else if (player.isSpinAttacking()) {
			stack.rotate(Vector3f.XP.rotationDegrees(-90.0F - player.rotationPitch));
			stack.rotate(Vector3f.YP.rotationDegrees(((float) player.ticksExisted + partialTicks) * -75.0F));
		} else if (pose == Pose.SLEEPING) {
			Direction direction = player.getBedDirection();
			float f1 = direction != null ? getFacingAngle(direction) : rotationYaw;
			stack.rotate(Vector3f.YP.rotationDegrees(f1));
			stack.rotate(Vector3f.ZP.rotationDegrees(90));
			stack.rotate(Vector3f.YP.rotationDegrees(270.0F));
		} else if (player.hasCustomName() || player instanceof PlayerEntity) {
			String s = TextFormatting.getTextWithoutFormattingCodes(player.getName().getString());
			if (("Dinnerbone".equals(s) || "Grumm".equals(s))
					&& (!(player instanceof PlayerEntity) || ((PlayerEntity) player).isWearing(PlayerModelPart.CAPE))) {
				stack.translate(0.0D, (double) (player.getHeight() + 0.1F), 0.0D);
				stack.rotate(Vector3f.ZP.rotationDegrees(180.0F));
			}
		}
	}

	public static float getFacingAngle(Direction facingIn) {
		switch (facingIn) {
		case SOUTH:
			return 90.0F;
		case WEST:
			return 0.0F;
		case NORTH:
			return 270.0F;
		case EAST:
			return 180.0F;
		default:
			return 0.0F;
		}
	}
}
