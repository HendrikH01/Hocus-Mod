package com.xX_deadbush_Xx.witchcraftmod.common.container;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.BottomlessBagInventory;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.BottomlessBagSlot;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class BottomLessBagContainer extends Container {

    private BottomlessBagInventory bagInventory;

    public BottomLessBagContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(windowId, playerInventory, new BottomlessBagInventory(playerInventory.getCurrentItem()), packetBuffer.readVarInt());
    }

    public BottomLessBagContainer(int windowId, PlayerInventory playerInventory, BottomlessBagInventory inventory, int selectedSlot) {
        super(ModContainers.BOTTOM_LESS_BAG.get(), windowId);
        this.bagInventory = inventory;

        this.addSlot(new BottomlessBagSlot(bagInventory, 0, 81, 35));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    public ItemStack getStoredItem() {
        return bagInventory.getStack();
    }

    public int getAmount() {
        return bagInventory.getActuallyStack();
    }

    @Override
    public void detectAndSendChanges() {
    } // for now I will leave this empty because the super method doesnt work here and I dont know if we even need this method

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);

        ItemStack itemstack = slot.getStack();
        if (!itemstack.isEmpty()) {
            if (bagInventory.getStack().isEmpty()) {
                bagInventory.insertItem(0, itemstack, false);
                slot.putStack(ItemStack.EMPTY);
                this.inventorySlots.get(0).onSlotChanged();
                return ItemStack.EMPTY;
            } else if (itemstack.isItemEqual(bagInventory.getStack())) {
                bagInventory.insertItem(0, itemstack, false);
                itemstack.setCount(0);
                slot.putStack(ItemStack.EMPTY);
                return ItemStack.EMPTY;
            }
        }

        return ItemStack.EMPTY;
    }


    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        this.bagInventory.saveData();
        super.onContainerClosed(playerIn);
    }


    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
        bagInventory.insertItem(0, stack, false);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) {
        if (slotId == 0) {
            if(clickType == ClickType.QUICK_MOVE) {
                ItemStack ex = bagInventory.extractItem(0, 64, false);
                if(this.mergeItemStack(ex, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                } else {
                    bagInventory.insertItem(0, ex, false);
                    return ItemStack.EMPTY;
                }
            }
            BottomlessBagSlot slot = (BottomlessBagSlot) this.inventorySlots.get(slotId);
            ItemStack dragged = player.inventory.getItemStack();
            if (slot.getHasStack()) {
                if (dragged.isEmpty()) {
                    //EXTRACT A ITEM
                    int amountToExtract = dragType == 0 ? 64 : 32; //Calculating the amount to extract, if the player right click then dragType == 1 and its a right click otherwise a left click
                    ItemStack ex = bagInventory.extractItem(0, amountToExtract, false);
                    player.inventory.setItemStack(ex);
                    return ex.copy();
                } else {
                    //INSERT A ITEM
                    slot.getItemHandler().insertItem(0, dragged, false);
                    player.inventory.setItemStack(ItemStack.EMPTY);
                    return ItemStack.EMPTY;
                }
            } else {
                return super.slotClick(slotId, dragType, clickType, player);
            }
        }
        return super.slotClick(slotId, dragType, clickType, player);
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
