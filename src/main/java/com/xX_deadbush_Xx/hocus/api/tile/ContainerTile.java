package com.xX_deadbush_Xx.hocus.api.tile;

import javax.annotation.Nullable;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;

public abstract class ContainerTile extends BasicItemHolderTile implements INamedContainerProvider, INameable {
    private ITextComponent customName;

    public ContainerTile(TileEntityType<?> tileEntityTypeIn, int inventorySlots, ItemStack... initialStacks) {
        super(tileEntityTypeIn, inventorySlots, initialStacks);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }

        return compound;
    }

    public ITextComponent getName() {
        return this.customName != null ? this.customName : this.getDefaultName();
    }

    public ITextComponent getDisplayName() {
        return getName();
    }

    @Nullable
    public ITextComponent getCustomName() {
        return this.customName;
    }

    protected abstract ITextComponent getDefaultName();
}
