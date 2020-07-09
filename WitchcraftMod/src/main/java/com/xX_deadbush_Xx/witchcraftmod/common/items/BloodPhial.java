package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.List;

import com.xX_deadbush_Xx.witchcraftmod.common.blocks.ChalkBlock;
import com.xX_deadbush_Xx.witchcraftmod.common.register.ModItems;
import com.xX_deadbush_Xx.witchcraftmod.common.tile.MortarTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

public class BloodPhial extends Item {
	public BloodPhial(Properties properties) {
		super(properties);
	}
	
	public void setBloodType(ItemStack stackIn, LivingEntity target) {
		if(stackIn.getItem() == ModItems.EMPTY_PHIAL.get()) {
			String targetname = Registry.ENTITY_TYPE.getKey(target.getType()).getPath();
			CompoundNBT nbt = new CompoundNBT();
			nbt.putString("mobname", targetname);
			stackIn.setTag(nbt);
		}
	}
	
	public static void copySacrificeKnife(ItemStack phial, ItemStack knife) {
		if(phial.getItem() == ModItems.BLOOD_PHIAL.get() && knife.getItem() == ModItems.SACRIFICE_KNIFE.get()) {
			phial.setTag(knife.getTag());
		}
	}
	
	public String readMob(ItemStack stack) {
		if(stack.hasTag()) {
			CompoundNBT tag = stack.getTag();
			if(!tag.isEmpty()) return tag.getString("mobname");
		} return null;
	}

    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    	String mob = readMob(stack);
        if(mob != null && mob != "") tooltip.add(new StringTextComponent("\u00A77Filled with " + mob + " blood"));
    }
 }
