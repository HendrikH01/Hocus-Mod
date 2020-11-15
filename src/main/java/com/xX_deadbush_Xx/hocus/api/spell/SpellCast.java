package com.xX_deadbush_Xx.hocus.api.spell;

import java.util.Arrays;

import javax.annotation.Nullable;

import com.xX_deadbush_Xx.hocus.Hocus;
import com.xX_deadbush_Xx.hocus.common.items.wands.WandItem;
import com.xX_deadbush_Xx.hocus.common.world.data.PlayerSpellCapability;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Hocus.MOD_ID, bus = Bus.FORGE)
public abstract class SpellCast implements INBTSerializable<CompoundNBT> {
	
	@Nullable
	public PlayerEntity caster;
	@Nullable
	protected ItemStack wand;
	
	public int[] args;
	public int ticks = 0;
	
	public SpellCast(PlayerEntity player, ItemStack wand, int... args) {
		this.caster = player;
		this.wand = wand;
		this.args = args;
	}
	
	public SpellCast(int ticks, int... args) {
		this.ticks = ticks;
		this.args = args;
	}
	
	public void tick(PlayerEntity player) {
		this.caster = player;
		this.wand = player.getHeldItemMainhand();
 		if(!(wand.getItem() instanceof WandItem)) {
 			PlayerSpellCapability.getSpellCap(player).stopSpell();
 		}
 		
		this.ticks++;
	}
	
	public int expectedAmountOfArgs() {
		return 0;
	}
	
	public abstract ISpellRenderer<? extends SpellCast> getRenderer();
	
	//The player and item are serialized by the capability!
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("ticks", ticks);
		nbt.putIntArray("args", args);
		return nbt;
	}
	
	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		this.ticks = nbt.getInt("ticks");
		this.args = nbt.getIntArray("args");
	}
	
	public void readBuffer(PacketBuffer buf) {
		this.ticks = buf.readInt();
		int j = buf.readInt();
		this.args = new int[j];
		for(int i = 0; i < this.args.length; i++) {
			this.args[i] = buf.readInt();
		}
	}
	
	public void writeBuffer(PacketBuffer buf) {
		buf.writeInt(this.ticks);
		buf.writeInt(this.args.length);
		for(int i : args) {
			buf.writeInt(i);
		}
	}

	public PlayerEntity getCaster() {
		return this.caster;
	}
	
	@SubscribeEvent
	public static void onWorldJoin(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof ServerPlayerEntity) {
			ServerPlayerEntity player = (ServerPlayerEntity)event.getEntity();
			PlayerSpellCapability.sendToClient(player, player.world);
		}
	}

	public void initialize(PlayerEntity player, ItemStack wand) {
		this.caster = player;
		this.wand = wand;
	}
	
	@Override
	public String toString() {
		return String.format("%s{ticks: %d, args: %s}",getClass().getName(), this.ticks, Arrays.toString(this.args));
	}
}
