package com.xX_deadbush_Xx.witchcraftmod.api.ritual.activation;

import com.xX_deadbush_Xx.witchcraftmod.api.ritual.RitualManager;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus=Bus.FORGE)
public class SacrificeTask extends RitualTask<LivingDeathEvent> {
	
	public SacrificeTask(int ticksuntilexpire, boolean necessary) {
		super(ticksuntilexpire, necessary);
	}
	
	@SubscribeEvent
	public static void onKill(LivingDeathEvent event) {
		if(event.getSource().getTrueSource() instanceof PlayerEntity) {
			if(event.getEntityLiving() instanceof AnimalEntity) {
				RitualManager.getInstance().handleTaskEvent(event, SacrificeTask.class);
			}
		}
	}

	@Override
	public void expire() {
		System.out.println("TASK EXPIRED!");
	}

	@Override
	protected boolean tryComplete(LivingDeathEvent event) {
		System.out.println("TASK COMPLETED! " + event.getEntity());
		return true;
	}
}
