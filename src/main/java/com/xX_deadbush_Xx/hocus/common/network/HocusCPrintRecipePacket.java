package com.xX_deadbush_Xx.hocus.common.network;

import java.util.function.Supplier;

import com.xX_deadbush_Xx.hocus.common.tile.AutoToolTableTile;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class HocusCPrintRecipePacket {

	private boolean pressed;
	private BlockPos pos;

	public HocusCPrintRecipePacket(BlockPos pos, boolean pressed) {
		this.pos = pos;
		this.pressed = pressed;
	}

	public static void encode(HocusCPrintRecipePacket pkt, PacketBuffer buf) {
		buf.writeBlockPos(pkt.pos);
		buf.writeBoolean(pkt.pressed);
	}
	
	public static HocusCPrintRecipePacket decode(PacketBuffer buf) {
		return new HocusCPrintRecipePacket(buf.readBlockPos(), buf.readBoolean());
	}
	
	public static void handle(HocusCPrintRecipePacket pkt, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(()->{
			if(ctx.get().getSender().world.getTileEntity(pkt.pos) instanceof AutoToolTableTile) {
				((AutoToolTableTile)ctx.get().getSender().world.getTileEntity(pkt.pos)).setPrintRecipe(pkt.pressed);
			}
		});
		ctx.get().setPacketHandled(true);
	}
	
}
