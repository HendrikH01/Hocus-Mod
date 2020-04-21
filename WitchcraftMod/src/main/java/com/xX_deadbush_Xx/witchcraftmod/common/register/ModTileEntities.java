package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.DryingRackTile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, WitchcraftMod.MOD_ID);

	public static final RegistryObject<TileEntityType<DryingRackTile>> DRYING_RACK = TILE_ENTITIES.register("drying_rack_tile",
			() -> TileEntityType.Builder.create(DryingRackTile::new, ModBlocks.DRYING_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<DryingRackTile>> RITUAL_STONE = TILE_ENTITIES.register("ritual_stone_tile",
			() -> TileEntityType.Builder.create(DryingRackTile::new, ModBlocks.RITUAL_STONE.get()).build(null));	 

}
