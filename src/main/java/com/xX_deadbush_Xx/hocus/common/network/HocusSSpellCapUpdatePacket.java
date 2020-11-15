package com.xX_deadbush_Xx.hocus.common.network;

import java.util.UUID;
import java.util.function.Supplier;

import com.xX_deadbush_Xx.hocus.api.spell.SpellCast;
import com.xX_deadbush_Xx.hocus.common.register.SpellRegistry;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class HocusSSpellCapUpdatePacket {

	private SpellCast spell;
	private UUID playerID;

	public HocusSSpellCapUpdatePacket(PlayerEntity player, PlayerSpellCapability cap) {
		this.spell = cap.getSpell();
		this.playerID = player.getUniqueID();
	}
	
	public HocusSSpellCapUpdatePacket(SpellCast spell, UUID playerID) {
		this.spell = spell;
		this.playerID = playerID;
	}

	public static void encode(HocusSSpellCapUpdatePacket msg, PacketBuffer buf) {
		buf.writeInt(SpellRegistry.getID(msg.spell));
		buf.writeUniqueId(msg.playerID);
		msg.spell.writeBuffer(buf);
	}

	public static HocusSSpellCapUpdatePacket decode(PacketBuffer buf) {
		int spellID = buf.readInt();
		UUID playerID = buf.readUniqueId();
		SpellCast spell = SpellRegistry.create(spellID, 0);
		spell.readBuffer(buf);
		
		return new HocusSSpellCapUpdatePacket(spell, playerID);
	}

	public static void handle(HocusSSpellCapUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
		NetworkEvent.Context context = ctx.get();
		context.enqueueWork(new Runnable() {
			
			@SuppressWarnings("resource")
			@Override
			public void run() {
				PlayerEntity player = Minecraft.getInstance().world.getPlayerByUuid(msg.playerID);
				PlayerSpellCapability cap = PlayerSpellCapability.getSpellCap(player);
				cap.setActiveWand(player.getHeldItemMainhand(), msg.spell);
			}
		});
		
		context.setPacketHandled(true);
	}
}
