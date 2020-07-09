package com.xX_deadbush_Xx.witchcraftmod.common.items;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.xX_deadbush_Xx.witchcraftmod.api.util.helpers.ModMathHelper;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.ModParticles;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.particles.data.ScaledColoredParticleData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningRod extends WandItem {
	
	public LightningRod(Properties properties) {
		super(properties);
	}

	private void performAttack(World world, ItemStack stack, PlayerEntity player) {
		Vec3d look = player.getLookVec();
		Vec3d eyes = player.getEyePosition(0);
		BlockRayTraceResult raytrace = world.rayTraceBlocks(new RayTraceContext(eyes, look.add(eyes), BlockMode.COLLIDER, FluidMode.NONE, player));
		List<LivingEntity> entities = getEntities(eyes, look,4, world, player);
		for(LivingEntity entity : entities) {
			entity.attackEntityFrom(DamageSource.causeIndirectMagicDamage(player, null), 2.5f);
		}
    }
	
	@Nullable
	protected List<LivingEntity> getEntities(Vec3d pos, Vec3d direction, double range, World world, PlayerEntity player) {
		List<LivingEntity> entities = new ArrayList<>();
		Vec3d vec1 = pos.add(direction.scale(range/5));
		AxisAlignedBB aabb1 = new AxisAlignedBB(vec1.add(range/5, range/5, range/5), vec1.subtract(range/5, range/5, range/5));
		Vec3d vec2 = pos.add(direction.scale(3*range/5));
		AxisAlignedBB aabb2 = new AxisAlignedBB(vec2.add(2*range/5, 2*range/5, 2*range/5), vec2.subtract(2*range/5, 2*range/5, 2*range/5));
		
		Predicate<Entity> filter = entity -> {
			return entity != null && entity.canBeCollidedWith() && entity.isAlive() && !entity.isSpectator() && entity instanceof LivingEntity && entity.getUniqueID() != player.getUniqueID();
		};

		entities.addAll(world.getEntitiesInAABBexcluding(null, aabb1, filter).stream().map(e -> (LivingEntity)e).collect(Collectors.toList()));
		entities.addAll(world.getEntitiesInAABBexcluding(null, aabb2, filter).stream().map(e -> (LivingEntity)e).collect(Collectors.toList()));

		return entities;
	}

	@Override
	protected ActionResult<ItemStack> onWandUse(World worldIn, PlayerEntity player, Hand handIn, ItemStack wand) {
		if(player.areEyesInFluid(FluidTags.WATER)) return ActionResult.resultPass(wand);

		performAttack(worldIn, wand, player);
		if(worldIn.isRemote) {
			Vec3d look = player.getLookVec();
			Vec3d eyes = player.getPositionVec().add(0, player.getEyeHeight(), 0);
			Vec3d startpos = handIn == Hand.MAIN_HAND ? eyes.add(ModMathHelper.rotateY(look.mul(0.3, 0.3, 0.3), -20)) : eyes.add(ModMathHelper.rotateY(look.mul(0.3, 0.3, 0.3), 20));
			worldIn.addParticle(new ScaledColoredParticleData(ModParticles.LIGHTNING, true, 0xAFC6FF, 1), startpos.x, startpos.y- 0.3, startpos.z, look.x, look.y, look.z);
 		}
		return ActionResult.resultSuccess(wand);
	}

	@Override
	public int getEnergyPerUse(ItemStack wand) {
		return 50;
	}

	@Override
	public int getCooldown() {
		return 0;
	}
 }