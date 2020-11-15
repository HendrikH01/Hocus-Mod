package com.xX_deadbush_Xx.hocus.common.entity;

import com.xX_deadbush_Xx.hocus.common.register.ModEntities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.DamagingProjectileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.network.NetworkHooks;

public class LightningBallEntity extends DamagingProjectileEntity {
	private float strength;

	public LightningBallEntity(EntityType<? extends DamagingProjectileEntity> type, World world) {
		super(type, world);
		this.strength = 1;
	}
	
	public LightningBallEntity(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ, float strength) {
		super(ModEntities.LIGHTING_BALL.get(), x, y, z, accelX, accelY, accelZ, worldIn);
		this.strength = strength;
	}

	/**
	 * Called when this EntityFireball hits a block or entity.
	 */
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
		if (!this.world.isRemote) {
			if (result.getType() == RayTraceResult.Type.ENTITY) {
				Entity entity = ((EntityRayTraceResult) result).getEntity();
				entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, this.strength * 3);
				this.applyEnchantments(this.shootingEntity, entity);
			}

			boolean mobGriefing = ForgeEventFactory.getMobGriefingEvent(this.world, this.shootingEntity);
			this.world.createExplosion((Entity) null, this.getPosX(), this.getPosY(), this.getPosZ(), (float)this.strength, mobGriefing, mobGriefing ? Explosion.Mode.DESTROY : Explosion.Mode.NONE);
			this.remove();
		}
	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putFloat("Strength", this.strength);
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("Strength", 99)) {
			this.strength = compound.getInt("Strength");
		}

	}

	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}

	protected boolean isFireballFiery() {
		return false;
	}
}
