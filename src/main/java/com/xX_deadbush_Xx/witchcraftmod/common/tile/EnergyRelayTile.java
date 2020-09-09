package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.EnergyRelayBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.TileEntityManaStorage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;


public class EnergyRelayTile extends TileEntity implements ITickableTileEntity {
	
	TileEntityManaStorage manastorage = new TileEntityManaStorage(20, 20, 20, 20);
	private LazyOptional<BlockPos> targetPos = LazyOptional.empty();
	
    public EnergyRelayTile() {
        super(ModTileEntities.ENERGY_RELAY_TILE.get());
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	return TileEntityManaStorage.getCap().orEmpty(cap, LazyOptional.of(() -> this.manastorage));
    }
    
    @Override
    public void tick() {
 		if (manastorage != null) {
			extractFromAttached();
			TileEntity targetTile = getTargetTile();
			if (targetTile != null) {
				TileEntityManaStorage targetstorage = targetTile.getCapability(TileEntityManaStorage.Capability.CRYSTAL_ENERGY_CAP, null).orElse(null);
				if (targetstorage != null) {
					transferEnergyToTarget(targetstorage);
					if (world.isRemote) {
						Vec3d start = new Vec3d(pos);
						Vec3d diff = new Vec3d(targetTile.getPos()).subtract(start);
						double l = diff.length();
						for (int i = 0; i < 10; i++) {
							Vec3d p = start.add(diff.normalize().scale((double) i / 9 * l)).add(0.5, 0.5, 0.5);
							world.addParticle(ParticleTypes.HAPPY_VILLAGER, p.x, p.y, p.z, 0, 0, 0);
						}

						// TODO MANAWAVE TESR & deal with server client synchronization
					}
				} 
			} else {
				this.targetPos.invalidate();
			}
		}
    }
    
    private TileEntity getTargetTile() {
		return world.getTileEntity(targetPos.orElse(new BlockPos(0, -1, 0)));
	}
    
    public boolean attemptLink(BlockPos targetpos) {
    	if(targetpos.equals(pos)) return false;
    	
		TileEntity tile = world.getTileEntity(targetpos);
		
		if (tile != null) {
			this.targetPos = LazyOptional.of(() -> targetpos);
			return true;
		}
		
		return false;
    }
    
    private void transferEnergyToTarget(@Nonnull TileEntityManaStorage target) {
    	if(!target.canReceive()) return;
    	int remaining = target.receiveEnergy(manastorage.getEnergy(), false);
    	if(remaining != 0) {
    		System.out.println(target.getEnergy() + " " + world.isRemote());
    	}
    	manastorage.extractEnergy(remaining, false);
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
				LazyOptional<TileEntityManaStorage> other = tile.getCapability(TileEntityManaStorage.Capability.CRYSTAL_ENERGY_CAP, null);
				other.ifPresent((storage) -> {
					int extracted = storage.extractEnergy(manastorage.getMaxEnergy() - manastorage.getEnergy(), false);
					if (extracted != 0)
						System.out.println("extract " + extracted);
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
}
