package com.xX_deadbush_Xx.witchcraftmod.common.items;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.MortarTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicChalk extends Item {
	public MagicChalk(Properties properties) {
		super(properties);
	}
	
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		Direction blockFace = context.getFace();
		BlockPos pos = context.getPos().offset(blockFace);
		BlockState stateToReplace = world.getBlockState(pos);
		BlockState stateForPlacement = ChalkBlock.getStateForPlacement(world, pos);
		if (stateForPlacement.isValidPosition(world, pos) && canBeReplaced(stateToReplace)) {
			Block.replaceBlock(stateToReplace, stateForPlacement, world, pos, 1);

			PlayerEntity player = context.getPlayer();

			if (!player.abilities.isCreativeMode) {
				context.getItem().damageItem(1, player, (p) -> {
					p.sendBreakAnimation(context.getHand()); // on break
				});
			}
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}/*
	
		public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		Direction blockFace = context.getFace();

		TileEntity te = world.getTileEntity(context.getPos());
		if(te instanceof MortarTile) {
			MortarTile t = (MortarTile)te;
			if(blockFace == Direction.NORTH)t.v1+=6;
			if(blockFace == Direction.UP)t.v2+= 6;
			if(blockFace == Direction.EAST)t.v3+=6;
			if(blockFace == Direction.SOUTH)t.v1-=6;
			if(blockFace == Direction.DOWN)t.v2-= 6;
			if(blockFace == Direction.WEST)t.v3-=6;
			
			System.out.println(t.get1() + " " + t.get2() + " " + t.get3());
		}
		return ActionResultType.PASS;
	}*/
	
	private boolean canBeReplaced(BlockState state) {
        return state.getMaterial() == Material.AIR || state.isIn(BlockTags.FLOWERS) || state.getBlock() == Blocks.GRASS;
	}
 }
