package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.tile.BasicItemHolderTile;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class RitualStoneTile extends BasicItemHolderTile implements ITickableTileEntity {

	public RitualStoneTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn, 1);
	}
	
	public RitualStoneTile() {
		super(ModTileEntities.RITUAL_STONE.get(), 1);
	}

	@Override
	public void tick() {
		
	}	
}
