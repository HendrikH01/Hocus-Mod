package com.xX_deadbush_Xx.hocus.common.tile;

import com.xX_deadbush_Xx.hocus.common.register.ModTileEntities;
import com.xX_deadbush_Xx.hocus.common.world.data.TileEntityManaStorage;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;


public class CreativeManaSourceTile extends TileEntity {

public CreativeManaSourceTile() {
        super(ModTileEntities.CREATIVE_MANA_SOURCE.get());
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	if(cap.equals(TileEntityManaStorage.getCap())) {
    		return TileEntityManaStorage.getCap().orEmpty(cap, LazyOptional.of(() -> new TileEntityManaStorage(10000, 10000, 10000, 10000)));
    	}
    	
    	return super.getCapability(cap, side);
    }
}
