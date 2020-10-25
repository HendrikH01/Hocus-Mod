package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import javax.annotation.Nonnull;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.EnergyRelayBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.TileEntityManaStorage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;


public class EnergyRelayTile extends TileEntity implements ITickableTileEntity {
	public static final double RANGE = 10.0;
	public static final double CAPACITY = 20.0;
	public final TileEntityManaStorage manastorage = new TileEntityManaStorage(CAPACITY, 20, 20, 0);
	private LazyOptional<BlockPos> targetPos = LazyOptional.empty();
	private int particleTick = 0; //For testing, will be moved to TESR
	
    public EnergyRelayTile() {
        super(ModTileEntities.ENERGY_RELAY_TILE.get());
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	return TileEntityManaStorage.getCap().orEmpty(cap, LazyOptional.of(() -> this.manastorage));
    }
    
    @Override
    public void tick() {
    	boolean active = !this.world.getBlockState(pos).get(BlockStateProperties.POWERED);
 		if (manastorage != null) {
			if(active) extractFromAttached();
			TileEntity targetTile = getTargetTile();
			if (targetTile != null) {
				TileEntityManaStorage targetstorage = targetTile.getCapability(TileEntityManaStorage.getCap(), null).orElse(null);
				if (targetstorage != null) {
					double transferred = 0;
					if(active) transferred = transferEnergyToTarget(targetstorage);
					if (world.isRemote) {
				    	particleTick++;
						BlockPos target = targetTile.getPos();
						double yoffset = targetTile instanceof EnergyRelayTile ? 0.4 : 0.7;
											}
				} 
			} else {
				this.targetPos.invalidate();
			}
		}
    }
    
    private TileEntity getTargetTile() {
		return world.getTileEntity(targetPos.orElse(BlockPos.ZERO));
	}
    
    public boolean attemptLink(BlockPos targetpos) {
    	if(targetpos.equals(pos)) return false;
    	if(!pos.withinDistance(targetpos, RANGE)) return false;
    	
		TileEntity targettile = world.getTileEntity(targetpos);
		
		if (targettile != null) {
			if(targettile instanceof EnergyRelayTile) {
				if(((EnergyRelayTile)targettile).targetPos.orElse(BlockPos.ZERO).equals(pos)) return false;
			}
			this.targetPos = LazyOptional.of(() -> targetpos);
			return true;
		}
		
		return false;
    }
    
    private double transferEnergyToTarget(@Nonnull TileEntityManaStorage target) {
    	if(!target.canReceive()) return 0;
    	double remaining = target.receiveEnergy(manastorage.getEnergy(), false);
    	manastorage.extractEnergy(remaining, false);
    	return remaining;
    }
    
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT comp = new CompoundNBT();
		this.write(comp);
		return new SUpdateTileEntityPacket(this.pos, -999, comp);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
    	super.onDataPacket(net, pkt);
    }
	
	@Override
	public CompoundNBT getUpdateTag() {
	    CompoundNBT tag = new CompoundNBT();
	    this.write(tag);
	    return tag;
	}
    
	private void extractFromAttached() {
		BlockState state = this.world.getBlockState(pos);
		Block block = state.getBlock();
		if (block instanceof EnergyRelayBlock) {
			BlockPos attachedpos = ((EnergyRelayBlock) block).getAttached(pos, state);
			TileEntity tile = world.getTileEntity(attachedpos);
			if (tile != null) {
				LazyOptional<TileEntityManaStorage> other = tile.getCapability(TileEntityManaStorage.getCap(), null);
				other.ifPresent((storage) -> {
					double extracted = storage.extractEnergy(manastorage.getMaxEnergy() - manastorage.getEnergy(), false);
					manastorage.receiveEnergy(extracted, false);
				});
			}
		}
	}
    
    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if(compound.contains("mana"))TileEntityManaStorage.getCap().readNBT(manastorage, null, compound.get("mana"));
        if(!compound.contains("pos")) return;
        targetPos = LazyOptional.of(() -> NBTUtil.readBlockPos(compound.getCompound("pos")));
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("mana", TileEntityManaStorage.getCap().writeNBT(manastorage, null));
        targetPos.ifPresent(pos -> compound.put("pos", NBTUtil.writeBlockPos(pos)));
        return compound;
    }

	public boolean isLinked() {
		return targetPos.isPresent();
	}

	public void unlink() {
		this.targetPos.invalidate();
	}
}
