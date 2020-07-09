package com.xX_deadbush_Xx.witchcraftmod.api.util.helpers;

import java.util.List;
import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityHelper {
	/**
	 * Taken with permission from Shadows-Of-Fires ClickMachine, lightly modified
	 */
	public static EntityRayTraceResult rayTraceLivingEntities(PlayerEntity player, Vec3d base, Vec3d target, World world) {
		LivingEntity pointedEntity = null;
		EntityRayTraceResult result = null;
		Vec3d vec3d3 = null;
		AxisAlignedBB search = new AxisAlignedBB(base.x, base.y, base.z, target.x, target.y, target.z).grow(0.5, 0.5, 0.5);
		List<Entity> list = world.getEntitiesInAABBexcluding(player, search, entity -> {
					return entity != null && entity.canBeCollidedWith() && entity.isAlive() && !entity.isSpectator() && entity instanceof LivingEntity;
				});
		
		double d2 = 5;

		for (int j = 0; j < list.size(); ++j) {
			LivingEntity entity = (LivingEntity)list.get(j);

			AxisAlignedBB aabb = entity.getBoundingBox().grow(entity.getCollisionBorderSize());
			Optional<Vec3d> raytraceresult = aabb.rayTrace(base, target);

			if (aabb.contains(base)) {
				if (d2 >= 0.0D) {
					pointedEntity = entity;
					vec3d3 = raytraceresult.isPresent() ? base : raytraceresult.get();
					d2 = 0.0D;
				}
			} else if (raytraceresult.isPresent()) {
				Vec3d hitVec = raytraceresult.get();
				double d3 = base.distanceTo(hitVec);

				if (d3 < d2 || d2 == 0.0D) {
					if (entity.getLowestRidingEntity() == player.getLowestRidingEntity() && !entity.canRiderInteract()) {
						if (d2 == 0.0D) {
							pointedEntity = entity;
							vec3d3 = hitVec;
						}
					} else {
						pointedEntity = entity;
						vec3d3 = hitVec;
						d2 = d3;
					}
				}
			}
		}

		if (pointedEntity != null && base.distanceTo(vec3d3) > 5) {
			pointedEntity = null;
			result = new EntityRayTraceResult(null, vec3d3);
		}

		if (pointedEntity != null) {
			result = new EntityRayTraceResult(pointedEntity, vec3d3);
		}

		return result;
	}

}
