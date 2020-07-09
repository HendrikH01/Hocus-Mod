package com.xX_deadbush_Xx.witchcraftmod.common.world.data;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WitchcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CapabilityAttachEvent {

    @SubscribeEvent
    public static void attackToPlayer(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(WitchcraftMod.MOD_ID, "player_crystal_energy"), new PlayerCrystalEnergyProvider());
    }

}