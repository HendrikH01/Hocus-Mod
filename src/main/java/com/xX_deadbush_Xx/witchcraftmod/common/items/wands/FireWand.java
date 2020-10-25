package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.HocusSParticlePacket;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireWand extends WandItem {
	public FireWand(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack wand = player.getHeldItem(hand);
		if(player.areEyesInFluid(FluidTags.WATER) || !this.attemptWandUse(player, wand)) 
			return ActionResult.resultPass(wand);
	
		Vec3d look = player.getLookVec();
		Vec3d pos = player.getPositionVec();
		FireballEntity fireball = new FireballEntity(world, pos.x + look.x, pos.y + 0.6, pos.z + look.z, look.x, look.y, look.z);
		fireball.explosionPower = 2;
		world.addEntity(fireball);
		
		if(!world.isRemote) HocusPacketHandler.sendToNearby(world, player, new HocusSParticlePacket(EffectType.FIRE_WAND, pos.x + look.x, pos.y + 0.6, pos.z + look.z, (float)look.x, (float)look.y, (float)look.z));
		return ActionResult.resultSuccess(wand);
	}

	@Override
	public int getEnergyPerUse() {
		return 150;
	}


	@Override
	public int getCooldown() {
		return 10;
	}
 }