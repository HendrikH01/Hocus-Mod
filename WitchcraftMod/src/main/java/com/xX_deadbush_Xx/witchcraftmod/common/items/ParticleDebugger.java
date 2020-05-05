package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleDebugger extends Item {

	public ParticleDebugger(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if(world.isRemote) {
			System.out.println("outtt");
			Direction blockFace = context.getFace();
			BlockPos pos = context.getPos().offset(blockFace);
			ClientParticleHandler.addEffect(EffectType.RITUAL_ITEM_CREATE, world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
			return ActionResultType.SUCCESS;
		} else return ActionResultType.PASS;
	}
}
