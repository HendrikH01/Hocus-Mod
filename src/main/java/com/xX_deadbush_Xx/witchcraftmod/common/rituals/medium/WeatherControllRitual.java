package com.xX_deadbush_Xx.witchcraftmod.common.rituals.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IStaticRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.MediumRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.BasicEffect;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.CraftingHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualStoneTile;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.RitualPedestalTile;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WeatherControllRitual extends MediumRitual implements IStaticRitual {
	public static final RitualConfig CONFIG = new RitualConfig(ConfigType.MEDIUM).addAnchorBlocks(4,  Blocks.LAPIS_BLOCK, ModBlocks.RITUAL_PEDESTAL.get()).addTotems(4, new Block[] {Blocks.CHISELED_STONE_BRICKS, ModBlocks.GREEN_TOTEM.get()});
	private Type type;
	
	public WeatherControllRitual(RitualStoneTile tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static WeatherControllRitual create(RitualStoneTile tile, PlayerEntity player) {
		return new WeatherControllRitual(tile, player);
	}
	
	@Override
	public boolean conditionsMet() {
		if (this.type != null) {
			if(!CraftingHelper.checkMatchUnordered(Arrays.asList(this.type.getItems()), getPedestals(), (item, tile) -> item.equals(tile.getStack().getItem())) ) {
				return false;
			}
		}
		
		return super.conditionsMet();
	}
	
	public GlowType getGlowType() {
		return GlowType.GREEN;
	}

	@Override
	public void init() {
		List<RitualPedestalTile> tiles = getPedestals();
		for(Type type : Type.values()) {
			if(CraftingHelper.checkMatchUnordered(Arrays.asList(type.getItems()), tiles, (item, tile) -> {
				return item.equals(tile.getStack().getItem());
			})) {
				this.type = type;
				System.out.println(world.isRaining() + " " + world.isThundering());
				switch(this.type) {
 				case RAIN:
					if(!world.isRaining() || world.isThundering()) return;
					break;
				case SUN:
					if(world.isRaining() || world.isThundering()) return;
					break;
				case THUNDER:
					if(!world.isThundering()) return;
					break;
				}
				stopRitual(false);
			}
		}
		
		stopRitual(false);
	}

	private List<RitualPedestalTile> getPedestals() {
		List<RitualPedestalTile> pedestals = new ArrayList<>();
		World world = this.tile.getWorld();
		for (BlockPos pos : this.anchorBlocks) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof RitualPedestalTile)
				pedestals.add((RitualPedestalTile) tile);
		}
		return pedestals;
	}

	@Override
	public RitualEffectHandler getEffectHandler() {
		return new RitualEffectHandler(this.tile, this.performingPlayer) {
			@Override
			public void init() {
				addEffect(new BasicEffect(player, ritualStone) {
					@Override
					protected void execute() {
						switch(WeatherControllRitual.this.type) {
						case RAIN:
							setRain(world, 6000);
							break;
						case SUN:
							setClear(world, 6000);
							break;
						case THUNDER:
							setThunder(world, 6000);
							break;
						default:
							break;
						}
						
						for(RitualPedestalTile tile : getPedestals()) {
							tile.clearItem();
						}
						
						stopRitual(true);
					}
				}, 10);
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
				if(!consumeEnergy(1000)) {
					stopRitual(false);
				} else nextPhase();
			}
		};
	}
	
	private static void setClear(World world, int time) {
		world.getWorldInfo().setClearWeatherTime(time);
		world.getWorldInfo().setRainTime(0);
		world.getWorldInfo().setThunderTime(0);
		world.getWorldInfo().setRaining(false);
		world.getWorldInfo().setThundering(false);
	}

	private static void setRain(World world, int time) {
		world.getWorldInfo().setClearWeatherTime(0);
		world.getWorldInfo().setRainTime(time);
		world.getWorldInfo().setThunderTime(time);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(false);
	}

	private static void setThunder(World world, int time) {
		world.getWorldInfo().setClearWeatherTime(0);
		world.getWorldInfo().setRainTime(time);
		world.getWorldInfo().setThunderTime(time);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(true);
	}
	
	private enum Type {
		SUN(ModItems.DUST_OF_NATURE.get(), Blocks.SUNFLOWER.asItem(), ModItems.DUST_OF_SERENITY.get(), Blocks.SUNFLOWER.asItem()),
		RAIN(ModItems.DUST_OF_NATURE.get(), Items.LAPIS_LAZULI, ModItems.DUST_OF_CHAOS.get(), Items.LAPIS_LAZULI),
		THUNDER(ModItems.DUST_OF_NATURE.get(), ModItems.DUST_OF_CHAOS.get(), ModItems.DUST_OF_DESTRUCTION.get(), ModItems.DUST_OF_CHAOS.get());

		private Item[] items;

		Type(Item...items) {
			this.items = items;
		}
		
		public Item[] getItems() {
			return items;
		}
	}
}