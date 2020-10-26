package com.xX_deadbush_Xx.hocus.common.items.wands;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.hocus.api.util.ModMathHelper;
import com.xX_deadbush_Xx.hocus.api.util.ModNBTUtil;
import com.xX_deadbush_Xx.hocus.client.renderers.wands.ManawaveSpellRenderer;
import com.xX_deadbush_Xx.hocus.client.renderers.wands.SpellRenderer;
import com.xX_deadbush_Xx.hocus.common.network.HocusPacketHandler;
import com.xX_deadbush_Xx.hocus.common.network.HocusSBlockBreakPacket;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceContext.BlockMode;
import net.minecraft.util.math.RayTraceContext.FluidMode;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.LogicalSide;

public class StaffOfDestruction extends ContinuousWandItem {

	private static final int RANGE_SQUARED = 16 * 16;

	public StaffOfDestruction(Properties properties) {
		super(properties);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {

		ItemStack wand = player.getHeldItem(hand);
		CompoundNBT nbt = wand.getOrCreateTag();

		if (player.isSneaking()) {
			boolean mode = ModNBTUtil.toggleShiftMode(nbt);
			player.sendStatusMessage(new StringTextComponent("Mode set to: " + (mode ? "manual" : "autobreak")).applyTextStyle(TextFormatting.AQUA), true);
			return ActionResult.resultSuccess(wand);
		}
		
		BlockPos target;

		if (ModNBTUtil.getShiftMode(nbt)) {	
			return ActionResult.resultPass(wand);
		} else {
			BlockRayTraceResult result = world.rayTraceBlocks(new RayTraceContext(
					player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(MathHelper.sqrt(RANGE_SQUARED))),
					BlockMode.COLLIDER, FluidMode.NONE, player));
			

			if (!nbt.contains("target")) {
				if (result.getType() == BlockRayTraceResult.Type.MISS) {
					return ActionResult.resultPass(wand);
				}

				target = result.getPos();
				nbt.put("target", NBTUtil.writeBlockPos(target));
			} else {
				if (result.getType() == BlockRayTraceResult.Type.MISS) {
					stopUse(wand, player);
					return ActionResult.resultSuccess(wand);
				}

				target = NBTUtil.readBlockPos(nbt.getCompound("target"));
			}
		}

		if (target.distanceSq(player.getPosition()) > RANGE_SQUARED)
			return ActionResult.resultPass(wand);
		if (this.attemptWandUse(player, wand)) {
			PlayerSpellCapability.getSpellCap(player).setActiveWand(wand, target.getX(), target.getY(), target.getZ());
			nbt.putFloat("progress", 0.0F);
		}

		return ActionResult.resultSuccess(wand);
	}
	
	@SuppressWarnings("resource")
	@Nullable
	private BlockPos findNextToBreak(ItemStack wand, World world, PlayerEntity player) {
		if(Minecraft.getInstance().objectMouseOver instanceof BlockRayTraceResult) {
			BlockRayTraceResult result = (BlockRayTraceResult)Minecraft.getInstance().objectMouseOver;
			if(result.getType() == BlockRayTraceResult.Type.BLOCK) {
				if(canBreak(world, result.getPos(), world.getBlockState(result.getPos()))) {
					return result.getPos();
				}
			}
		}
		
		Vec3d start = player.getEyePosition(1);
		Vec3d end = start.add(player.getLookVec().scale(6));
		Vec3d diff = end.subtract(start);
		float yaw = 45 - Math.abs(player.getRotationYawHead()) % 90;
		float pitch = 90 - Math.abs(player.getPitch(1));
		int signX = ModMathHelper.sign(diff.x);
		int signZ = ModMathHelper.sign(diff.z);

		if(pitch > 35) {
			if(player.getRotationYawHead() % 180 > 45 && player.getRotationYawHead() % 180 < 135) {
				start = start.add(0, 1.5, signZ*yaw/30);
				end = end.add(0, -2.5, -signZ*yaw/30);
			} else {
				start = start.add(signX*yaw/30, 1.5, 0);
				end = end.add(-signX*yaw/30, -2.5, 0);
			}
		} 

		end = end.add(signX*pitch/45, 0, signZ*pitch/45);
		diff = end.subtract(start);
		int signY = ModMathHelper.sign(diff.y);
		signX = ModMathHelper.sign(diff.x);
		signZ = ModMathHelper.sign(diff.z);
		
		for(int y = (int)Math.round(start.y); y != (int)Math.round(end.y); y += signY) {
			for(int x = (int)Math.round(start.x); x != (int)Math.round(end.x); x += signX) {
				for(int z = (int)Math.round(start.z); z != (int)Math.round(end.z); z += signZ) {
					BlockPos pos = new BlockPos(x, y, z);
		 			BlockState state = world.getBlockState(pos);
					if(canBreak(world, pos, state))
						return pos;
				}
			}
		}
		
		return null;
	}
	
