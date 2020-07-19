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
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;


public class CrystalRechargerTile extends ContainerTile implements ITickableTileEntity {

    public static final int ENERGY_ADD_PER_TICK = 1;

    private int burnTime;
    private int total;
    private IIntArray data = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return burnTime;
                case 1:
                    return total;
                default:
                    return -1;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    burnTime = value;
                case 1:
                    total = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

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
        return new CrystalRechargerContainer(p_createMenu_1_, p_createMenu_2_, this, data);
    }

    @Override
    public void tick() {
        if (this.isBurning()) {
            this.burnTime -= 1;
        }

        if (!this.world.isRemote) {
            ItemStack fuel = this.inventory.getStackInSlot(0); //Fuel
            ItemStack crystalStack = this.inventory.getStackInSlot(1); //Crystal
            if (!fuel.isEmpty() && !crystalStack.isEmpty()) {
                if (!this.isBurning()) {
                    this.burnTime = this.getBurnTimeForStack(fuel);
                    this.total = this.burnTime;
                    fuel.shrink(1);
                }
            }
            if (this.isBurning() && this.canCharge(crystalStack)) {
                EnergyCrystal.addStoredEnergy(crystalStack, ENERGY_ADD_PER_TICK);
            }

        }

    }

    protected boolean canCharge(ItemStack stack) {
        return EnergyCrystal.getEnergyStored(stack) < EnergyCrystal.getMaxEnergy(stack);
    }

    public boolean isBurning() {
        return this.burnTime > 0;
    }

    public int getBurnTimeForStack(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel);
        }
    }

    public IIntArray getData() {
        return data;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, items);
        this.burnTime = compound.getInt("BurnTime");
        this.total = this.getBurnTimeForStack(items.get(0));
        this.inventory.setNonNullList(items);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.remove("BurnTime");
        compound.remove("Items");
        compound.putInt("BurnTime", this.burnTime);
        ItemStackHelper.saveAllItems(compound, this.inventory.toNonNullList());
        return compound;
    }
}
