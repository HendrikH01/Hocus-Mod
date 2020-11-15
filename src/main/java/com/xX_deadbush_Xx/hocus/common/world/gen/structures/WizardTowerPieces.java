package com.xX_deadbush_Xx.hocus.common.world.gen.structures;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.register.ModFeatures;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.EvokerEntity;
import net.minecraft.entity.monster.VindicatorEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;

public class WizardTowerPieces {

	private static final ResourceLocation TOP = new ResourceLocation(Hocus.MOD_ID, "wizard_tower_top");
	private static final ResourceLocation BOTTOM = new ResourceLocation(Hocus.MOD_ID, "wizard_tower_bottom");

	public static void start(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> pieceList, Random random) {
		int x = pos.getX();
		int z = pos.getZ();

		BlockPos rotationOffSet = new BlockPos(0, 0, 0).rotate(rotation);
		BlockPos blockpos = rotationOffSet.add(x, pos.getY(), z);
		pieceList.add(new WizardTowerPieces.Piece(templateManager, TOP, blockpos, rotation));

		rotationOffSet = new BlockPos(0, -10, 0).rotate(rotation);
		blockpos = rotationOffSet.add(x, pos.getY(), z);
		pieceList.add(new WizardTowerPieces.Piece(templateManager, BOTTOM, blockpos, rotation));
	}

	public static class Piece extends TemplateStructurePiece {
		
		private ResourceLocation resourceLocation;
		private Rotation rotation;

		public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
			super(ModFeatures.WTPT, 0);
			
			this.resourceLocation = resourceLocationIn;
			this.templatePosition = pos.add(0, 1, 0);
			this.rotation = rotationIn;
			this.setupPiece(templateManagerIn);
		}

		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(ModFeatures.WTPT, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
		}

		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			
			this.setup(template, this.templatePosition, placementsettings);
		}

		@Override
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IWorld worldIn, Random rand, MutableBoundingBox sbb) {
			
			switch (function) {
			case "chest": {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); //placing air forces the game to instantly remove the TE of the structure block.
				generateChest(worldIn, sbb, rand, pos, LootTables.CHESTS_SIMPLE_DUNGEON, null);
				return;
			}

			case "book_chest": {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				generateChest(worldIn, sbb, rand, pos, LootTables.CHESTS_SIMPLE_DUNGEON, null);
				return;
			}

			case "boss_chest": {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				generateChest(worldIn, sbb, rand, pos, LootTables.CHESTS_SIMPLE_DUNGEON, null);
				return;
			}

			case "furnace_chest": {
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				generateChest(worldIn, sbb, rand, pos, LootTables.CHESTS_SIMPLE_DUNGEON, null);
				return;
			}

			case "vindicator": {
				VindicatorEntity entity = EntityType.VINDICATOR.create(worldIn.getWorld());
				entity.enablePersistence();
				entity.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
				entity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
				worldIn.addEntity(entity);
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				return;
			}

			case "archwizard": {
				EvokerEntity entity = EntityType.EVOKER.create(worldIn.getWorld());
				entity.enablePersistence();
				entity.moveToBlockPosAndAngles(pos, 0.0F, 0.0F);
				entity.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(new BlockPos(entity)), SpawnReason.STRUCTURE, (ILivingEntityData) null, (CompoundNBT) null);
				worldIn.addEntity(entity);
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
				return;
			}
			}

		}

		@Override
		public boolean create(IWorld worldIn, ChunkGenerator<?> p_225577_2_, Random randomIn, MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
			this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(0, 0, 0)));

			return super.create(worldIn, p_225577_2_, randomIn, structureBoundingBoxIn, chunkPos);
		}
	}

}