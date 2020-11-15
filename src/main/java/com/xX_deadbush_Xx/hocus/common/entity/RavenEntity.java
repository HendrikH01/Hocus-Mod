package com.xX_deadbush_Xx.hocus.common.entity;

import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Sets;
import com.xX_deadbush_Xx.hocus.common.register.ModItems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class RavenEntity extends ShoulderRidingEntity {

	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
	private int featherDropTimer = 12000;

	public RavenEntity(EntityType<? extends RavenEntity> type, World worldIn) {
		super(type, worldIn);
	      this.moveController = new FlyingMovementController(this, 10, false);
	}

	@Nullable
	public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason,
			@Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
		if (spawnDataIn == null) {
			spawnDataIn = new AgeableEntity.AgeableData();
			((AgeableEntity.AgeableData) spawnDataIn).setCanBabySpawn(false);
		}

		return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
	}

	public void livingTick() {
		super.livingTick();

		if (!this.world.isRemote && this.isAlive() && !this.isChild() && --this.featherDropTimer <= 0) {
			this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
			this.entityDropItem(ModItems.RAVEN_FEATHER.get());
			this.featherDropTimer = this.rand.nextInt(8000) + 8000;
		}

	}

	protected void registerGoals() {
		this.sitGoal = new SitGoal(this);
		this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
		this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(2, this.sitGoal);
		this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
		this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
	}

	protected void registerAttributes() {
		super.registerAttributes();
		this.getAttributes().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		this.getAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue((double) 0.7F);
		this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double) 0.3F);
	}

	protected PathNavigator createNavigator(World worldIn) {
		FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
		flyingpathnavigator.setCanOpenDoors(false);
		flyingpathnavigator.setCanEnterDoors(true);
		return flyingpathnavigator;
	}

	public boolean processInteract(PlayerEntity player, Hand hand) {
		ItemStack held = player.getHeldItem(hand);

		if (held.getItem() instanceof SpawnEggItem) {
			return super.processInteract(player, hand);

		} else if (!this.isTamed() && TAME_ITEMS.contains(held.getItem())) {
			if (!player.abilities.isCreativeMode) {
				held.shrink(1);
			}

			if (!this.world.isRemote) {
				if (this.rand.nextInt(10) == 0
						&& !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
					this.setTamedBy(player);
					this.world.setEntityState(this, (byte) 7);
				} else {
					this.world.setEntityState(this, (byte) 6);
				}
			}

			return true;

		} else if (!this.isFlying() && this.isTamed() && this.isOwner(player)) {
			if (!this.world.isRemote) {
				this.sitGoal.setSitting(!this.isSitting());
			}

			return true;
		} else {
			return super.processInteract(player, hand);
		}
	}

	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		if (compound.contains("FeatherDropTimer")) {
			this.featherDropTimer = compound.getInt("FeatherDropTimer");
		}

	}

	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		compound.putInt("FeatherDropTimer", this.featherDropTimer);
	}

	public boolean isBreedingItem(ItemStack stack) {
		return false;
	}

	public boolean onLivingFall(float distance, float damageMultiplier) {
		return false;
	}

	protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
	}

	public boolean canMateWith(AnimalEntity otherAnimal) {
		return false;
	}

	@Nullable
	public AgeableEntity createChild(AgeableEntity ageable) {
		return null;
	}

	public boolean canBeCollidedWith() {
		return true;
	}

	public boolean isFlying() {
		return !this.onGround;
	}

	@Override
	public IPacket<?> createSpawnPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}
}
