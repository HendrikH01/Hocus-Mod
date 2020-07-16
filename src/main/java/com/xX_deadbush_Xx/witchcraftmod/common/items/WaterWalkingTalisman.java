package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.LogicalSide;

public class WaterWalkingTalisman extends TalismanItem {

	public WaterWalkingTalisman(Properties properties) {
		super(properties);
	}

	@Override
	protected int getTickInterval() {
		return 1;
	}

	@Override
	public int getManaCostPerTick() {
		return 1;
	}

	@Override
	protected boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side) {

		if(player.isInWater() && !player.isSneaking()) {
			player.onGround = true;
			double distancetosurface = player.getSubmergedHeight();
			BlockPos pos = player.getPosition().up(1);
			int i = 0;
			
			while(true) {
				i++;
				IFluidState state = player.world.getBlockState(pos.up(i)).getFluidState();
				if(!state.isTagged(FluidTags.WATER)) break;
				distancetosurface += state.getActualHeight(player.world, pos.up(i));
			}
			double motionY = Math.max(0, player.getMotion().y) + distancetosurface/15;
			player.setMotion(player.getMotion().mul(1.15, 0, 1.15).add(0, motionY, 0));

			if(side == LogicalSide.SERVER) return true;
			//view bobbing and particles
			
			if(player.distanceWalkedModified - player.prevDistanceWalkedModified > 0.02) {
				Vec3d splashmotion = player.getMotion().inverse().add(0, 0.1, 0);
				Random rand = new Random();
				
				for (int j = 0; j < 4.0F; ++j) {
					double posx = player.getPosX() + rand.nextFloat() - 0.5;
					double posz = player.getPosZ() + rand.nextFloat() - 0.5;
					double posy = MathHelper.floor(player.getPosY()) + 0.9F;
					player.world.addParticle(ParticleTypes.SPLASH, posx, posy, posz, splashmotion.x, splashmotion.y, splashmotion.z);
				}
			}
			
			float f = 0;
			if (player.onGround && !(player.getHealth() <= 0.0F) && !player.isSwimming()) {
				f = Math.min(0.1F, MathHelper.sqrt(Entity.horizontalMag(player.getMotion())));
			}

			player.cameraYaw = player.prevCameraYaw; //reset
			player.cameraYaw += (f - player.cameraYaw) * 0.4F;

			return true;
		} else return false;
	}

}
