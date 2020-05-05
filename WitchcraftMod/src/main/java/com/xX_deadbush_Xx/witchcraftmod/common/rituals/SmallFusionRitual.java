package com.xX_deadbush_Xx.witchcraftmod.common.rituals;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IRitualSmall;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualAnimation;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.SmallRitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.util.SimpleItemHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.compat.jei.WitchcraftJEIPlugin;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.SmallFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualPedestalTile;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class SmallFusionRitual implements IRitualSmall {
	private final Block pedestal = ModBlocks.RITUAL_PEDESTAL.get();
	public final SmallRitualConfig config = new SmallRitualConfig(pedestal, pedestal, pedestal, pedestal);
	public static SmallFusionRitual INSTANCE = new SmallFusionRitual();
	
	@Override
	public boolean multiblockComplete(BlockPos ritualStonePos, World worldIn) {
		return chalkInPlace(ritualStonePos, worldIn) && junctionBlocksInPlace(config, worldIn, ritualStonePos);
	}

	@Override
	public boolean conditionsMet(BlockPos ritualStonePos, World worldIn, PlayerEntity player) {
		return multiblockComplete(ritualStonePos, worldIn);
	}

	@Override
	public void activate(BlockPos ritualStonePos, World worldIn, PlayerEntity player) {
		if(worldIn.isRemote) return;
		if(!conditionsMet(ritualStonePos, worldIn, player)) return;
		
		RitualStoneTile tile = (RitualStoneTile) worldIn.getTileEntity(ritualStonePos);
		SmallFusionRecipe recipe = getRecipe(tile, worldIn);
		if(recipe != null) {
			System.out.println(recipe.getRecipeOutput());
			tile.lastRecipe = recipe;
			tile.animationHandler.set(new SmallFusionAnimation(tile, player));
		}
	}
	
	private SmallFusionRecipe getRecipe(RitualStoneTile ritualStoneTile, World worldIn) {
		ItemStack stack = ritualStoneTile.getItem();
		if(stack == null) return null;
		
		List<IRecipe<?>> recipes = WitchcraftJEIPlugin.findRecipesByType(ModRecipeTypes.SMALL_FUSION_TYPE);
		for(IRecipe<?> r : recipes) {
			SmallFusionRecipe recipe = (SmallFusionRecipe) r;
			ItemStack[] ritualstoneItemArray = {ritualStoneTile.getItem()};
			ItemStack[] array = ArrayUtils.addAll(ritualstoneItemArray, getPedestalItems(ritualStoneTile.getPos(), worldIn));
			if(recipe.matches(new RecipeWrapper(new SimpleItemHandler(5, array)), worldIn)) {
				return recipe;
			}
		}
		return null;
	}
	
	private ItemStack[] getPedestalItems(BlockPos ritualStonePos, World worldIn) {
		ItemStack[] items = new ItemStack[4];
		RitualPedestalTile[] pedestals = getPedestals(ritualStonePos, worldIn);
		for(int i = 0; i < 4; i++) {
			items[i] = pedestals[i].getItem();
		}
		return items;
	}
	
	private RitualPedestalTile[] getPedestals(BlockPos ritualStonePos, World worldIn) {
		RitualPedestalTile[] pedestals = new RitualPedestalTile[4];
		for(int i = 0; i < 4; i++) {
			Direction direction = Direction.byHorizontalIndex(i);
			pedestals[i] =  (RitualPedestalTile) worldIn.getTileEntity(ritualStonePos.offset(direction, 2));
		}
		return pedestals;
	}
	
	private class SmallFusionAnimation extends RitualAnimation {
		public SmallFusionAnimation(RitualStoneTile tile, PlayerEntity player) {
			super(tile, player);
		}

		public void initAnimation() {
			this.addEffect((tile, player) -> {
				BlockPos pos = tile.getPos();
				for(RitualPedestalTile pedestal : getPedestals(pos, tile.getWorld())) {
					BlockPos pedestalpos = pedestal.getPos();
					WitchcraftPacketHandler.sendToNearby(tile.getWorld(), pos, new WitchcraftParticlePacket(EffectType.PEDESTAL_DISAPPEAR, pedestalpos.getX() + 0.5, pedestalpos.getY() + 1.4, pedestalpos.getZ() + 0.5));
				}
			}, 5);
			this.addEffect((tile, player) -> {
				BlockPos pos = tile.getPos();
				for(RitualPedestalTile pedestal : getPedestals(pos, tile.getWorld())) {
					pedestal.useForCrafting();
				}
				WitchcraftPacketHandler.sendToNearby(tile.getWorld(), pos, new WitchcraftParticlePacket(EffectType.RITUAL_ITEM_CREATE, pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5));
				tile.setItem(tile.lastRecipe.getRecipeOutput().copy());
				tile.markDirty();
			}, 40);
		}
	}
}
