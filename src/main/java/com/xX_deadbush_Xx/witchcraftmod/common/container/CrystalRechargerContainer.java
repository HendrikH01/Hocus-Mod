package com.xX_deadbush_Xx.witchcraftmod.common.container;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.ExtraItemSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.util.SimpleIntReferenceHolder;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ContainerHelper;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ItemStackHelper;
import com.xX_deadbush_Xx.witchcraftmod.common.items.EnergyCrystal;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModBlocks;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.CrystalRechargerTile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandlerModifiable;

public class CrystalRechargerContainer extends Container {

    private IItemHandlerModifiable inventory;
    private final IWorldPosCallable canInteractWithCallable;
    private CrystalRechargerTile tile;

    public CrystalRechargerContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, ContainerHelper.getTileEntity(CrystalRechargerTile.class, playerInventory, data));
    }

    public CrystalRechargerContainer(int id, PlayerInventory playerinv, CrystalRechargerTile tile) {
        super(ModContainers.CRYSTAL_RECHARGER.get(), id);
        this.inventory = tile.getItemHandler();
        this.tile = tile;
        this.canInteractWithCallable = IWorldPosCallable.of(tile.getWorld(), tile.getPos());

        //This is to sync the "burnTime" and "total" data with the server
        this.trackInt(new SimpleIntReferenceHolder(() -> tile.burnTime, v -> tile.burnTime = v));
        this.trackInt(new SimpleIntReferenceHolder(() -> tile.total, v -> tile.total = v));

        this.addSlot(new ExtraItemSlot(this.inventory, 0, 60, 26, 64, ItemStackHelper::isFuel));
        this.addSlot(new ExtraItemSlot(this.inventory, 1, 99, 35, 1, (stack) -> stack.getItem() instanceof EnergyCrystal));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerinv, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }
        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerinv, l, 8 + l * 18, 142));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getMaxBurnTime() {
        return this.tile.getMaxBurnTime();
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnTime() {
        return this.tile.getBurnTime();
    }

    public CrystalRechargerTile getTile() {
        return tile;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemstack1, ItemStack.EMPTY);
            } else if (index != 0) {
                if (ItemStackHelper.isFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        this.inventory.setStackInSlot(0, itemstack);
                        return ItemStack.EMPTY;
                    }
                } else if (index < 39 && EnergyCrystal.isStackEnergyCrystal(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
        }

        return itemstack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if (slotId <= 1 && slotId >= 0) {
            Slot slot = this.inventorySlots.get(slotId);
            ItemStack dragged = player.inventory.getItemStack();
            if (slotId == 0) { //Fuel
                if (slot.getHasStack()) {
                    ItemStack stack = slot.getStack();
                    if (clickTypeIn == ClickType.QUICK_MOVE) {
                        ItemStack ex = tile.getItemHandler().extractItem(0, stack.getCount(), false);
                        if (this.mergeItemStack(ex, 2, this.inventorySlots.size(), true)) {
                            return ItemStack.EMPTY;
                        }
                    }
                    if (dragged.isEmpty()) {
                        ItemStack toExtract = tile.getItemHandler().extractItem(0, stack.getCount(), false);
                        if (tile.getItemHandler().getStackInSlot(0).isEmpty())
                            tile.getItemHandler().setStackInSlot(0, ItemStack.EMPTY);
                        player.inventory.setItemStack(toExtract);
                        return toExtract;
                    } else {
                        if (ItemStackHelper.isFuel(dragged)) {
                            if (stack.isItemEqual(dragged)) {
                                ItemStack res = tile.getItemHandler().insertItem(0, dragged, false);
                                player.inventory.setItemStack(res);
                                return res;
                            } else {
                                tile.getItemHandler().setStackInSlot(0, dragged);
                                player.inventory.setItemStack(stack);
                                return stack;
                            }
                        }
                    }
                }
            } else { //Crystal
                if (slot.getHasStack()) {
                    ItemStack stack = slot.getStack();
                    if (clickTypeIn == ClickType.QUICK_MOVE) {
                        ItemStack ex = tile.getItemHandler().extractItem(1, stack.getCount(), false);
                        if (this.mergeItemStack(ex, 2, this.inventorySlots.size(), true)) {
                            if(ex.isEmpty())
                                slot.putStack(ItemStack.EMPTY);
                            return ItemStack.EMPTY;
                        }
                    }
                    if (dragged.isEmpty()) {
                        tile.getItemHandler().setStackInSlot(1, ItemStack.EMPTY);
                        player.inventory.setItemStack(stack);
                        return stack;
                    } else {
                        if (EnergyCrystal.isStackEnergyCrystal(dragged)) {
                            if (!dragged.isItemEqual(stack)) {
                                tile.getItemHandler().setStackInSlot(1, dragged);
                                player.inventory.setItemStack(stack);
                                return stack;
                            }
                        }
                    }
                }
            }
        }

        return super.slotClick(slotId, dragType, clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(canInteractWithCallable, playerIn, ModBlocks.CRYSTAL_RECHARGER.get());
    }
}
