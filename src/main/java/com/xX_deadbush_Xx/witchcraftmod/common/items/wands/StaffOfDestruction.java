package com.xX_deadbush_Xx.witchcraftmod.common.items.wands;

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

public class StaffOfDestruction extends WandItem {
	public StaffOfDestruction(Properties properties) {
		super(properties);
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