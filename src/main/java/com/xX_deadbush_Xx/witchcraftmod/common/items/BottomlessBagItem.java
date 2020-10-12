package com.xX_deadbush_Xx.witchcraftmod.common.items;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.witchcraftmod.api.inventory.BottomlessBagInventory;
import com.xX_deadbush_Xx.witchcraftmod.common.container.BottomLessBagContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BottomlessBagItem extends Item {

    public BottomlessBagItem(Properties properties) {
        super(properties);
    }

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		final int selectedSlot = handIn == Hand.MAIN_HAND ? playerIn.inventory.currentItem : -1;
		if (!worldIn.isRemote) {
			NetworkHooks.openGui((ServerPlayerEntity) playerIn,
					new SimpleNamedContainerProvider(
							(id, playerInventory, openPlayer) -> new BottomLessBagContainer(id, playerInventory,
									new BottomlessBagInventory(playerIn.getHeldItem(handIn)), selectedSlot),
							new TranslationTextComponent("bottomless_bag")),
					packetBuffer -> packetBuffer.writeVarInt(selectedSlot));
			return ActionResult.resultSuccess(playerIn.getHeldItemMainhand());
		}
		return ActionResult.resultPass(playerIn.getHeldItemMainhand());
	}

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        if (!stack.hasTag()) {
            return null;
        }
        final CompoundNBT compound = stack.getTag().copy();
        compound.remove("Items");
        if (compound.isEmpty()) {
            return null;
        }
        return compound;
    }
}