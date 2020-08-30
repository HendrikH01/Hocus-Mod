package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.List;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.LogicalSide;

public class MagnetTalisman extends TalismanItem {

	public MagnetTalisman(Properties properties) {
		super(properties);
	}

	@Override
	protected int getTickInterval() {
		return 3;
	}

	@Override
	public int getManaCostPerTick() {
		return 2;
	}

	@Override
	protected boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		if(!player.isSneaking()) {
			List<ItemEntity> items = player.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(player.getPosition().add(-3.5, -2.5, -3.5), player.getPosition().add(3.5, 3, 3.5)));

			if(!items.isEmpty()) {
				Vec3d playerpos = player.getPositionVec().add(0, 0.5, 0);
				for(ItemEntity item : items) {
					Vec3d direction = playerpos.subtract(item.getPositionVec());
					if(direction.length() < 1) direction.normalize();
	
					item.setMotion(direction.scale(0.35));
				}
				return true;
			}
		} 
		return false;
	}

}
