package com.xX_deadbush_Xx.hocus.common.items.wands;

import java.util.List;

import com.xX_deadbush_Xx.hocus.common.tile.EnergyRelayTile;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class LinkingWand extends Item {
	public LinkingWand(Properties properties) {
		super(properties);
	}
	
	@SuppressWarnings("resource")
	@Override
	public ActionResultType onItemUse(ItemUseContext context) {
		BlockPos pos = context.getPos();
		CompoundNBT nbt = context.getItem().getOrCreateTag();
		//First position saved as "first"
		if (nbt.contains("first")) {
			BlockPos pos2 = NBTUtil.readBlockPos(nbt.getCompound("first"));
			EnergyRelayTile tile = (EnergyRelayTile)context.getWorld().getTileEntity(pos2);
			if (tile != null && tile.attemptLink(pos)) {
				if (context.getWorld().isRemote)
					context.getPlayer().sendStatusMessage(new StringTextComponent(String.format("\u00A77" + "Linked %d, %d, %d with %d, %d, %d.", pos.getX(), pos.getY(), pos.getZ(), pos2.getX(), pos2.getY(), pos2.getZ())), true);
			} else {
				if (context.getWorld().isRemote)
					context.getPlayer().sendStatusMessage(new StringTextComponent(String.format("\u00A77" + "Failed to link %d, %d, %d with %d, %d, %d.", pos.getX(), pos.getY(), pos.getZ(), pos2.getX(), pos2.getY(), pos2.getZ())), true);

			}

			nbt.remove("first");
		} else if (context.getWorld().getTileEntity(pos) instanceof EnergyRelayTile) {
			EnergyRelayTile tile = (EnergyRelayTile)context.getWorld().getTileEntity(pos);
			
			if(tile.isLinked()) {
				tile.unlink();
			} else {
				nbt.put("first", NBTUtil.writeBlockPos(pos));
				if (context.getWorld().isRemote)
					context.getPlayer().sendStatusMessage(new StringTextComponent(String.format("\u00A77" + "First position: %d, %d, %d", pos.getX(), pos.getY(), pos.getZ())), true);
			}
			
			return ActionResultType.SUCCESS;
		}
		
		return ActionResultType.PASS;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
		if(stack.getOrCreateTag().contains("first")) {
			BlockPos pos = NBTUtil.readBlockPos(stack.getTag().getCompound("first"));
			tooltip.add(new StringTextComponent(String.format("\u00A7" + "First position: %d, %d, %d", pos.getX(), pos.getY(), pos.getZ())));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
