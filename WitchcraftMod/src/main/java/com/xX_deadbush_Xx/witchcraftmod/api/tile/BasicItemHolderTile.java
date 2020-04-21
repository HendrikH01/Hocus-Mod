package com.xX_deadbush_Xx.witchcraftmod.api.tile;

import java.util.Arrays;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BasicItemHolderTile extends TileEntity implements IInventory {
	protected ItemStack[] inventory;
	protected final int size;
	
	public BasicItemHolderTile(TileEntityType<?> tileEntityTypeIn, int inventorySlots, ItemStack... initialStacks) {
		super(tileEntityTypeIn);
		this.size = inventorySlots;
		this.inventory = new ItemStack[this.size];
		this.clear();
		for(int i = 0; i < initialStacks.length; i++) {
			this.inventory[i] = initialStacks[i];
		}
	}
	
	@Nullable
	@Override
	 public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT comp = new CompoundNBT();
		write(comp);
		return new SUpdateTileEntityPacket(this.pos, 42, comp); //42 is an arbitrary number which wont be used by forge. It is only used for vanilla TEs
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
	
	//INVENTORY
	
	@Override
	public void clear() {
		Arrays.fill(inventory, ItemStack.EMPTY);
	}

	@Override
	public int getSizeInventory() {
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack stack : this.inventory ){
			if(!stack.equals(ItemStack.EMPTY)) return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		ItemStack stack = this.inventory[index];
		ItemStack stack2 = stack.copy();
		if(stack.getCount() > amount) {
			stack2.setCount(amount);
			this.inventory[index].shrink(amount);
		} else {
			this.inventory[index] = ItemStack.EMPTY;
		}
		this.markDirty();
		System.out.println(stack2);
		return stack2;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = this.inventory[index].copy();
		this.inventory[index] = ItemStack.EMPTY;
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		this.inventory[index] =  stack;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return false;
	}
	
	@Override
	public void markDirty() {
		this.world.markBlockRangeForRenderUpdate(this.pos, this.getBlockState(), this.getBlockState());
		this.world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 3);
		super.markDirty();
	}

}
