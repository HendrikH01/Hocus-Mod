package com.xX_deadbush_Xx.witchcraftmod.common.tile;

import com.xX_deadbush_Xx.witchcraftmod.api.tile.ContainerTile;
import com.xX_deadbush_Xx.witchcraftmod.common.container.CrystalRechargerContainer;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;


public class CrystalRechargerTile extends ContainerTile implements ITickableTileEntity {

    public static final int ENERGY_ADD_PER_TICK = 1;

    private int burnTime;

    public CrystalRechargerTile() {
        super(ModTileEntities.CRYSTAL_RECHARGER_TILE.get(), 2, ItemStack.EMPTY, ItemStack.EMPTY);
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
        boolean flag = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isRemote) {
            ItemStack fuel = this.inventory.getStackInSlot(0); //Fuel
            ItemStack crystalStack = this.inventory.getStackInSlot(1); //Crystal
            if (!fuel.isEmpty() && !crystalStack.isEmpty()) {
                //Charging
                if (!this.isBurning()) {
                    this.burnTime = this.getBurnTime(fuel);
                    if (this.isBurning())
                        flag = true;
                    if (!fuel.isEmpty()) {
                        fuel.shrink(1);
                        if (fuel.isEmpty()) {
                            this.inventory.setStackInSlot(0, ItemStack.EMPTY);
                        }
                    }
                }
                if (this.isBurning() && this.canCharge(crystalStack)) {
                    EnergyCrystal.addStoredEnergy(crystalStack, ENERGY_ADD_PER_TICK);
                }
            }
        }

        if (flag) {
            this.markDirty();
        }
    }

    protected boolean canCharge(ItemStack stack) {
        return EnergyCrystal.getEnergyStored(stack) < EnergyCrystal.getMaxEnergy(stack);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public void setFuel(ItemStack itemStack) {
        this.inventory.setStackInSlot(0, itemStack);
    }

    /*
        This method dont check if the stack is valid for the slot
     */
    public void setCrystal(ItemStack itemStack) {
        this.inventory.setStackInSlot(1, itemStack);
    }

    public int getBurnTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
        }
    }

    public int getBurnTime() {
        return this.burnTime;
    }

    /*
            Calculating the total charge time:
            If the maxEnergy = 1000 and the storedEnergy = 300, so is the difEnergy 700 and the difenergy divided through 20 (a second) make 35. So the crystal need 35 Seconds to charge
            If you want it faster, then increase the 20

         */
    protected int getChargeTime(ItemStack crystal) {
        int energyStored = EnergyCrystal.getEnergyStored(crystal);
        int maxEnergy = EnergyCrystal.getMaxEnergy(crystal);
        int difEnergy = maxEnergy - energyStored;
        return difEnergy / (ENERGY_ADD_PER_TICK * 20);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, items);
        this.burnTime = compound.getInt("BurnTime");
        this.inventory.setNonNullList(items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putInt("BurnTime", this.burnTime);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        return compound;
    }
}
