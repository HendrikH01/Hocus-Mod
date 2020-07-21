package com.xX_deadbush_Xx.witchcraftmod.common.network;

import com.xX_deadbush_Xx.witchcraftmod.WitchcraftMod;
import com.xX_deadbush_Xx.witchcraftmod.common.network.packets.client.WitchcraftParticlePacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class WitchcraftPacketHandler {
	private static final String PROTOCOL_VERSION = "1.0";
	
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
	    new ResourceLocation(WitchcraftMod.MOD_ID, "main"),
	    () -> PROTOCOL_VERSION,
	    PROTOCOL_VERSION::equals,
	    PROTOCOL_VERSION::equals
	);
	
	public static void registerPackets() {
		int id = 0;

		INSTANCE.registerMessage(id++, WitchcraftParticlePacket.class, WitchcraftParticlePacket::encode, WitchcraftParticlePacket::decode, WitchcraftParticlePacket::handle);

	}
	
	public static void sendToNearby(World worldIn, BlockPos pos, Object toSend) {
		sendToWithinRadius(worldIn, pos, 64, toSend);
	}
	
	public static void sendToNearby(World worldIn, Entity e, Object toSend) {
		sendToNearby(worldIn, new BlockPos(e), toSend);
	}
	
	public static void sendToAll(World worldIn, Object toSend) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), toSend);
	}
	
	public static void sendToWithinRadius(World worldIn, BlockPos pos, double radius, Object toSend) {
		if (worldIn instanceof ServerWorld) {
			ServerWorld serverworld = (ServerWorld) worldIn;
			
			serverworld.getChunkProvider().chunkManager.getTrackingPlayers(new ChunkPos(pos), false)
					.filter(p -> p.getDistanceSq(pos.getX(), pos.getY(), pos.getZ()) < radius * radius)
					.forEach(p -> INSTANCE.send(PacketDistributor.PLAYER.with(() -> p), toSend));
		}
	}
	
	public static void sendTo(ServerPlayerEntity playerMP, Object toSend) {
		INSTANCE.sendTo(toSend, playerMP.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
	}
}
