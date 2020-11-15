package com.xX_deadbush_Xx.hocus.common.items.wands;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.xX_deadbush_Xx.hocus.common.spell.LightningSpell;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LightningRod extends WandItem<LightningSpell> {
	
	public LightningRod(Properties properties) {
		super(properties);
	}

	private void performAttack(World world, ItemStack stack, PlayerEntity player) {
		Vec3d look = player.getLookVec();
		Vec3d eyes = player.getEyePosition(0);
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
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
		ItemStack wand = player.getHeldItem(hand);
		if(player.areEyesInFluid(FluidTags.WATER) || !this.attemptWandUse(player, wand)) 
			return ActionResult.resultPass(wand);

		performAttack(world, wand, player);
		
		PlayerSpellCapability.getSpellCap(player).setActiveWand(player, wand);
		return ActionResult.resultSuccess(wand);
	}
	
	@Override
	public LightningSpell getSpell(PlayerEntity caster, ItemStack wand, int... args) {
		Vec3d look = caster.getLookVec();
		return new LightningSpell(caster, wand, (int)(look.x*10000), (int)(look.y*10000), (int)(look.z*10000));
	}
	
	@Override
	public int getEnergyPerUse() {
		return 50;
	}

	@Override
	public int getCooldown() {
		return 0;
	}
 }