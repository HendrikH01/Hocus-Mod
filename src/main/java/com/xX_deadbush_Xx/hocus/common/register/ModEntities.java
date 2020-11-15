package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.entity.LightningBallEntity;
import com.xX_deadbush_Xx.hocus.common.entity.RavenEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Hocus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Hocus.MOD_ID);
	
	public static final RegistryObject<EntityType<LightningBallEntity>> LIGHTING_BALL = ENTITIES.register("lightning_ball", () -> 
		EntityType.Builder.<LightningBallEntity>create(LightningBallEntity::new, EntityClassification.MISC).size(0.6f, 0.6f)
		.build(new ResourceLocation(Hocus.MOD_ID, "lightning_ball").toString()));
	
	public static final RegistryObject<EntityType<RavenEntity>> RAVEN = ENTITIES.register("raven", () -> EntityType.Builder.<RavenEntity>create(RavenEntity::new, EntityClassification.CREATURE)
			.size(0.6f, 0.7f).build(new ResourceLocation(Hocus.MOD_ID, "raven").toString()));
	
}
