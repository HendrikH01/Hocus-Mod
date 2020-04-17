package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class DryingRackTile extends TileEntity implements ITickable {
	
	private int ticksUntilDry = 1000;
	private ItemStack itemHolding = ItemStack.EMPTY;
	
	public DryingRackTile(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	public DryingRackTile() {
		this(ModTileEntities.DRYING_RACK.get());
	}
	
	public void swapItems(World worldIn, BlockPos pos, PlayerEntity player) {
		ItemStack items = player.getHeldItemMainhand().copy();
		items.setCount(1);
		
		if(!itemHolding.equals(items)) {
			Item heldItem = player.getHeldItemMainhand().getItem();
			if(!this.itemHolding.getItem().equals(heldItem)) {
				player.getHeldItemMainhand().shrink(1);
				if(this.hasItem()) {
					if(heldItem.equals(Items.AIR)) player.setHeldItem(Hand.MAIN_HAND, this.itemHolding);
					else if(!player.inventory.addItemStackToInventory(this.itemHolding)) { 
						this.spawnItem(worldIn, this.itemHolding);
					}
				}
				itemHolding = items;
			}
		}
	}

	private void spawnItem(World worldIn, ItemStack stack) {
		System.out.println("spawning item " + stack);
        ItemEntity itementity = new ItemEntity(this.world, this.pos.getX(), this.pos.getY() + 0.5F, this.pos.getZ(), stack);
        worldIn.addEntity(itementity);
	}

	private boolean hasItem() {
		return !this.itemHolding.isEmpty() && !this.itemHolding.getItem().equals(Items.AIR);
	}

	@Override
	public void tick() {
		this.ticksUntilDry--;
		
		if (this.ticksUntilDry <= 0) {
			this.craft(this.getRecipe(this.itemHolding));
		}
	}
	
	private void craft(Object recipe) {
		
	}

	private Object getRecipe(ItemStack itemHolding2) {
		return null;
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
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("item", this.itemHolding.write(new CompoundNBT()));
		compound.putInt("tickUntilDry", this.ticksUntilDry);
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		super.read(compound);
		CompoundNBT item = compound.getCompound("item");

		if(item != null && !item.isEmpty()) {
			this.itemHolding = ItemStack.read(item);
		}
		this.ticksUntilDry = compound.getInt("tickUntilDry");
	}
}
