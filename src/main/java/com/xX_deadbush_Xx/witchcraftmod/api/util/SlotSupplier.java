package com.xX_deadbush_Xx.witchcraftmod.api.util;

import net.minecraft.item.ItemStack;

@FunctionalInterface
public interface SlotSupplier {

    boolean invoke(ItemStack stack);

}
