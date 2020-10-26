package com.xX_deadbush_Xx.hocus.common.register;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.tile.CreativeManaSourceTile;
import com.xX_deadbush_Xx.hocus.common.tile.CrystalRechargerTile;
import com.xX_deadbush_Xx.hocus.common.tile.DryingRackTile;
import com.xX_deadbush_Xx.hocus.common.tile.EnergyRelayTile;
import com.xX_deadbush_Xx.hocus.common.tile.MortarTile;
import com.xX_deadbush_Xx.hocus.common.tile.RitualPedestalTile;
import com.xX_deadbush_Xx.hocus.common.tile.RitualStoneTile;
import com.xX_deadbush_Xx.hocus.common.tile.ToolTableTile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Hocus.MOD_ID);

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
    public static final RegistryObject<TileEntityType<CrystalRechargerTile>> CRYSTAL_RECHARGER_TILE = TILE_ENTITIES.register("crystal_recharger_tile",
            () -> TileEntityType.Builder.create(CrystalRechargerTile::new, ModBlocks.CRYSTAL_RECHARGER.get()).build(null));
    public static final RegistryObject<TileEntityType<EnergyRelayTile>> ENERGY_RELAY_TILE = TILE_ENTITIES.register("energy_relay_tile",
            () -> TileEntityType.Builder.create(EnergyRelayTile::new, ModBlocks.ENERGY_RELAY.get()).build(null));
    public static final RegistryObject<TileEntityType<CreativeManaSourceTile>> CREATIVE_MANA_SOURCE = TILE_ENTITIES.register("creative_mana_source",
            () -> TileEntityType.Builder.create(CreativeManaSourceTile::new, ModBlocks.CREATIVE_MANA_SOURCE.get()).build(null));

}
