package com.xX_deadbush_Xx.witchcraftmod.common.rituals.medium;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.IStaticRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.MediumRitual;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.RitualActivationTaskHandler;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation.SacrificeTask;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.ConfigType;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.config.RitualConfig;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.BasicEffect;
import com.xX_deadbush_Xx.witchcraftmod.api.ritual.effect.RitualEffectHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;
import com.xX_deadbush_Xx.witchcraftmod.common.blocks.blockstate.GlowType;
import com.xX_deadbush_Xx.witchcraftmod.common.network.WitchcraftPacketHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.AbstractRitualCore;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

public class BloodInfusionRitual extends MediumRitual implements IStaticRitual {
	public static final RitualConfig config = new RitualConfig(ConfigType.MEDIUM).addAnchorBlocks(4, ModBlocks.FIRE_BOWL.get(), ModBlocks.CANDLE.get()).addTotems(4, new Block[] {Blocks.CHISELED_STONE_BRICKS, ModBlocks.RED_TOTEM.get()});
	private final double checkradius = 8;
	private int manaconsumecooldown = 20;

	public BloodInfusionRitual(AbstractRitualCore tile, PlayerEntity player) {
		super(tile, player);
	}
	
	public static BloodInfusionRitual create(AbstractRitualCore tile, PlayerEntity player) {
		return new BloodInfusionRitual(tile, player);
	}
	
	@Override
	public GlowType getGlowType() {
		return GlowType.RED;
	}

	@Override
	public RitualConfig getConfig() {
		return config;
	}

	@Override
	public RitualEffectHandler getEffectHandler() {
		return new RitualEffectHandler(tile, performingPlayer) {
			@Override
			public void init() {
				this.addEffect(new BasicEffect(performingPlayer, tile) {

					@Override
					public void execute() {
						BlockPos pos = tile.getPos();
						WitchcraftPacketHandler.sendToNearby(tile.getWorld(), pos,
								new WitchcraftParticlePacket(EffectType.PEDESTAL_DISAPPEAR, pos.getX() + 0.5,
										pos.getY() + 1.4, pos.getZ() + 0.5));

						stopRitual(true);
					}
				}, 10);
			}
		};
	}
	
	@Override
	public RitualActivationTaskHandler getActivationHandler() {
		return new RitualActivationTaskHandler(tile, performingPlayer) {
			private int kills = 0;
			
			@Override
			public void init() {
				this.setStopTime(500);
				this.addTask(new InfusionSacrificeTask());
			}
			
			@Override
			protected void onActivationEnd() {
				System.out.println("ACTIVATION ENDED! " + kills + " KILLS!");
			}
			
			class InfusionSacrificeTask extends SacrificeTask {

				public InfusionSacrificeTask() {
					super(72000, false);
				}
				
				@Override
				protected boolean tryComplete(LivingDeathEvent event) {
					if(super.tryComplete(event)) {
						addTask(new InfusionSacrificeTask());
						kills++;
						return true;
					} else return false;
				}
			}
		};
	}
}
