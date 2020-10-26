package com.xX_deadbush_Xx.hocus.common.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class HocusSBlockBreakPacket {
	
	private float progress;
	private int playerID;
	private BlockPos pos;
	
	public HocusSBlockBreakPacket(BlockPos pos, float progress, PlayerEntity player) {
		this.progress = progress;
		this.pos = pos;
		this.playerID = player.getEntityId();
	}
	
	public HocusSBlockBreakPacket(BlockPos pos, float progress, int playerID) {
		this.progress = progress;
		this.pos = pos;
		this.playerID = playerID;
	}

	public static void encode(HocusSBlockBreakPacket msg, PacketBuffer buf) {
		buf.writeFloat(msg.progress);
		buf.writeInt(msg.playerID);
		buf.writeBlockPos(msg.pos);
	}

	public static HocusSBlockBreakPacket decode(PacketBuffer buf) {
		float progress = buf.readFloat();
		int playerID = buf.readInt();
		BlockPos pos = buf.readBlockPos();
		return new HocusSBlockBreakPacket(pos, progress, playerID);
	}

	public static void handle(HocusSBlockBreakPacket msg, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(new Runnable() {

			@SuppressWarnings("resource")
			@Override
			public void run() {
				Minecraft.getInstance().worldRenderer.sendBlockBreakProgress(msg.playerID, msg.pos, (int) (msg.progress * 10) - 1);
			}
		});
		
		context.setPacketHandled(true);
	}
}
