package com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client;

import java.util.function.Supplier;

import com.xX_deadbush_Xx.witchcraftmod.common.world.data.PlayerCrystalEnergyProvider;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class CrystalEnergyUpdate {

	private int energy;

	public CrystalEnergyUpdate(int energy) {
		this.energy = energy;
	}

	public static void encode(CrystalEnergyUpdate msg, PacketBuffer buf) {
		buf.writeInt(msg.energy);
	}

	public static CrystalEnergyUpdate decode(PacketBuffer buf) {
		int energy = buf.readInt();
		return new CrystalEnergyUpdate(energy);
	}

	public static void handle(CrystalEnergyUpdate msg, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(new Runnable() {
			@Override
			public void run() {
				PlayerCrystalEnergyProvider.getClientCapability().ifPresent(storage -> {
					storage.setEnergy(msg.energy);
				});
			}
		});
		context.setPacketHandled(true);
	}
}
