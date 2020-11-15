package com.xX_deadbush_Xx.hocus.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SixWayBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

//vanilla copy pasta
public class IvyBlock extends Block {

	public static final BooleanProperty UP = SixWayBlock.UP;
	public static final BooleanProperty NORTH = SixWayBlock.NORTH;
	public static final BooleanProperty EAST = SixWayBlock.EAST;
	public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
	public static final BooleanProperty WEST = SixWayBlock.WEST;
	public static final BooleanProperty DOWN = SixWayBlock.DOWN;

	protected static final VoxelShape UP_AABB = Block.makeCuboidShape(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 1.0D, 16.0D, 16.0D);
	protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(15.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 1.0D);
	protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0.0D, 0.0D, 15.0D, 16.0D, 16.0D, 16.0D);
	protected static final VoxelShape DOWN_AABB = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);
	
	private static final Direction[] ATTACHDIRECTIONS = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.DOWN};
	
	public IvyBlock(Properties properties) {
		super(properties);
	      this.setDefaultState(this.stateContainer.getBaseState().with(UP, Boolean.valueOf(false)).with(DOWN, Boolean.valueOf(false)).with(NORTH, Boolean.valueOf(false)).with(EAST, Boolean.valueOf(false)).with(SOUTH, Boolean.valueOf(false)).with(WEST, Boolean.valueOf(false)));
	}

	@Override
	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity && entity.getType() != EntityType.BEE) {
			((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.POISON, 60));
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape voxelshape = VoxelShapes.empty();
		if (state.get(DOWN)) {
			voxelshape = VoxelShapes.or(voxelshape, DOWN_AABB);
		}
		
		if (state.get(UP)) {
			voxelshape = VoxelShapes.or(voxelshape, UP_AABB);
		}

		if (state.get(NORTH)) {
			voxelshape = VoxelShapes.or(voxelshape, NORTH_AABB);
		}

		if (state.get(EAST)) {
			voxelshape = VoxelShapes.or(voxelshape, EAST_AABB);
		}

		if (state.get(SOUTH)) {
			voxelshape = VoxelShapes.or(voxelshape, SOUTH_AABB);
		}

		if (state.get(WEST)) {
			voxelshape = VoxelShapes.or(voxelshape, WEST_AABB);
		}

		return voxelshape;
	}

	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return this.isAttached(this.getUpdatedBlockstate(state, worldIn, pos));
	}

	private boolean isAttached(BlockState state) {
		return this.countBlocksVineIsAttachedTo(state) > 0;
	}

	private int countBlocksVineIsAttachedTo(BlockState state) {
		return (int) SixWayBlock.FACING_TO_PROPERTY_MAP.values().stream().filter(p -> state.get(p)).count();
	}

	private boolean canAttachToSide(IBlockReader blockReader, BlockPos pos, Direction directionIn) {
		BlockPos blockpos = pos.offset(directionIn);
		if (canAttachTo(blockReader, blockpos, directionIn)) {
			return true;
		} else if (directionIn.getAxis() == Direction.Axis.Y) {
			return false;
		} else {
			BooleanProperty booleanproperty = SixWayBlock.FACING_TO_PROPERTY_MAP.get(directionIn);
			BlockState blockstate = blockReader.getBlockState(pos.up());
			return blockstate.getBlock() == this && blockstate.get(booleanproperty);
		}
	}

	public static boolean canAttachTo(IBlockReader blockReader, BlockPos pos, Direction neighborPos) {
		BlockState blockstate = blockReader.getBlockState(pos);
		return Block.doesSideFillSquare(blockstate.getCollisionShape(blockReader, pos), neighborPos.getOpposite());
	}

	private BlockState getUpdatedBlockstate(BlockState state, IBlockReader blockReader, BlockPos pos) {
		BlockPos blockpos = pos.up();
		if (state.get(UP)) {
			state = state.with(UP, Boolean.valueOf(canAttachTo(blockReader, blockpos, Direction.DOWN)));
		}

		BlockState blockstate = null;

		for (Direction direction : ATTACHDIRECTIONS) {
			BooleanProperty booleanproperty = getPropertyFor(direction);
			if (state.get(booleanproperty)) {
				boolean flag = this.canAttachToSide(blockReader, pos, direction);
				if (!flag) {
					if (blockstate == null) {
						blockstate = blockReader.getBlockState(blockpos);
					}

					flag = blockstate.getBlock() == this && blockstate.get(booleanproperty);
				}

				state = state.with(booleanproperty, Boolean.valueOf(flag));
			}
		}

		return state;
	}

	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		BlockState blockstate = this.getUpdatedBlockstate(stateIn, worldIn, currentPos);
		return !this.isAttached(blockstate) ? Blocks.AIR.getDefaultState() : blockstate;
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		BlockState newState = this.getUpdatedBlockstate(state, worldIn, pos);
		if (newState != state) {
			if (this.isAttached(newState)) {
				worldIn.setBlockState(pos, newState, 2);
			} else {
				spawnDrops(state, worldIn, pos);
				worldIn.removeBlock(pos, false);
			}
			
			return;

		}
		if(!state.get(UP) && !state.get(NORTH) && !state.get(EAST) && !state.get(SOUTH) && !state.get(WEST)) return;
		if (worldIn.rand.nextInt(4) == 0 && worldIn.isAreaLoaded(pos, 4)) { //code for growing
			Direction randomDir = Direction.random(rand);
			BlockPos posUp = pos.up();
			
			if (randomDir.getAxis().isHorizontal() && !state.get(getPropertyFor(randomDir))) { //if not own direction and horizontal
				if (this.canSpreadVineSideways(worldIn, pos)) {
					BlockPos randomPos = pos.offset(randomDir);
					BlockState randomState = worldIn.getBlockState(randomPos);
					
					if (randomState.isAir(worldIn, randomPos)) {
						Direction direction3 = randomDir.rotateY();
						Direction direction4 = randomDir.rotateYCCW();
						boolean flag = state.get(getPropertyFor(direction3));
						boolean flag1 = state.get(getPropertyFor(direction4));
						BlockPos randomDiagonal1 = randomPos.offset(direction3);
						BlockPos randomDiagonal2 = randomPos.offset(direction4);
						
						if (flag && canAttachTo(worldIn, randomDiagonal1, direction3)) { //check which side the new vine can attach to
							worldIn.setBlockState(randomPos, this.getDefaultState().with(getPropertyFor(direction3), Boolean.valueOf(true)), 2);
							
						} else if (flag1 && canAttachTo(worldIn, randomDiagonal2, direction4)) {
							worldIn.setBlockState(randomPos, this.getDefaultState().with(getPropertyFor(direction4), Boolean.valueOf(true)), 2);
							
						} else {
							Direction direction1 = randomDir.getOpposite();
							 
							if (flag && worldIn.isAirBlock(randomDiagonal1) && canAttachTo(worldIn, pos.offset(direction3), direction1)) {
								worldIn.setBlockState(randomDiagonal1, this.getDefaultState().with(getPropertyFor(direction1), Boolean.valueOf(true)), 2);
								
							} else if (flag1 && worldIn.isAirBlock(randomDiagonal2) && canAttachTo(worldIn, pos.offset(direction4), direction1)) {
								worldIn.setBlockState(randomDiagonal2, this.getDefaultState().with(getPropertyFor(direction1), Boolean.valueOf(true)), 2);
								
							} else if ((double) worldIn.rand.nextFloat() < 0.05D && canAttachTo(worldIn, randomPos.up(), Direction.UP)) {
								worldIn.setBlockState(randomPos, this.getDefaultState().with(UP, Boolean.valueOf(true)), 2);
							}
						}
					} else if (canAttachTo(worldIn, randomPos, randomDir)) {
						worldIn.setBlockState(pos, state.with(getPropertyFor(randomDir), Boolean.valueOf(true)), 2);
					}

				}
			} else {
				if (randomDir == Direction.UP && pos.getY() < 255) { //grow to the bottom side of block above
					if (this.canAttachToSide(worldIn, pos, randomDir)) {
						worldIn.setBlockState(pos, state.with(UP, Boolean.valueOf(true)), 2);
						return;
					}

					if (worldIn.isAirBlock(posUp)) {
						if (!this.canSpreadVineSideways(worldIn, pos)) {
							return;
						}

						BlockState blockstate4 = state;

						for (Direction direction2 : Direction.Plane.HORIZONTAL) {
							if (rand.nextBoolean() || !canAttachTo(worldIn, posUp.offset(direction2), Direction.UP)) {
								blockstate4 = blockstate4.with(getPropertyFor(direction2), Boolean.valueOf(false));
							}
						}

						if (this.hasAttacheableSide(blockstate4)) {
							worldIn.setBlockState(posUp, blockstate4, 2);
						}

						return;
					}
				}

				if (pos.getY() > 0) { //grow down
					BlockPos posDown = pos.down();
					BlockState stateDown = worldIn.getBlockState(posDown);
					
					if (stateDown.isAir(worldIn, posDown) || stateDown.getBlock() == this) {
						BlockState stateToReplace = stateDown.isAir(worldIn, posDown) ? this.getDefaultState() : stateDown;
						BlockState blockstate3 = this.getNewGrownState(state, stateToReplace, rand);
						if (stateToReplace != blockstate3 && this.hasAttacheableSide(blockstate3)) {
							worldIn.setBlockState(posDown, blockstate3, 2);
						}
					} else if(!state.get(DOWN)){ //non vanilla code
						if(canAttachToSide(worldIn, pos, Direction.DOWN)) {
							worldIn.setBlockState(pos, state.with(DOWN, Boolean.valueOf(true)), 2);
						}
					}
				}
			}
		}
	}
	
	/**
	 * gets a new state that keeps some of the sides of the old state
	 * @param state
	 * @param statetoreplace
	 * @param rand
	 * @return
	 */
	private BlockState getNewGrownState(BlockState state, BlockState statetoreplace, Random rand) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (rand.nextBoolean()) {
				BooleanProperty booleanproperty = getPropertyFor(direction);
				if (state.get(booleanproperty)) {
					statetoreplace = statetoreplace.with(booleanproperty, Boolean.valueOf(true));
				}
			}
		}

		return statetoreplace;
	}

	private boolean hasAttacheableSide(BlockState state) {
		return state.get(DOWN) || state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST);
	}

	private boolean canSpreadVineSideways(IBlockReader world, BlockPos pos) {
		Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
		int j = 5;

		for (BlockPos blockpos : iterable) {
			if (world.getBlockState(blockpos).getBlock() == this) {
				--j;
				if (j <= 0) {
					return false;
				}
			}
		}

		return true;
	}

	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
		BlockState blockstate = useContext.getWorld().getBlockState(useContext.getPos());
		if (blockstate.getBlock() == this) {
			return this.countBlocksVineIsAttachedTo(blockstate) < SixWayBlock.FACING_TO_PROPERTY_MAP.size();
		} else {
			return super.isReplaceable(state, useContext);
		}
	}

	@Nullable
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockState blockstate = context.getWorld().getBlockState(context.getPos());
		boolean flag = blockstate.getBlock() == this;
		BlockState blockstate1 = flag ? blockstate : this.getDefaultState();

		for (Direction direction : context.getNearestLookingDirections()) {
			BooleanProperty booleanproperty = getPropertyFor(direction);
			boolean flag1 = flag && blockstate.get(booleanproperty);
			
			if (!flag1 && this.canAttachToSide(context.getWorld(), context.getPos(), direction)) {
				return blockstate1.with(booleanproperty, Boolean.valueOf(true));
			}
		}

		return flag ? blockstate1 : null;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withRotation(Rotation)} whenever
	 *             possible. Implementing/overriding is fine.
	 */
	public BlockState rotate(BlockState state, Rotation rot) {
		switch (rot) {
		case CLOCKWISE_180:
			return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH))
					.with(WEST, state.get(EAST));
		case COUNTERCLOCKWISE_90:
			return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST))
					.with(WEST, state.get(NORTH));
		case CLOCKWISE_90:
			return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST))
					.with(WEST, state.get(SOUTH));
		default:
			return state;
		}
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 * 
	 * @deprecated call via {@link IBlockState#withMirror(Mirror)} whenever
	 *             possible. Implementing/overriding is fine.
	 */
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		switch (mirrorIn) {
		case LEFT_RIGHT:
			return state.with(NORTH, state.get(SOUTH)).with(SOUTH, state.get(NORTH));
		case FRONT_BACK:
			return state.with(EAST, state.get(WEST)).with(WEST, state.get(EAST));
		default:
			return super.mirror(state, mirrorIn);
		}
	}

	public static BooleanProperty getPropertyFor(Direction side) {
		return SixWayBlock.FACING_TO_PROPERTY_MAP.get(side);
	}

	@Override
	public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos,
			net.minecraft.entity.LivingEntity entity) {
		return true;
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(UP, DOWN, NORTH, EAST, SOUTH, WEST);
	}
}