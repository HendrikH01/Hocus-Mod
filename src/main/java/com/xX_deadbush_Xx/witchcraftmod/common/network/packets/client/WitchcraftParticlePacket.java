package com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client;

import java.util.Random;
import java.util.function.Supplier;

import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler;
import com.xX_deadbush_Xx.witchcraftmod.client.effect.ClientParticleHandler.EffectType;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.network.NetworkEvent;

public class WitchcraftParticlePacket {
	
	private float[] args;
	private EffectType type;
	private double x;
	private double y;
	private double z;
	
	public WitchcraftParticlePacket(EffectType type, double x, double y, double z, float... args) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.args = args;
	}

	public static void encode(WitchcraftParticlePacket msg, PacketBuffer buf) {
		buf.writeByte(msg.type.ordinal());
		buf.writeDouble(msg.x);
		buf.writeDouble(msg.y);
		buf.writeDouble(msg.z);
		buf.writeInt(msg.args.length);
		for(float arg : msg.args) {
			buf.writeFloat(arg);
		}
	}

	public static WitchcraftParticlePacket decode(PacketBuffer buf) {
		EffectType type = EffectType.values()[buf.readByte()];
		double x = buf.readDouble();
		double y = buf.readDouble();
		double z = buf.readDouble();
		float[] args = new float[buf.readInt()];
		for(int i = 0; i < args.length; i++) {
			args[i] = buf.readFloat();
		}
		
		return new WitchcraftParticlePacket(type, x, y, z, args);
	}

	public static void handle(WitchcraftParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(new Runnable() {
			private Random rand = new Random();

			@Override
			public void run() {
				World world = Minecraft.getInstance().world;
				ClientParticleHandler.addEffect(msg.type, world, msg.x, msg.y, msg.z, msg.args);
			}
		});
		context.setPacketHandled(true);
	}
}
