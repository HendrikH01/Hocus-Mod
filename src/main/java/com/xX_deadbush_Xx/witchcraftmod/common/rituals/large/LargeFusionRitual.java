package com.xX_deadbush_Xx.witchcraftmod.common.rituals.large;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.IFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.api.crafting.recipes.ModRecipeTypes;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.SimpleItemHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.ICraftingRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IStaticRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.LargeRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.BasicEffect;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.LargeFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.recipes.MediumFusionRecipe;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualPedestalTile;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class LargeFusionRitual extends LargeRitual implements ICraftingRitual, IStaticRitual {
	public static final RitualConfig CONFIG = new RitualConfig(ConfigType.LARGE).addAnchorBlocks(8, ModBlocks.RITUAL_PEDESTAL.get()).addTotems(4, 
					new Block[] {Blocks.CHISELED_STONE_BRICKS, ModBlocks.RED_TOTEM.get()}, 
					new Block[] {Blocks.CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, ModBlocks.FIRE_BOWL.get()}, 
					new Block[] {Blocks.CHISELED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, ModBlocks.FIRE_BOWL.get()});
	
	public LargeFusionRitual(RitualStoneTile tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static LargeFusionRitual create(RitualStoneTile tile, PlayerEntity player) {
		return new LargeFusionRitual(tile, player);
	}
	
	public GlowType getGlowType() {
		return GlowType.RED;
	}

	@Override
	public void init() {
		LargeFusionRecipe recipe = getRecipe();
		if(recipe != null) {
			tile.lastRecipe = recipe;
			this.effecthandler = getEffectHandler();
		} else {
			this.stopRitual(false);
		}
	}
	
	@Override
	public LargeFusionRecipe getRecipe() {
		ItemStack stack = this.tile.getItem();
		if(stack == null) return null;
		
		Set<IRecipe<?>> recipes = CraftingHelper.findRecipesByType(ModRecipeTypes.LARGE_FUSION_TYPE);
		for(IRecipe<?> r : recipes) {
			LargeFusionRecipe recipe = (LargeFusionRecipe) r;
			if(recipeComplete(recipe)) {
				return recipe;
			}
		}
		return null;
	}
	
	@Override
	public ItemStack[] getRecipeInputs() {
		ItemStack[] items = new ItemStack[9];
		items[0] = this.tile.getItem();

		List<RitualPedestalTile> pedestals = getPedestals();
		for(int i = 1; i < 9; i++) {
			items[i] = pedestals.get(i-1).getStack();
		}
		return items;
	}
	
	private List<RitualPedestalTile> getPedestals() {
		List<RitualPedestalTile> pedestals = new ArrayList<>();
		World world = this.tile.getWorld();
		for(BlockPos pos : this.anchorBlocks) {
			TileEntity tile = world.getTileEntity(pos);
			if(tile instanceof RitualPedestalTile) pedestals.add((RitualPedestalTile)tile);
		}
		return pedestals;
	}
	
	private boolean recipeComplete(IRecipe<RecipeWrapper> recipe) {
		return recipe.matches(new RecipeWrapper(new SimpleItemHandler(9, getRecipeInputs())), this.world);
	}

	@Override
	public RitualEffectHandler getEffectHandler() {
		return new RitualEffectHandler(this.tile, this.performingPlayer) {
			@Override
			public void init() {
				RitualEffectHandler handler = this;
				this.addEffect(new BasicEffect(player, ritualStone) {
					@Override
					public void execute() {
						BlockPos pos = tile.getPos();
						for(RitualPedestalTile pedestal : getPedestals()) {
							BlockPos pedestalpos = pedestal.getPos();
							WitchcraftPacketHandler.sendToNearby(tile.getWorld(), pos, new WitchcraftParticlePacket(EffectType.PEDESTAL_DISAPPEAR, pedestalpos.getX() + 0.5, pedestalpos.getY() + 1.4, pedestalpos.getZ() + 0.5));
						}
					} 
				}, 10);
				
				this.addEffect(new BasicEffect(player, ritualStone) {
					@Override
					public void execute() {
						if(!recipeComplete(tile.lastRecipe)) {
							stopRitual(true); 
							return;
						}
						BlockPos pos = tile.getPos();
						for(RitualPedestalTile pedestal : getPedestals()) {
							pedestal.useForCrafting();
						}
						WitchcraftPacketHandler.sendToNearby(tile.getWorld(), pos, new WitchcraftParticlePacket(EffectType.RITUAL_ITEM_CREATE, pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5));
						tile.setItem(tile.lastRecipe.getRecipeOutput().copy());
						tile.markDirty();
						handler.stopEffect(true);
					}
				}, 80);
			}
		};
	}

	@Override
	public RitualConfig getConfig() {
		return CONFIG;
	}

	@Override
	public RitualActivationTaskHandler getActivationHandler() {
		return new RitualActivationTaskHandler(this.tile, this.performingPlayer) {

			@Override
			public void init() {
				consumeEnergy(((IFusionRecipe)tile.lastRecipe).getActivationCost());
			}
		};
	}
}