package com.xX_deadbush_Xx.hocus.common.items.wands;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.World;

public class ParticleDebugger extends Item {

	public ParticleDebugger(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		System.out.println(world.getTileEntity(context.getPos()));
		return ActionResultType.PASS;
	}
}
