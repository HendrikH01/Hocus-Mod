package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

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
			System.out.println("Attempted to spawn particle with debugger");
			Direction blockFace = context.getFace();
			BlockPos pos = context.getPos().offset(blockFace);
			world.addParticle(new ScaledColoredParticleData(ModParticles.MANAWAVE, true, 0x80B4CE, 1), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,  pos.getX() + 15, pos.getY() + 0, pos.getZ() + 0);
			return ActionResultType.SUCCESS;
		} else return ActionResultType.PASS;
	}
}
