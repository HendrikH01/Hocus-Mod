package com.xX_deadbush_Xx.hocus.common.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HocusSSpellCapUpdatePacket {

	private int ticks;
	private int[] args;
	private UUID playerID;

	public HocusSSpellCapUpdatePacket(PlayerEntity player, PlayerSpellCapability cap) {
		this.ticks = cap.ticks;
		this.args = cap.args;
		this.playerID = player.getUniqueID();
	}
	
	public HocusSSpellCapUpdatePacket(int ticks, int[] args, UUID playerID) {
		this.ticks = ticks;
		this.args = args;
		this.playerID = playerID;
	}

	public static void encode(HocusSSpellCapUpdatePacket msg, PacketBuffer buf) {
		buf.writeInt(msg.ticks);
		buf.writeVarIntArray(msg.args);
		buf.writeUniqueId(msg.playerID);
	}

	public static HocusSSpellCapUpdatePacket decode(PacketBuffer buf) {
		int ticks = buf.readInt();
		int[] args = buf.readVarIntArray();
		UUID playerID = buf.readUniqueId();
		
		return new HocusSSpellCapUpdatePacket(ticks, args, playerID);
	}

	public static void handle(HocusSSpellCapUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(new Runnable() {
			@SuppressWarnings("resource")
			@Override
			public void run() {
				PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.playerID);
				PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
				cap.setActiveWand(player.getHeldItemMainhand(), msg.args);
				cap.ticks = msg.ticks;
			}
		});
		context.setPacketHandled(true);
	}
}