	private static boolean canBreak(World world, BlockPos pos, BlockState state) {
		return !state.isAir(world, pos) && !(state.getBlock() instanceof FlowingFluidBlock) 
				&& state.getBlockHardness(world, pos) != -1 
				&& world.getBlockState(pos).getBlockHardness(world, pos) <= 2.0F
				&& !state.hasTileEntity();
	}
	
	@Override
	public boolean effectTick(ItemStack stack, PlayerEntity player, LogicalSide side) {
		CompoundNBT nbt = stack.getTag();
		World world = player.world;
		BlockPos target;
		
		if (ModNBTUtil.getShiftMode(nbt) && side == LogicalSide.SERVER) {
			target = findNextToBreak(stack, world, player);
			if(target == null) 
				return false;
			
			PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
			cap.args = new int[] {target.getX(), target.getY(), target.getZ()};
			cap.sendToClient(player, world);
			
			destroyAndUpdateClient(world, target, player, false);
			return true;
	
		} else if(!nbt.contains("target")) {
			return false;
			
		} else {
			target = NBTUtil.readBlockPos(nbt.getCompound("target"));
		}

		if (nbt.contains("progress")) {
			float progress = nbt.getFloat("progress") + 1 / world.getBlockState(target).getBlockHardness(player.world, target) / 2;

			if (progress > 1.0F) {
				destroyAndUpdateClient(world, target, player, true);
				stopUse(stack, player);
			} else {
				nbt.putFloat("progress", progress);
				if(side == LogicalSide.SERVER)
					HocusPacketHandler.sendToNearby(world, player, new HocusSBlockBreakPacket(target, progress, player));
			}

			return true;
		}

		return false;
	}

	@Override
	protected boolean canUseWand(ItemStack wand, PlayerEntity player, LogicalSide side) {
		CompoundNBT nbt = wand.getOrCreateTag();
		if (nbt.contains("target")) {
			double dist = NBTUtil.readBlockPos(nbt.getCompound("target")).distanceSq(player.getPosition());
			return dist <= RANGE_SQUARED && Minecraft.getInstance().objectMouseOver != null;
		}

		else return true;
	}
	
	private static void destroyAndUpdateClient(World world, BlockPos target, PlayerEntity player, boolean destroyTiles) {
		BlockState blockstate = world.getBlockState(target);
		boolean tile = blockstate.hasTileEntity();
		if(!destroyTiles && tile) return;
		
		IFluidState ifluidstate = world.getFluidState(target);
		world.playEvent(2001, target, Block.getStateId(blockstate));
        Block.spawnDrops(blockstate, world, target, tile ? world.getTileEntity(target) : null, player, ItemStack.EMPTY);
        world.setBlockState(target, ifluidstate.getBlockState(), 2);
	}
	
	@Override
	public int getEnergyPerUse() {
		return 3;
	}

	@Override
	public int getCooldown() {
		return 0;
	}

	@Override
	public SpellRenderer getSpellRenderer() {
		return ManawaveSpellRenderer.INSTANCE;
	}

	@Override
	public int getTickInterval() {
		return 1;
	}

	@Override
	public int getManaCostPerTick() {
		return 3;
	}
}