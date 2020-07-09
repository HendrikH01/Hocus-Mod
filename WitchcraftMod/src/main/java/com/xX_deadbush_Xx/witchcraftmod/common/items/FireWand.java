package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;

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
	protected ActionResult<ItemStack> onWandUse(World worldIn, PlayerEntity player, Hand handIn, ItemStack wand) {
		if(player.areEyesInFluid(FluidTags.WATER)) return ActionResult.resultPass(wand);
		Vec3d look = player.getLookVec();
		Vec3d pos = player.getPositionVec();
		FireballEntity fireball = new FireballEntity(worldIn, pos.x + look.x, pos.y + 0.6, pos.z + look.z, look.x, look.y, look.z);
		fireball.explosionPower = 2;
		worldIn.addEntity(fireball);
		if(!worldIn.isRemote) WitchcraftPacketHandler.sendToNearby(worldIn, player, 
				new WitchcraftParticlePacket(EffectType.FIRE_WAND, pos.x + look.x, pos.y + 0.6, pos.z + look.z, (float)look.x, (float)look.y, (float)look.z));
		return ActionResult.resultSuccess(wand);
	}

	@Override
	public int getEnergyPerUse(ItemStack wand) {
		return 150;
	}


	@Override
	public int getCooldown() {
		return 10;
	}
 }