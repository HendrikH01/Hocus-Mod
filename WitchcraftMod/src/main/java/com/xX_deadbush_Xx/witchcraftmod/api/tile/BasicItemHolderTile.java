package com.xX_deadbush_Xx.witchcraftmod.api.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.SimpleItemHandler;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public abstract class BasicItemHolderTile extends TileEntity {
	
	protected SimpleItemHandler inventory;
	private final LazyOptional<IItemHandler> automationItemHandler = LazyOptional.of(() -> inventory);

	public BasicItemHolderTile(TileEntityType<?> tileEntityTypeIn, int inventorySlots, ItemStack... initialStacks) {
		super(tileEntityTypeIn);

		this.inventory  = new SimpleItemHandler(inventorySlots);
		for(int i = 0; i < initialStacks.length; i++) {
			this.inventory.setStackInSlot(i, initialStacks[i]);
		}
	}
	
	public final IItemHandlerModifiable getItemHandler() {
		return this.inventory;
	}
	
	@Nullable
	@Override
	 public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT comp = new CompoundNBT();
		write(comp);
		return new SUpdateTileEntityPacket(this.pos, 69, comp); //nice
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
	    CompoundNBT tag = new CompoundNBT();
	    write(tag);
	    return tag;
	}
		
	@Override
	public void handleUpdateTag(CompoundNBT tag) {
	    this.read(tag);
	}
	
	@Override
	public void read(CompoundNBT nbt) {
		super.read(nbt);
		NonNullList<ItemStack> inv = NonNullList.create();
		ItemStackHelper.loadAllItems(nbt, inv);
		this.inventory.setNonNullList(inv);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		super.write(nbt);
		ItemStackHelper.saveAllItems(nbt, this.inventory.toNonNullList());
		return nbt;
	}
	
	@Override
	public void markDirty() {
		this.world.markBlockRangeForRenderUpdate(this.pos, this.getBlockState(), this.getBlockState());
		this.world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
		super.markDirty();
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side) {
		return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, automationItemHandler);
	}
}
