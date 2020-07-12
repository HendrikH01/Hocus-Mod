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
    public void detectAndSendChanges() {} // for now I will leave this empty because the super method doesnt work here and I dont know if we even need this method

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        final Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            bagInventory.setStackInSlot(0, itemstack1.copy());
            if (index < 1) {
                System.out.println("BLBC_DEBUG_2");

                if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                System.out.println("BLBC_DEBUG_3");
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        System.out.println("BLBC_DEBUG_4");
        return itemstack;
    }

    @Override
    public void putStackInSlot(int slotID, ItemStack stack) {
    	bagInventory.insertItem(0, stack, false);
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickType, PlayerEntity player) { //This is what needs to be worked on next. Determine what items should be given to the player and what should remain in the slot for every clickType. Im not sure what the method is supposed to return.
        Slot slot = this.inventorySlots.get(slotId);
        ItemStack currentStack = slot.getStack();
        
        return ItemStack.EMPTY;
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
