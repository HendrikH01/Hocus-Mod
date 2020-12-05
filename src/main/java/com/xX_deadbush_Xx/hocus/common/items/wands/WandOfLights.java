package com.xX_deadbush_Xx.hocus.common.items.wands;

import com.xX_deadbush_Xx.hocus.common.register.ModBlocks;
import com.xX_deadbush_Xx.hocus.common.spell.NoSpell;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class WandOfLights extends WandItem<NoSpell> {

	private static final float RANGE_SQUARED = 64;

	public WandOfLights(Properties properties) {
		super(properties);
	}
	
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		PlayerEntity player = context.getPlayer();
		ItemStack wand = context.getItem();
		World world = context.getWorld();
		if(attemptWandUse(player, wand)) {			
			BlockRayTraceResult result = world.rayTraceBlocks(new RayTraceContext(
					player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(MathHelper.sqrt(RANGE_SQUARED))),
					BlockMode.COLLIDER, FluidMode.NONE, player));

			if(result.getType() == Type.BLOCK) {
				BlockPos target = result.getPos().offset(result.getFace());
				BlockState state = world.getBlockState(target);
				
				if(state.isAir(world, target)) {
					world.setBlockState(target, ModBlocks.SHIMMER_BLOCK.get().getDefaultState(), 1);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return ActionResultType.PASS;
	}

	@Override
	public int getEnergyPerUse() {
		return 15;
	}

	@Override
	public int getCooldown() {
		return 5;
	}
}
