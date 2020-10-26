package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.entity.LightningBallEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, Hocus.MOD_ID);
	public static final RegistryObject<EntityType<LightningBallEntity>> LIGHTING_BALL = ENTITIES.register("lightning_ball", () -> 
		EntityType.Builder.<LightningBallEntity>create(LightningBallEntity::new, EntityClassification.MISC).size(1.0f, 1.0f)
		.build(new ResourceLocation(Hocus.MOD_ID, "lightning_ball").toString()));	 	
}
