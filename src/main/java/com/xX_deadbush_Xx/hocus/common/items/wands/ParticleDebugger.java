package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.common.entity.ManaShardEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ParticleDebugger extends Item {

	public ParticleDebugger(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack stack = player.getHeldItem(hand);
		Vec3d look = player.getLookVec();
		ManaShardEntity s = new ManaShardEntity(world, player.getPosX(), player.getPosY() + 1, player.getPosZ(), look.x/200, look.y/200, look.z/200);
		world.addEntity(s);
		
		return ActionResult.resultSuccess(stack);
	}
}
