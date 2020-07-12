package com.xX_deadbush_Xx.witchcraftmod.common.container;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.InfinitiveSlot;
import com.xX_deadbush_Xx.witchcraftmod.api.inventory.SimpleItemHandler;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class BottomLessBagContainer extends Container {

    private SimpleItemHandler simpleItemHandler;

    public BottomLessBagContainer(final int windowId, final PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(windowId, playerInventory);
    }

    public BottomLessBagContainer(int windowId, PlayerInventory playerInventory) {
        super(ModContainers.BOTTOM_LESS_BAG.get(), windowId);
        this.simpleItemHandler = new SimpleItemHandler(1);

        this.addSlot(new InfinitiveSlot(simpleItemHandler,0, 81, 34));

        for (int k = 0; k < 3; ++k) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
            }
        }

        for (int l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if(index < 1) {
                if(!this.mergeItemStack(itemStack, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            }else if(!this.mergeItemStack(itemStack1, 0, 1, false)) {
                return ItemStack.EMPTY;
            }

            if(itemStack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            return ItemStack.EMPTY;
        }
        return itemStack;
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        return super.slotClick(slotId, dragType, clickTypeIn == ClickType.PICKUP_ALL ? ClickType.PICKUP : clickTypeIn, player);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }


}
