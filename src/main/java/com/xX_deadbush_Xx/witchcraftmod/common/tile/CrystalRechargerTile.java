package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.tile.ContainerTile;
import com.xX_deadbush_Xx.witchcraftmod.common.container.CrystalRechargerContainer;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.CrystalLinkedManaStorage;
import com.xX_deadbush_Xx.witchcraftmod.common.world.data.TileEntityManaStorage;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;


public class CrystalRechargerTile extends ContainerTile implements ITickableTileEntity {

    private static final int ENERGY_ADD_PER_TICK = 1;
    private static final int CRYSTAL_SLOT = 1;
    private static final int FUEL_SLOT = 0;

    private int burnTime; //How many ticks the fuel has been burning
    private int maxBurnTime; //How many ticks the fuel can burn for

    public CrystalRechargerTile() {
        super(ModTileEntities.CRYSTAL_RECHARGER_TILE.get(), 2, ItemStack.EMPTY, ItemStack.EMPTY);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
    	if(cap.equals(TileEntityManaStorage.getCap()) && EnergyCrystal.isStackEnergyCrystal(this.inventory.getStackInSlot(CRYSTAL_SLOT))) {
    		ItemStack crystal = this.inventory.getStackInSlot(CRYSTAL_SLOT);
    		int energy = EnergyCrystal.getEnergyStored(crystal);
    		int maxenergy = EnergyCrystal.getMaxEnergy(crystal);
    		return TileEntityManaStorage.getCap().orEmpty(cap, LazyOptional.of(() -> new CrystalLinkedManaStorage(crystal, 10, 10)));
    	} else if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
    		 return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> inventory));
    	}
    	
    	return super.getCapability(cap, side);
    }
    
    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("Crystal Recharger"); //TODO: change later
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new CrystalRechargerContainer(p_createMenu_1_, p_createMenu_2_, this);
    }

    @Override
    public void tick() {
        ItemStack crystal = this.inventory.getStackInSlot(CRYSTAL_SLOT);
        boolean canCharge = this.canCharge(crystal);
        
        if (this.burnTime > 0) {
            this.burnTime--;
            if(canCharge) {
                EnergyCrystal.addStoredEnergy(crystal, ENERGY_ADD_PER_TICK);
            }
        }
        
        if(this.burnTime <= 0 && canCharge) {
            ItemStack fuel = this.inventory.getStackInSlot(FUEL_SLOT);
            if(!fuel.isEmpty()) {
                burnTime = ForgeHooks.getBurnTime(fuel);
                maxBurnTime = burnTime;
                fuel.shrink(1);
            }
        }
    }
    
    public int getBurnTime() {
    	return this.burnTime;
    }
     
	public int getMaxBurnTime() {
		return this.maxBurnTime;
	}

    protected boolean canCharge(ItemStack stack) {
    	if (!EnergyCrystal.isStackEnergyCrystal(stack)) return false;
        return EnergyCrystal.getEnergyStored(stack) < EnergyCrystal.getMaxEnergy(stack);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.burnTime = compound.getInt("burn_time");
        this.maxBurnTime = compound.getInt("max_burn_time");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("burn_time", this.burnTime);
        compound.putInt("max_burn_time", this.maxBurnTime);
        return compound;
    }
}
