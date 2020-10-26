package com.xX_deadbush_Xx.hocus.common.items.talisman;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.LogicalSide;

public class MagnetTalisman extends TalismanItem {
	
	private static Random rand = new Random();
	
	public MagnetTalisman(Properties properties) {
		super(properties);
	}

	@Override
	public int getTickInterval() {
		return 3;
	}

	@Override
	public int getManaCostPerTick() {
		return 2;
	}

	@Override
	public boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		if(!player.isSneaking()) {
			List<ItemEntity> items = player.world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(player.getPosition().add(-3.5, -2.5, -3.5), player.getPosition().add(3.5, 3, 3.5)));

			if(!items.isEmpty()) {
				Vec3d playerpos = player.getPositionVec().add(0, 0.5, 0);
	
				for(ItemEntity item : items) {
					Vec3d pos = item.getPositionVec();
					Vec3d direction = playerpos.subtract(pos);
					if(direction.length() < 1) direction.normalize();
	
					item.setMotion(direction.scale(0.35));
					
					if(player.world.isRemote) {
						if(item.getAge()%2 == 0)player.world.addParticle(ParticleTypes.ENTITY_EFFECT, pos.x, pos.y, pos.z, (float)(70 + rand.nextInt(110))/255, 70.0f/255, 1.0f);
					}
				}
				return true;
			}
		} 
		return false;
	}

}
