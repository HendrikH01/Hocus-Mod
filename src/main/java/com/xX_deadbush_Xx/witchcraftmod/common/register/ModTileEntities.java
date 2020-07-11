package com.xX_deadbush_Xx.witchcraftmod.common.register;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.*;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
	public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, WitchcraftMod.MOD_ID);

	public static final RegistryObject<TileEntityType<DryingRackTile>> DRYING_RACK = TILE_ENTITIES.register("drying_rack_tile",
			() -> TileEntityType.Builder.create(DryingRackTile::new, ModBlocks.DRYING_RACK.get()).build(null));
	public static final RegistryObject<TileEntityType<RitualStoneTile>> RITUAL_STONE = TILE_ENTITIES.register("ritual_stone_tile",
			() -> TileEntityType.Builder.create(RitualStoneTile::new, ModBlocks.RITUAL_STONE.get()).build(null));	 
	public static final RegistryObject<TileEntityType<RitualPedestalTile>> RITUAL_PEDESTAL = TILE_ENTITIES.register("ritual_pedestal_tile",
			() -> TileEntityType.Builder.create(RitualPedestalTile::new, ModBlocks.RITUAL_PEDESTAL.get()).build(null));
	public static final RegistryObject<TileEntityType<MortarTile>> MORTAR_TILE = TILE_ENTITIES.register("mortar_tile",
			() -> TileEntityType.Builder.create(MortarTile::new, ModBlocks.STONE_MORTAR.get()).build(null));
	public static final RegistryObject<TileEntityType<ToolTableTile>> TOOL_TABLE = TILE_ENTITIES.register("tool_table",
			() -> TileEntityType.Builder.create(ToolTableTile::new, ModBlocks.TOOL_TABLE.get()).build(null));

}
